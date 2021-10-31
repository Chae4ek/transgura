package chae4ek.transgura.exceptions;

public enum GameErrorType {
  COMPONENT_DOES_NOT_EXIST("The component doesn't exist"),
  COMPONENT_ALREADY_EXISTS("The component already exists"),
  ENTITY_DOES_NOT_EXIST("The entity doesn't exist"),
  SYSTEM_ENTITY_HAS_NOT_SCRIPT("The system entity has not a system script"),
  SYSTEM_ENTITY_DOES_NOT_EXIST("The system entity doesn't exist"),
  ENTITY_HAS_NOT_SPRITE("The entity has not a sprite"),
  ENTITY_HAS_NOT_POSITION("The entity has not a position");

  private final String message;

  GameErrorType(final String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return message;
  }
}
