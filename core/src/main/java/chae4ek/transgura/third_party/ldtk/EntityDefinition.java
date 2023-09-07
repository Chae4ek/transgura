package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class EntityDefinition {
  private String color;
  private String doc;
  private boolean exportToToc;
  private FieldDefinition[] fieldDefs;
  private double fillOpacity;
  private int height;
  private boolean hollow;
  private String identifier;
  private boolean keepAspectRatio;
  private LimitBehavior limitBehavior;
  private LimitScope limitScope;
  private double lineOpacity;
  private int maxCount;
  private Integer maxHeight;
  private Integer maxWidth;
  private Integer minHeight;
  private Integer minWidth;
  private int[] nineSliceBorders;
  private double pivotX;
  private double pivotY;
  private RenderMode renderMode;
  private boolean resizableX;
  private boolean resizableY;
  private boolean showName;
  private String[] tags;
  private Integer tileId;
  private double tileOpacity;
  private TilesetRectangle tileRect;
  private TileRenderMode tileRenderMode;
  private Integer tilesetId;
  private int uid;
  private int width;

  /** Base entity color */
  @JsonProperty("color")
  public String getColor() {
    return color;
  }

  @JsonProperty("color")
  public void setColor(final String value) {
    color = value;
  }

  /** User defined documentation for this element to provide help/tips to level designers. */
  @JsonProperty("doc")
  public String getDoc() {
    return doc;
  }

  @JsonProperty("doc")
  public void setDoc(final String value) {
    doc = value;
  }

  /**
   * If enabled, all instances of this entity will be listed in the project "Table of content"
   * object.
   */
  @JsonProperty("exportToToc")
  public boolean getExportToToc() {
    return exportToToc;
  }

  @JsonProperty("exportToToc")
  public void setExportToToc(final boolean value) {
    exportToToc = value;
  }

  /** Array of field definitions */
  @JsonProperty("fieldDefs")
  public FieldDefinition[] getFieldDefs() {
    return fieldDefs;
  }

  @JsonProperty("fieldDefs")
  public void setFieldDefs(final FieldDefinition[] value) {
    fieldDefs = value;
  }

  @JsonProperty("fillOpacity")
  public double getFillOpacity() {
    return fillOpacity;
  }

  @JsonProperty("fillOpacity")
  public void setFillOpacity(final double value) {
    fillOpacity = value;
  }

  /** Pixel height */
  @JsonProperty("height")
  public int getHeight() {
    return height;
  }

  @JsonProperty("height")
  public void setHeight(final int value) {
    height = value;
  }

  @JsonProperty("hollow")
  public boolean getHollow() {
    return hollow;
  }

  @JsonProperty("hollow")
  public void setHollow(final boolean value) {
    hollow = value;
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

  /**
   * Only applies to entities resizable on both X/Y. If TRUE, the entity instance width/height will
   * keep the same aspect ratio as the definition.
   */
  @JsonProperty("keepAspectRatio")
  public boolean getKeepAspectRatio() {
    return keepAspectRatio;
  }

  @JsonProperty("keepAspectRatio")
  public void setKeepAspectRatio(final boolean value) {
    keepAspectRatio = value;
  }

  /** Possible values: `DiscardOldOnes`, `PreventAdding`, `MoveLastOne` */
  @JsonProperty("limitBehavior")
  public LimitBehavior getLimitBehavior() {
    return limitBehavior;
  }

  @JsonProperty("limitBehavior")
  public void setLimitBehavior(final LimitBehavior value) {
    limitBehavior = value;
  }

  /**
   * If TRUE, the maxCount is a "per world" limit, if FALSE, it's a "per level". Possible values:
   * `PerLayer`, `PerLevel`, `PerWorld`
   */
  @JsonProperty("limitScope")
  public LimitScope getLimitScope() {
    return limitScope;
  }

  @JsonProperty("limitScope")
  public void setLimitScope(final LimitScope value) {
    limitScope = value;
  }

  @JsonProperty("lineOpacity")
  public double getLineOpacity() {
    return lineOpacity;
  }

  @JsonProperty("lineOpacity")
  public void setLineOpacity(final double value) {
    lineOpacity = value;
  }

  /** Max instances count */
  @JsonProperty("maxCount")
  public int getMaxCount() {
    return maxCount;
  }

  @JsonProperty("maxCount")
  public void setMaxCount(final int value) {
    maxCount = value;
  }

  /** Max pixel height (only applies if the entity is resizable on Y) */
  @JsonProperty("maxHeight")
  public Integer getMaxHeight() {
    return maxHeight;
  }

  @JsonProperty("maxHeight")
  public void setMaxHeight(final Integer value) {
    maxHeight = value;
  }

  /** Max pixel width (only applies if the entity is resizable on X) */
  @JsonProperty("maxWidth")
  public Integer getMaxWidth() {
    return maxWidth;
  }

  @JsonProperty("maxWidth")
  public void setMaxWidth(final Integer value) {
    maxWidth = value;
  }

  /** Min pixel height (only applies if the entity is resizable on Y) */
  @JsonProperty("minHeight")
  public Integer getMinHeight() {
    return minHeight;
  }

  @JsonProperty("minHeight")
  public void setMinHeight(final Integer value) {
    minHeight = value;
  }

  /** Min pixel width (only applies if the entity is resizable on X) */
  @JsonProperty("minWidth")
  public Integer getMinWidth() {
    return minWidth;
  }

  @JsonProperty("minWidth")
  public void setMinWidth(final Integer value) {
    minWidth = value;
  }

  /**
   * An array of 4 dimensions for the up/right/down/left borders (in this order) when using 9-slice
   * mode for `tileRenderMode`.<br>
   * If the tileRenderMode is not NineSlice, then this array is empty.<br>
   * See: https://en.wikipedia.org/wiki/9-slice_scaling
   */
  @JsonProperty("nineSliceBorders")
  public int[] getNineSliceBorders() {
    return nineSliceBorders;
  }

  @JsonProperty("nineSliceBorders")
  public void setNineSliceBorders(final int[] value) {
    nineSliceBorders = value;
  }

  /** Pivot X coordinate (from 0 to 1.0) */
  @JsonProperty("pivotX")
  public double getPivotX() {
    return pivotX;
  }

  @JsonProperty("pivotX")
  public void setPivotX(final double value) {
    pivotX = value;
  }

  /** Pivot Y coordinate (from 0 to 1.0) */
  @JsonProperty("pivotY")
  public double getPivotY() {
    return pivotY;
  }

  @JsonProperty("pivotY")
  public void setPivotY(final double value) {
    pivotY = value;
  }

  /** Possible values: `Rectangle`, `Ellipse`, `Tile`, `Cross` */
  @JsonProperty("renderMode")
  public RenderMode getRenderMode() {
    return renderMode;
  }

  @JsonProperty("renderMode")
  public void setRenderMode(final RenderMode value) {
    renderMode = value;
  }

  /** If TRUE, the entity instances will be resizable horizontally */
  @JsonProperty("resizableX")
  public boolean getResizableX() {
    return resizableX;
  }

  @JsonProperty("resizableX")
  public void setResizableX(final boolean value) {
    resizableX = value;
  }

  /** If TRUE, the entity instances will be resizable vertically */
  @JsonProperty("resizableY")
  public boolean getResizableY() {
    return resizableY;
  }

  @JsonProperty("resizableY")
  public void setResizableY(final boolean value) {
    resizableY = value;
  }

  /** Display entity name in editor */
  @JsonProperty("showName")
  public boolean getShowName() {
    return showName;
  }

  @JsonProperty("showName")
  public void setShowName(final boolean value) {
    showName = value;
  }

  /** An array of strings that classifies this entity */
  @JsonProperty("tags")
  public String[] getTags() {
    return tags;
  }

  @JsonProperty("tags")
  public void setTags(final String[] value) {
    tags = value;
  }

  /**
   * **WARNING**: this deprecated value is no longer exported since version 1.2.0 Replaced by:
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

  @JsonProperty("tileOpacity")
  public double getTileOpacity() {
    return tileOpacity;
  }

  @JsonProperty("tileOpacity")
  public void setTileOpacity(final double value) {
    tileOpacity = value;
  }

  /** An object representing a rectangle from an existing Tileset */
  @JsonProperty("tileRect")
  public TilesetRectangle getTileRect() {
    return tileRect;
  }

  @JsonProperty("tileRect")
  public void setTileRect(final TilesetRectangle value) {
    tileRect = value;
  }

  /**
   * An enum describing how the the Entity tile is rendered inside the Entity bounds. Possible
   * values: `Cover`, `FitInside`, `Repeat`, `Stretch`, `FullSizeCropped`, `FullSizeUncropped`,
   * `NineSlice`
   */
  @JsonProperty("tileRenderMode")
  public TileRenderMode getTileRenderMode() {
    return tileRenderMode;
  }

  @JsonProperty("tileRenderMode")
  public void setTileRenderMode(final TileRenderMode value) {
    tileRenderMode = value;
  }

  /** Tileset ID used for optional tile display */
  @JsonProperty("tilesetId")
  public Integer getTilesetId() {
    return tilesetId;
  }

  @JsonProperty("tilesetId")
  public void setTilesetId(final Integer value) {
    tilesetId = value;
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

  /** Pixel width */
  @JsonProperty("width")
  public int getWidth() {
    return width;
  }

  @JsonProperty("width")
  public void setWidth(final int value) {
    width = value;
  }
}
