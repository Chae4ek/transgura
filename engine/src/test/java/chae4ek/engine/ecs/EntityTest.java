package chae4ek.engine.ecs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.only;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import chae4ek.engine.util.GameSettings;
import chae4ek.engine.util.exceptions.GameException;
import chae4ek.util.ReflectUtils;
import com.badlogic.gdx.utils.ObjectMap;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EntityTest {

  private final Field entityManagerField = ReflectUtils.getField(Scene.class, "entityManager");
  private EntityManager entityManager;

  @BeforeAll
  static void setUp() {
    ReflectUtils.setFieldValue(null, Game.class, "scene", Mockito.mock(Scene.class));
  }

  @BeforeEach
  void setUpEach() {
    GameSettings.isWARNThrowOn = true;
    entityManager = Mockito.mock(EntityManager.class);
    ReflectUtils.setFieldValue(Game.getScene(), entityManagerField, entityManager);
  }

  private static class Component1 extends Component {}

  private static class Component2 extends Component {}

  private static class Component3 extends Component {
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(final Object obj) {
      return true;
    }
  }

  @Nested
  public class CreatingEntityTest {

    @Test
    void noneComponents__addEntity() {
      final Entity entity = new Entity();
      Mockito.verify(entityManager, only()).addEntity(entity);
    }

    @Test
    void oneComponent__addComponent() {
      final Component comp = Mockito.mock(Component.class);
      Mockito.when(comp.bind(any())).thenReturn(true);

      final Entity entity = new Entity(comp);

      Mockito.verify(comp, times(1)).bind(entity);
      Assertions.assertEquals(comp, entity.getComponent(comp.getClass()));
    }

    @Test
    void twoComponents__2x_addComponent() {
      final Component1 comp1 = Mockito.mock(Component1.class);
      final Component2 comp2 = Mockito.mock(Component2.class);
      Mockito.when(comp1.bind(any())).thenReturn(true);
      Mockito.when(comp2.bind(any())).thenReturn(true);

      final Entity entity = new Entity(comp1, comp2);

      Mockito.verify(comp1, times(1)).bind(entity);
      Mockito.verify(comp2, times(1)).bind(entity);
      Assertions.assertEquals(comp1, entity.getComponent(comp1.getClass()));
      Assertions.assertEquals(comp2, entity.getComponent(comp2.getClass()));
    }

    @Test
    void twoSameComponents__addComponent() {
      final Component comp = Mockito.mock(Component.class);
      Mockito.when(comp.bind(any())).thenReturn(true).thenReturn(false);

      final Entity entity = new Entity(comp, comp);

      Mockito.verify(comp, times(2)).bind(entity);
      Assertions.assertEquals(comp, entity.getComponent(comp.getClass()));
      final ObjectMap<?, ?> components =
          ReflectUtils.getFieldValue(Entity.class, entity, "components");
      Assertions.assertEquals(1, components.size);
    }

    @Test
    void fiveSameComponentClasses__5x_addComponent() {
      final Component comp1 = new Component1();
      final Component comp2 = new Component1();
      final Component comp3 = new Component1();
      final Component comp4 = new Component1();
      final Component comp5 = new Component1();

      final Entity entity = new Entity(comp1, comp2, comp3, comp4, comp5);

      int i = 0;
      for (final Component component : entity) {
        ++i;
        if (i == 1) Assertions.assertEquals(comp1, component);
        if (i == 2) Assertions.assertEquals(comp2, component);
        if (i == 3) Assertions.assertEquals(comp3, component);
        if (i == 4) Assertions.assertEquals(comp4, component);
        if (i == 5) Assertions.assertEquals(comp5, component);
      }
    }

    @Test
    void twoEqualedComponents__WARN_componentReplaced() {
      Assertions.assertThrows(
          GameException.class,
          () -> {
            final Component comp1 = new Component3();
            final Component comp2 = new Component3();

            new Entity(comp1, comp2);
          },
          "Component {} replaced {}. The latter is still attached to this entity {}, but the entity doesn't have it");
    }

    @Test
    void destroyedComponent__WARN_componentDestroyed() {
      Assertions.assertThrows(
          GameException.class,
          this::destroyedComponent,
          "The component {} is already destroyed. It wasn't added");
    }

    @Test
    void destroyedComponent__nothing() {
      GameSettings.isWARNThrowOn = false;
      destroyedComponent();
    }

    private void destroyedComponent() {
      final Component comp = Mockito.mock(Component.class);
      Mockito.when(comp.isDestroyed()).thenReturn(true);

      final Entity entity = new Entity(comp);

      Mockito.verify(comp, only()).isDestroyed();
      Assertions.assertNull(entity.getComponent(comp.getClass()));
    }

    @Test
    void getNonExistComponent__WARN_componentDoesNotExist() {
      Assertions.assertThrows(
          GameException.class,
          this::getNonExistComponent,
          "The component {} doesn't belong to this entity {}");
    }

    @Test
    void getNonExistComponent__null() {
      GameSettings.isWARNThrowOn = false;
      getNonExistComponent();
    }

    private void getNonExistComponent() {
      final Entity entity = new Entity();
      Assertions.assertNull(entity.getComponent(Component.class));
    }
  }

  @Nested
  public class DestroyingEntityTest {

    @Test
    void noneComponents__onDestroy_removeEntity() {
      final Entity entity = Mockito.spy(new Entity());
      entity.destroy();

      Mockito.verify(entity, times(1)).onDestroy();
      Mockito.verify(entityManager, times(1)).removeEntity(entity);
      Assertions.assertTrue(entity.isDestroyed());
    }

    @Test
    void oneComponent__removeComponent() {
      final Component comp = Mockito.mock(Component.class);
      Mockito.when(comp.bind(any())).thenReturn(true);
      final Entity entity = new Entity(comp);

      entity.destroy();

      Mockito.verify(comp, times(1)).destroy();
    }

    @Test
    void afterDestroy__WARN_alreadyDestroyed() {
      Assertions.assertThrows(
          GameException.class, this::destroyAgain, "The entity {} is already destroyed");
      Assertions.assertThrows(
          GameException.class,
          this::addComponentsAfterDestroy,
          "The entity {} is already destroyed. Components weren't added");
      Assertions.assertThrows(
          GameException.class,
          this::getComponentAfterDestroy,
          "The entity {} is already destroyed. You cannot get a component");
    }

    @Test
    void destroyAgain__nothing() {
      GameSettings.isWARNThrowOn = false;
      destroyAgain();
    }

    private void destroyAgain() {
      Entity entity = new Entity();
      entity.destroy();

      entity = Mockito.spy(entity);
      entity.destroy();

      Mockito.verify(entity, only()).destroy();
      Mockito.verifyNoMoreInteractions(entity);
    }

    @Test
    void addComponentsAfterDestroy__nothing() {
      GameSettings.isWARNThrowOn = false;
      addComponentsAfterDestroy();
    }

    private void addComponentsAfterDestroy() {
      final Entity entity = new Entity();
      entity.destroy();
      final Component1 comp1 = Mockito.mock(Component1.class);
      final Component2 comp2 = Mockito.mock(Component2.class);

      entity.addComponent(comp1, comp2);

      Mockito.verifyNoInteractions(comp1);
      Mockito.verifyNoInteractions(comp2);
    }

    @Test
    void getComponentAfterDestroy__null() {
      GameSettings.isWARNThrowOn = false;
      getComponentAfterDestroy();
    }

    private void getComponentAfterDestroy() {
      final Entity entity = new Entity();
      entity.destroy();

      Assertions.assertNull(entity.getComponent(Component.class));
    }
  }
}
