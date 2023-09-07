package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** This object represents a custom sub rectangle in a Tileset image. */
public class TilesetRectangle {
  private int h;
  private int tilesetUid;
  private int w;
  private int x;
  private int y;

  /** Height in pixels */
  @JsonProperty("h")
  public int getH() {
    return h;
  }

  @JsonProperty("h")
  public void setH(final int value) {
    h = value;
  }

  /** UID of the tileset */
  @JsonProperty("tilesetUid")
  public int getTilesetUid() {
    return tilesetUid;
  }

  @JsonProperty("tilesetUid")
  public void setTilesetUid(final int value) {
    tilesetUid = value;
  }

  /** Width in pixels */
  @JsonProperty("w")
  public int getW() {
    return w;
  }

  @JsonProperty("w")
  public void setW(final int value) {
    w = value;
  }

  /** X pixels coordinate of the top-left corner in the Tileset image */
  @JsonProperty("x")
  public int getX() {
    return x;
  }

  @JsonProperty("x")
  public void setX(final int value) {
    x = value;
  }

  /** Y pixels coordinate of the top-left corner in the Tileset image */
  @JsonProperty("y")
  public int getY() {
    return y;
  }

  @JsonProperty("y")
  public void setY(final int value) {
    y = value;
  }
}
