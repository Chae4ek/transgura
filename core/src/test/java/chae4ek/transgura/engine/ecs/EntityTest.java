package chae4ek.transgura.engine.ecs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.only;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import chae4ek.transgura.engine.util.debug.GameSettings;
import chae4ek.transgura.engine.util.exceptions.GameException;
import chae4ek.transgura.util.ReflectUtils;
import java.lang.reflect.Field;
import java.util.Map;
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
    Game.scene = Mockito.mock(Scene.class);
  }

  @BeforeEach
  void setUpEach() {
    entityManager = Mockito.mock(EntityManager.class);
    ReflectUtils.setFieldValue(Game.scene, entityManagerField, entityManager);
  }

  private static class Component1 extends Component {}

  private static class Component2 extends Component {}

  @Nested
  public class CreatingEntityTest {

    @Test
    void noneComponents__addEntity() {
      final Entity entity = new Entity();
      Assertions.assertSame(Game.scene, entity.scene);
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
      final Map<?, ?> components = ReflectUtils.getFieldValue(Entity.class, entity, "components");
      Assertions.assertEquals(1, components.size());
    }

    @Test
    void destroyedComponent__WARN_componentDestroyed() {
      GameSettings.isWARNThrowOn = true;
      Assertions.assertThrows(
          GameException.class,
          this::destroyedComponent__nothing,
          "The component {} is already destroyed. It wasn't added");
      GameSettings.isWARNThrowOn = false;
    }

    @Test
    void destroyedComponent__nothing() {
      final Component comp = Mockito.mock(Component.class);
      Mockito.when(comp.isDestroyed()).thenReturn(true);

      final Entity entity = new Entity(comp);

      Mockito.verify(comp, only()).isDestroyed();
      Assertions.assertNull(entity.getComponent(comp.getClass()));
    }

    @Test
    void getNonExistComponent__WARN_componentDoesNotExist() {
      GameSettings.isWARNThrowOn = true;
      Assertions.assertThrows(
          GameException.class,
          this::getNonExistComponent__null,
          "The component {} doesn't belong to this entity {}");
      GameSettings.isWARNThrowOn = false;
    }

    @Test
    void getNonExistComponent__null() {
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
      GameSettings.isWARNThrowOn = true;
      Assertions.assertThrows(
          GameException.class, this::destroyAgain__nothing, "The entity {} is already destroyed");
      Assertions.assertThrows(
          GameException.class,
          this::addComponentsAfterDestroy__nothing,
          "The entity {} is already destroyed. Components weren't added");
      Assertions.assertThrows(
          GameException.class,
          this::getComponentAfterDestroy__null,
          "The entity {} is already destroyed. You cannot get a component");
      GameSettings.isWARNThrowOn = false;
    }

    @Test
    void destroyAgain__nothing() {
      Entity entity = new Entity();
      entity.destroy();

      entity = Mockito.spy(entity);
      entity.destroy();

      Mockito.verify(entity, only()).destroy();
      Mockito.verifyNoMoreInteractions(entity);
    }

    @Test
    void addComponentsAfterDestroy__nothing() {
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
      final Entity entity = new Entity();
      entity.destroy();

      Assertions.assertNull(entity.getComponent(Component.class));
    }
  }
}
