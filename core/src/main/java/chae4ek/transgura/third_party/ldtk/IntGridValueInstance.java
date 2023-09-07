package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** IntGrid value instance */
public class IntGridValueInstance {
  private int coordId;
  private int v;

  /** Coordinate ID in the layer grid */
  @JsonProperty("coordId")
  public int getCoordId() {
    return coordId;
  }

  @JsonProperty("coordId")
  public void setCoordId(final int value) {
    coordId = value;
  }

  /** IntGrid value */
  @JsonProperty("v")
  public int getV() {
    return v;
  }

  @JsonProperty("v")
  public void setV(final int value) {
    v = value;
  }
}
