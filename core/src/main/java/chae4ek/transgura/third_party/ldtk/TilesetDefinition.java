package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

/**
 * The `Tileset` definition is the most important part among project definitions. It contains some
 * extra informations about each integrated tileset. If you only had to parse one definition
 * section, that would be the one.
 */
public class TilesetDefinition {
  private int cHei;
  private int cWid;
  private Map<String, Object> cachedPixelData;
  private TileCustomMetadata[] customData;
  private EmbedAtlas embedAtlas;
  private EnumTagValue[] enumTags;
  private String identifier;
  private int padding;
  private int pxHei;
  private int pxWid;
  private String relPath;
  private Map<String, Object>[] savedSelections;
  private int spacing;
  private String[] tags;
  private Integer tagsSourceEnumUid;
  private int tileGridSize;
  private int uid;

  /** Grid-based height */
  @JsonProperty("__cHei")
  public int getCHei() {
    return cHei;
  }

  @JsonProperty("__cHei")
  public void setCHei(final int value) {
    cHei = value;
  }

  /** Grid-based width */
  @JsonProperty("__cWid")
  public int getCWid() {
    return cWid;
  }

  @JsonProperty("__cWid")
  public void setCWid(final int value) {
    cWid = value;
  }

  /**
   * The following data is used internally for various optimizations. It's always synced with source
   * image changes.
   */
  @JsonProperty("cachedPixelData")
  public Map<String, Object> getCachedPixelData() {
    return cachedPixelData;
  }

  @JsonProperty("cachedPixelData")
  public void setCachedPixelData(final Map<String, Object> value) {
    cachedPixelData = value;
  }

  /** An array of custom tile metadata */
  @JsonProperty("customData")
  public TileCustomMetadata[] getCustomData() {
    return customData;
  }

  @JsonProperty("customData")
  public void setCustomData(final TileCustomMetadata[] value) {
    customData = value;
  }

  /**
   * If this value is set, then it means that this atlas uses an internal LDtk atlas image instead
   * of a loaded one. Possible values: &lt;`null`&gt;, `LdtkIcons`
   */
  @JsonProperty("embedAtlas")
  public EmbedAtlas getEmbedAtlas() {
    return embedAtlas;
  }

  @JsonProperty("embedAtlas")
  public void setEmbedAtlas(final EmbedAtlas value) {
    embedAtlas = value;
  }

  /**
   * Tileset tags using Enum values specified by `tagsSourceEnumId`. This array contains 1 element
   * per Enum value, which contains an array of all Tile IDs that are tagged with it.
   */
  @JsonProperty("enumTags")
  public EnumTagValue[] getEnumTags() {
    return enumTags;
  }

  @JsonProperty("enumTags")
  public void setEnumTags(final EnumTagValue[] value) {
    enumTags = value;
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

  /** Distance in pixels from image borders */
  @JsonProperty("padding")
  public int getPadding() {
    return padding;
  }

  @JsonProperty("padding")
  public void setPadding(final int value) {
    padding = value;
  }

  /** Image height in pixels */
  @JsonProperty("pxHei")
  public int getPxHei() {
    return pxHei;
  }

  @JsonProperty("pxHei")
  public void setPxHei(final int value) {
    pxHei = value;
  }

  /** Image width in pixels */
  @JsonProperty("pxWid")
  public int getPxWid() {
    return pxWid;
  }

  @JsonProperty("pxWid")
  public void setPxWid(final int value) {
    pxWid = value;
  }

  /**
   * Path to the source file, relative to the current project JSON file<br>
   * It can be null if no image was provided, or when using an embed atlas.
   */
  @JsonProperty("relPath")
  public String getRelPath() {
    return relPath;
  }

  @JsonProperty("relPath")
  public void setRelPath(final String value) {
    relPath = value;
  }

  /** Array of group of tiles selections, only meant to be used in the editor */
  @JsonProperty("savedSelections")
  public Map<String, Object>[] getSavedSelections() {
    return savedSelections;
  }

  @JsonProperty("savedSelections")
  public void setSavedSelections(final Map<String, Object>[] value) {
    savedSelections = value;
  }

  /** Space in pixels between all tiles */
  @JsonProperty("spacing")
  public int getSpacing() {
    return spacing;
  }

  @JsonProperty("spacing")
  public void setSpacing(final int value) {
    spacing = value;
  }

  /** An array of user-defined tags to organize the Tilesets */
  @JsonProperty("tags")
  public String[] getTags() {
    return tags;
  }

  @JsonProperty("tags")
  public void setTags(final String[] value) {
    tags = value;
  }

  /** Optional Enum definition UID used for this tileset meta-data */
  @JsonProperty("tagsSourceEnumUid")
  public Integer getTagsSourceEnumUid() {
    return tagsSourceEnumUid;
  }

  @JsonProperty("tagsSourceEnumUid")
  public void setTagsSourceEnumUid(final Integer value) {
    tagsSourceEnumUid = value;
  }

  @JsonProperty("tileGridSize")
  public int getTileGridSize() {
    return tileGridSize;
  }

  @JsonProperty("tileGridSize")
  public void setTileGridSize(final int value) {
    tileGridSize = value;
  }

  /** Unique Intidentifier */
  @JsonProperty("uid")
  public int getUid() {
    return uid;
  }

  @JsonProperty("uid")
  public void setUid(final int value) {
    uid = value;
  }
}
