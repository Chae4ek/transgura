package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class EnumDefinition {
  private String externalFileChecksum;
  private String externalRelPath;
  private Integer iconTilesetUid;
  private String identifier;
  private String[] tags;
  private int uid;
  private EnumValueDefinition[] values;

  @JsonProperty("externalFileChecksum")
  public String getExternalFileChecksum() {
    return externalFileChecksum;
  }

  @JsonProperty("externalFileChecksum")
  public void setExternalFileChecksum(final String value) {
    externalFileChecksum = value;
  }

  /** Relative path to the external file providing this Enum */
  @JsonProperty("externalRelPath")
  public String getExternalRelPath() {
    return externalRelPath;
  }

  @JsonProperty("externalRelPath")
  public void setExternalRelPath(final String value) {
    externalRelPath = value;
  }

  /** Tileset UID if provided */
  @JsonProperty("iconTilesetUid")
  public Integer getIconTilesetUid() {
    return iconTilesetUid;
  }

  @JsonProperty("iconTilesetUid")
  public void setIconTilesetUid(final Integer value) {
    iconTilesetUid = value;
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

  /** An array of user-defined tags to organize the Enums */
  @JsonProperty("tags")
  public String[] getTags() {
    return tags;
  }

  @JsonProperty("tags")
  public void setTags(final String[] value) {
    tags = value;
  }

  /** Unique Int identifier */
  @JsonProperty("uid")
  public int getUid() {
    return uid;
  }

  @JsonProperty("uid")
  public void setUid(final int value) {
    uid = value;
  }

  /** All possible enum values, with their optional Tile infos. */
  @JsonProperty("values")
  public EnumValueDefinition[] getValues() {
    return values;
  }

  @JsonProperty("values")
  public void setValues(final EnumValueDefinition[] value) {
    values = value;
  }
}
