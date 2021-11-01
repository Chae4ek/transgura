package chae4ek.transgura.exceptions;

public enum GameErrorType {
  // EntityManager
  ENTITY_DOES_NOT_EXIST("The entity doesn't exist"),
  ENTITY_HAS_BEEN_REPLACED("The entity had already existed, so it was replaced"),
  // SystemManager
  SYSTEM_DOES_NOT_EXIST("The system doesn't exist"),
  ENTITY_HAS_NOT_SYSTEM("The entity has not a system"),
  SYSTEM_HAS_BEEN_REPLACED("The system had already existed, so it was replaced"),
  // RenderManager
  RENDER_COMPONENT_DOES_NOT_EXIST("The render component doesn't exist"),
  ENTITY_HAS_NOT_RENDER_COMPONENT("The entity has not a render component"),
  RENDER_COMPONENT_HAS_BEEN_REPLACED(
      "The render component had already existed, so it was replaced"),

  // Entity
  COMPONENT_DOES_NOT_EXIST("The component doesn't exist"),
  COMPONENT_ALREADY_EXISTS("The component already exists, so it was replaced"),
  COMPONENT_HAS_NOT_PARENT_ENTITY("The component has not parent entity, but the entity has"),

  // System
  SYSTEM_SCENE_IS_NOT_EQUAL_TO_ENTITY_SCENE(
      "The system's scene is not equal to the entity's scene"),
  // RenderComponent
  RENDER_COMPONENT_SCENE_IS_NOT_EQUAL_TO_ENTITY_SCENE(
      "The render component's scene is not equal to the entity's scene"),

  // ResourceLoader
  RESOURCE_LOADER_ERROR("Resource loader error occurs"),
  ATLAS_IS_NOT_LOADED("The atlas is not loaded");

  private final String message;

  GameErrorType(final String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return message;
  }
}
