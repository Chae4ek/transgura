package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** This structure represents a single tile from a given Tileset. */
public class TileInstance {
  private double a;
  private int[] d;
  private int f;
  private int[] px;
  private int[] src;
  private int t;

  /** Alpha/opacity of the tile (0-1, defaults to 1) */
  @JsonProperty("a")
  public double getA() {
    return a;
  }

  @JsonProperty("a")
  public void setA(final double value) {
    a = value;
  }

  /**
   * Internal data used by the editor.<br>
   * For auto-layer tiles: `[ruleId, coordId]`.<br>
   * For tile-layer tiles: `[coordId]`.
   */
  @JsonProperty("d")
  public int[] getD() {
    return d;
  }

  @JsonProperty("d")
  public void setD(final int[] value) {
    d = value;
  }

  /**
   * "Flip bits", a 2-bits integer to represent the mirror transformations of the tile.<br>
   * - Bit 0 = X flip<br>
   * - Bit 1 = Y flip<br>
   * Examples: f=0 (no flip), f=1 (X flip only), f=2 (Y flip only), f=3 (both flips)
   */
  @JsonProperty("f")
  public int getF() {
    return f;
  }

  @JsonProperty("f")
  public void setF(final int value) {
    f = value;
  }

  /**
   * Pixel coordinates of the tile in the **layer** (`[x,y]` format). Don't forget optional layer
   * offsets, if they exist!
   */
  @JsonProperty("px")
  public int[] getPx() {
    return px;
  }

  @JsonProperty("px")
  public void setPx(final int[] value) {
    px = value;
  }

  /** Pixel coordinates of the tile in the **tileset** (`[x,y]` format) */
  @JsonProperty("src")
  public int[] getSrc() {
    return src;
  }

  @JsonProperty("src")
  public void setSrc(final int[] value) {
    src = value;
  }

  /** The *Tile ID* in the corresponding tileset. */
  @JsonProperty("t")
  public int getT() {
    return t;
  }

  @JsonProperty("t")
  public void setT(final int value) {
    t = value;
  }
}
