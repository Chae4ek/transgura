package chae4ek.transgura.ecs.entity;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.MultipleComponent;

public class GameObject extends Entity {

  public GameObject(final MultipleComponent... components) {
    for (final MultipleComponent component : components) addComponent(component);
  }
}
