package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class EnumValueDefinition {
  private int[] tileSrcRect;
  private int color;
  private String id;
  private Integer tileId;
  private TilesetRectangle tileRect;

  /**
   * **WARNING**: this deprecated value will be *removed* completely on version 1.4.0+ Replaced by:
   * `tileRect`
   */
  @JsonProperty("__tileSrcRect")
  public int[] getTileSrcRect() {
    return tileSrcRect;
  }

  @JsonProperty("__tileSrcRect")
  public void setTileSrcRect(final int[] value) {
    tileSrcRect = value;
  }

  /** Optional color */
  @JsonProperty("color")
  public int getColor() {
    return color;
  }

  @JsonProperty("color")
  public void setColor(final int value) {
    color = value;
  }

  /** Enum value */
  @JsonProperty("id")
  public String getid() {
    return id;
  }

  @JsonProperty("id")
  public void setid(final String value) {
    id = value;
  }

  /**
   * **WARNING**: this deprecated value will be *removed* completely on version 1.4.0+ Replaced by:
   * `tileRect`
   */
  @JsonProperty("tileId")
  public Integer getTileId() {
    return tileId;
  }

  @JsonProperty("tileId")
  public void setTileId(final Integer value) {
    tileId = value;
  }

  /** Optional tileset rectangle to represents this value */
  @JsonProperty("tileRect")
  public TilesetRectangle getTileRect() {
    return tileRect;
  }

  @JsonProperty("tileRect")
  public void setTileRect(final TilesetRectangle value) {
    tileRect = value;
  }
}
