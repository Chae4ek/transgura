package chae4ek.engine.ecs;

import chae4ek.engine.util.debug.CallOnce;
import chae4ek.engine.util.exceptions.GameAlert;
import chae4ek.engine.util.serializers.HierarchicallySerializable;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.Iterator;

public class Entity implements Iterable<Component>, HierarchicallySerializable {

  private static final GameAlert gameAlert = new GameAlert(Entity.class);

  private transient ObjectMap<Class<? extends Component>, Object> components = new ObjectMap<>();
  private transient boolean isDestroyed;

  public Entity(final Component... components) {
    Game.getScene().entityManager.addEntity(this);
    for (final Component component : components) addComponent(component);
  }

  /**
   * @return true if this entity is destroyed or destroying
   */
  public final boolean isDestroyed() {
    return isDestroyed;
  }

  /**
   * Remove a component
   *
   * <p>Note: the component SHOULD exist in the {@link #components}
   */
  final void removeComponent(final Component component) {
    // if this entity is destroying the components is null. See the destroy() method
    if (components == null) return;
    final Object removed = components.remove(component.getClass());
    if (removed.getClass() == ComponentArray.class) {
      final ComponentArray comps = (ComponentArray) removed;
      Component notEqual = null;
      int i = 0;
      for (; i < comps.array.length; ++i) {
        if (comps.array[i] != null) {
          if (component.equals(comps.array[i])) {
            comps.remove(i);
            break;
          } else notEqual = comps.array[i];
        }
      }
      if (comps.size == 1) {
        if (notEqual == null)
          while (++i < comps.array.length)
            if (comps.array[i] != null) {
              notEqual = comps.array[i];
              break;
            }
        components.put(component.getClass(), notEqual);
      } else if (comps.size > 0) components.put(component.getClass(), comps);
    }
  }

  /**
   * Add a component
   *
   * @param component the unique component
   */
  public final void addComponent(final Component component) {
    if (isDestroyed) {
      gameAlert.warn(
          "The entity {} is already destroyed. A component {} wasn't added", this, component);
      return;
    }
    if (component.isDestroyed()) {
      gameAlert.warn("The component {} is already destroyed. It wasn't added", component);
      return;
    }
    if (component.bind(this)) {
      final Class<? extends Component> compClass = component.getClass();
      final Object prev = components.put(compClass, component);
      if (prev == null) return;
      final boolean wasAdded;
      if (prev.getClass() == ComponentArray.class) {
        final ComponentArray comps = (ComponentArray) prev;
        wasAdded = !comps.add(component);
        components.put(compClass, comps);
      } else {
        wasAdded = prev.equals(component);
        if (!wasAdded) {
          final ComponentArray comps = new ComponentArray((Component) prev, component);
          components.put(compClass, comps);
        }
      }
      if (wasAdded) {
        gameAlert.warn(
            "Component {} replaced {}. The latter is still attached to this entity {}, but the entity doesn't have it",
            component,
            prev,
            this);
      }
    }
  }

