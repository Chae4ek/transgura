package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** In a tileset definition, user defined meta-data of a tile. */
public class TileCustomMetadata {
  private String data;
  private int tileId;

  @JsonProperty("data")
  public String getData() {
    return data;
  }

  @JsonProperty("data")
  public void setData(final String value) {
    data = value;
  }

  @JsonProperty("tileId")
  public int getTileId() {
    return tileId;
  }

  @JsonProperty("tileId")
  public void setTileId(final int value) {
    tileId = value;
  }
}
