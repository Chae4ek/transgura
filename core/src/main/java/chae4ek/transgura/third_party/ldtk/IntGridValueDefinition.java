package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** IntGrid value definition */
public class IntGridValueDefinition {
  private String color;
  private String identifier;
  private TilesetRectangle tile;
  private int value;

  @JsonProperty("color")
  public String getColor() {
    return color;
  }

  @JsonProperty("color")
  public void setColor(final String value) {
    color = value;
  }

  /** User defined unique identifier */
  @JsonProperty("identifier")
  public String getIdentifier() {
    return identifier;
  }

  @JsonProperty("identifier")
  public void setIdentifier(final String value) {
    identifier = value;
  }

  @JsonProperty("tile")
  public TilesetRectangle getTile() {
    return tile;
  }

  @JsonProperty("tile")
  public void setTile(final TilesetRectangle value) {
    tile = value;
  }

  /** The IntGrid value itself */
  @JsonProperty("value")
  public int getValue() {
    return value;
  }

  @JsonProperty("value")
  public void setValue(final int value) {
    this.value = value;
  }
}