  /**
   * Add components
   *
   * @param components the unique components
   */
  public final void addComponent(final Component component, final Component... components) {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed. Components weren't added", this);
      return;
    }
    addComponent(component);
    for (final Component comp : components) addComponent(comp);
  }

  /**
   * Get a component or the first one if there are several of them
   *
   * @param componentClass the class of the component to get
   * @return the component or null if it doesn't exist
   * @see #iteratorOfComponents
   */
  public final <T extends Component> T getComponent(final Class<T> componentClass) {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed. You cannot get a component", this);
      return null;
    }
    final Object getted = components.get(componentClass);
    if (getted == null) {
      gameAlert.warn(
          "The component {} doesn't belong to this entity {}", componentClass.getName(), this);
      return null;
    }
    if (getted.getClass() == ComponentArray.class) {
      final Component[] array = ((ComponentArray) getted).array;
      for (final Component comp : array)
        if (comp != null) {
          @SuppressWarnings("unchecked")
          final T tcomp = (T) comp;
          return tcomp;
        }
    }
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    final T tcomp = (T) getted;
    return tcomp;
  }

  /**
   * Get set of components
   *
   * @param componentClass the class of the component to get
   * @return the component or null if it doesn't exist
   * @see #getComponent
   */
  public final <T extends Component> Iterator<T> iteratorOfComponents(
      final Class<T> componentClass) {
    if (isDestroyed) {
      gameAlert.warn(
          "The entity {} is already destroyed. You cannot get a component iterator", this);
      return null;
    }
    final Object getted = components.get(componentClass);
    if (getted == null) {
      gameAlert.warn(
          "The component {} doesn't belong to this entity {}", componentClass.getName(), this);
      return null;
    }
    if (getted.getClass() != ComponentArray.class) {
      gameAlert.warn(
          "There is only one component {} belong to this entity {}",
          componentClass.getName(),
          this);
      @SuppressWarnings("unchecked")
      final T component = (T) getted;
      return new SingleIterator<>(component);
    }
    return ((ComponentArray) getted).iterator();
  }

  /** Destroy this entity and all associated with it components on its scene */
  @CallOnce
  public final void destroy() {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed", this);
      return;
    }
    isDestroyed = true;
    onDestroy();
    Game.getScene().entityManager.removeEntity(this);

    final ObjectMap<Class<? extends Component>, Object> temp = components;
    components = null;
    for (final Object component : temp.values()) {
      if (component.getClass() == ComponentArray.class) ((ComponentArray) component).destroyAll();
      else ((Component) component).destroy();
    }
  }

  /** Invokes before actually destroying, but {@link #isDestroyed()} returns true now */
  @CallOnce
  protected void onDestroy() {}

  @Override
  public final boolean equals(final Object o) {
    return this == o;
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }

  @Override
  public Iterator<Component> iterator() {
    return new Iterator<>() {

      private final Iterator<Object> mainIterator = components.values().iterator();
      private final SingleIterator<Component> singleIterator = new SingleIterator<>();
      private Iterator<Component> iterator = singleIterator;

      @Override
      public boolean hasNext() {
        return iterator.hasNext() || mainIterator.hasNext();
      }

      @Override
      public Component next() {
        if (!iterator.hasNext()) {
          final Object next = mainIterator.next();
          if (next.getClass() == ComponentArray.class) {
            iterator = ((ComponentArray) next).iterator();
          } else {
            singleIterator.setElement((Component) next);
            iterator = singleIterator;
          }
        }
        return iterator.next();
      }
    };
  }

  @Override
  public void serialize(final DefaultSerializer serializer) throws Exception {
    serializer.writeInt(components.size);
    for (final Object comp : components.values()) serializer.write(comp);
    serializer.writeThis();
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    int size = deserializer.readInt();
    components = new ObjectMap<>(size);
    for (; size > 0; --size) {
      final Object getted = deserializer.read();
      Class<?> clazz = getted.getClass();
      if (clazz == ComponentArray.class) {
        final ComponentArray comps = (ComponentArray) getted;
        clazz = comps.array[0].getClass();
        for (int i = 0; i < comps.size; ++i) comps.array[i].bind(this);
      } else {
        ((Component) getted).bind(this);
      }
      @SuppressWarnings("unchecked")
      final Class<? extends Component> compClass = (Class<? extends Component>) clazz;
      components.put(compClass, getted);
    }
    deserializer.readTo(this);
  }

  private static final class SingleIterator<E> implements Iterator<E> {

    private E element;
    private boolean hasNext;

    public SingleIterator(final E element) {
      this.element = element;
      hasNext = true;
    }

    public SingleIterator() {}

    public void setElement(final E element) {
      this.element = element;
      hasNext = true;
    }

    @Override
    public boolean hasNext() {
      return hasNext;
    }

    @Override
    public E next() {
      hasNext = false;
      return element;
    }
  }

  private static final class ComponentArray implements HierarchicallySerializable {

    private Component[] array = new Component[4];
    private int size;

    public ComponentArray(final Component comp1, final Component comp2) {
      array[0] = comp1;
      array[1] = comp2;
      size = 2;
    }

    public void destroyAll() {
      for (int i = 0; size > 0; ++i) {
        if (array[i] != null) {
          --size;
          array[i].destroy();
        }
      }
    }

    public boolean add(final Component component) {
      ++size;
      if (size > array.length) {
        final Component[] old = array;
        array = new Component[(int) (old.length * 1.75f)];
        java.lang.System.arraycopy(old, 0, array, 0, old.length);
        return tryAdd(component, size);
      } else return tryAdd(component, array.length);
    }

    private boolean tryAdd(final Component component, final int maxLength) {
      boolean added = true;
      int index = -1;
      for (int i = 0, count = size; i < maxLength && count > 0; ++i) {
        if (array[i] != null) {
          --count;
          if (array[i].equals(component)) {
            index = i;
            added = false;
            break;
          }
        } else if (index < 0) {
          index = i;
        }
      }
      array[index] = component;
      return added;
    }

    public void remove(final int index) {
      array[index] = null;
      --size;
    }

    public <T> Iterator<T> iterator() {
      return new ComponentIterator<>(this);
    }

    @Override
    public void serialize(final DefaultSerializer serializer) throws Exception {
      serializer.writeInt(size);
      for (int i = 0, count = size; count > 0; ++i) {
        if (array[i] != null) {
          --count;
          serializer.write(array[i]);
        }
      }
    }

    @Override
    public void deserialize(final DefaultDeserializer deserializer) throws Exception {
      size = deserializer.readInt();
      array = new Component[size];
      for (int i = 0; i < size; ++i) array[i] = (Component) deserializer.read();
    }

    private static final class ComponentIterator<E> implements Iterator<E> {

      private final ComponentArray comps;
      private int index;
      private int count;

      public ComponentIterator(final ComponentArray comps) {
        this.comps = comps;
      }

      @Override
      public boolean hasNext() {
        return count < comps.size;
      }

      @Override
      public E next() {
        while (comps.array[index] == null) ++index;
        ++count;
        @SuppressWarnings("unchecked")
        final E e = (E) comps.array[index++];
        return e;
      }
    }
  }
}
