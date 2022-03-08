package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.SerializationEvent;
import chae4ek.transgura.engine.util.debug.CallOnce;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import org.nustaq.serialization.FSTBasicObjectSerializer;
import org.nustaq.serialization.FSTClazzInfo;
import org.nustaq.serialization.FSTClazzInfo.FSTFieldInfo;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

public class Entity extends SerializationEvent implements Iterable<Component>, Serializable {

  private static final GameAlert gameAlert = new GameAlert(Entity.class);

  /** The scene where this entity is created */
  public final Scene scene = Game.scene;

  private Map<Class<? extends Component>, Component> components = new HashMap<>();
  private boolean isDestroyed;

  public Entity(final Component... components) {
    scene.entityManager.addEntity(this);
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
    if (components != null) components.remove(component.getClass());
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
      final Component prev = components.put(component.getClass(), component);
      if (prev != null) {
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
   * Get a component
   *
   * @param componentClass the class of the component to get
   * @return the component or null if it doesn't exist
   */
  public final <T extends Component> T getComponent(final Class<T> componentClass) {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed. You cannot get a component", this);
      return null;
    }
    @SuppressWarnings("unchecked")
    final T component = (T) components.get(componentClass);
    if (component == null) {
      gameAlert.warn(
          "The component {} doesn't belong to this entity {}", componentClass.getName(), this);
    }
    return component;
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
    scene.entityManager.removeEntity(this);

    final Map<Class<? extends Component>, Component> temp = components;
    components = null;
    for (final Component component : temp.values()) component.destroy();
    components = temp;
    components.clear();
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
    return components.values().iterator();
  }

  @Override
  public void forEach(final Consumer<? super Component> action) {
    components.values().forEach(action);
  }

  @Override
  public Spliterator<Component> spliterator() {
    return components.values().spliterator();
  }

  @Override
  protected void beforeSerialize() {
    for (final Component component : components.values()) component.beforeSerialize();
  }

  @Override
  protected void afterDeserialize() {
    for (final Component component : components.values()) component.afterDeserialize();
  }

  public static class FSTEntitySerializer extends FSTBasicObjectSerializer {

    @Override
    public void writeObject(
        final FSTObjectOutput out,
        final Object toWrite,
        final FSTClazzInfo clzInfo,
        final FSTFieldInfo referencedBy,
        final int streamPosition)
        throws IOException {
      ((Entity) toWrite).beforeSerialize();
      out.defaultWriteObject(toWrite, clzInfo);
    }

    @Override
    public void readObject(
        final FSTObjectInput in,
        final Object toRead,
        final FSTClazzInfo clzInfo,
        final FSTFieldInfo referencedBy) {
      in.defaultReadObject(referencedBy, clzInfo, toRead);
      final Entity entity = (Entity) toRead;
      for (final Component component : entity) component.bind(entity);
      entity.afterDeserialize();
    }
  }
}
