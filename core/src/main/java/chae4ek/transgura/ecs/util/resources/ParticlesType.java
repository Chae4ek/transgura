package chae4ek.transgura.ecs.util.resources;

public enum ParticlesType {
  BLUE("blue.p");

  public final String particleName;

  ParticlesType(final String particleName) {
    this.particleName = particleName;
  }

  @Override
  public String toString() {
    return "particleName: " + particleName;
  }
}
