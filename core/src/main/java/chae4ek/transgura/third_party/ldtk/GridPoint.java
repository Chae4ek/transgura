package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** This object is just a grid-based coordinate used in Field values. */
public class GridPoint {
  private int cx;
  private int cy;

  /** X grid-based coordinate */
  @JsonProperty("cx")
  public int getCx() {
    return cx;
  }

  @JsonProperty("cx")
  public void setCx(final int value) {
    cx = value;
  }

  /** Y grid-based coordinate */
  @JsonProperty("cy")
  public int getCy() {
    return cy;
  }

  @JsonProperty("cy")
  public void setCy(final int value) {
    cy = value;
  }
}
