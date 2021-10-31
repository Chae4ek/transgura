package chae4ek.transgura.ecs.component;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import java.util.EnumMap;
import java.util.Map;

public class ComponentManager {

  private static final transient GameAlert gameAlert = new GameAlert(ComponentManager.class);

  private final Map<ComponentType, Component> typeToComponent = new EnumMap<>(ComponentType.class);

  public ComponentManager(final Component... components) {
    addComponents(components);
  }

  /**
   * Add a component
   *
   * @param components the unique components
   */
  public void addComponents(final Component... components) {
    for (final Component component : components) {
      final Component prevComponent = typeToComponent.put(component.getType(), component);
      if (prevComponent != null) {
        gameAlert.warn(GameErrorType.COMPONENT_ALREADY_EXISTS, prevComponent.getType().toString());
      }
    }
  }

  /**
   * Get a component
   *
   * @param componentType the type of the component to get
   * @return the component or null if it doesn't exist
   */
  public Component getComponent(final ComponentType componentType) {
    final Component component = typeToComponent.get(componentType);
    if (component == null) {
      gameAlert.warn(GameErrorType.COMPONENT_DOES_NOT_EXIST, componentType.toString());
    }
    return component;
  }

  @Override
  public String toString() {
    return "typeToComponent: " + typeToComponent;
  }
}
