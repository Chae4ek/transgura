package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** In a tileset definition, enum based tag infos */
public class EnumTagValue {
  private String enumValueId;
  private int[] tileIds;

  @JsonProperty("enumValueId")
  public String getEnumValueId() {
    return enumValueId;
  }

  @JsonProperty("enumValueId")
  public void setEnumValueId(final String value) {
    enumValueId = value;
  }

  @JsonProperty("tileIds")
  public int[] getTileIds() {
    return tileIds;
  }

  @JsonProperty("tileIds")
  public void setTileIds(final int[] value) {
    tileIds = value;
  }
}
