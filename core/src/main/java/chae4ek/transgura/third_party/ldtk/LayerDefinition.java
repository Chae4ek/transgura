package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class LayerDefinition {
  private String type;
  private AutoLayerRuleGroup[] autoRuleGroups;
  private Integer autoSourceLayerDefUid;
  private Integer autoTilesetDefUid;
  private boolean canSelectWhenInactive;
  private double displayOpacity;
  private String doc;
  private String[] excludedTags;
  private int gridSize;
  private int guideGridHei;
  private int guideGridWid;
  private boolean hideFieldsWhenInactive;
  private boolean hideInList;
  private String identifier;
  private double inactiveOpacity;
  private IntGridValueDefinition[] intGridValues;
  private double parallaxFactorX;
  private double parallaxFactorY;
  private boolean parallaxScaling;
  private int pxOffsetX;
  private int pxOffsetY;
  private boolean renderInWorldView;
  private String[] requiredTags;
  private double tilePivotX;
  private double tilePivotY;
  private Integer tilesetDefUid;
  private Type layerDefinitionType;
  private String uiColor;
  private int uid;

  /** Type of the layer (*IntGrid, Entities, Tiles or AutoLayer*) */
  @JsonProperty("__type")
  public String getType() {
    return type;
  }

  @JsonProperty("__type")
  public void setType(final String value) {
    type = value;
  }

  /** Contains all the auto-layer rule definitions. */
  @JsonProperty("autoRuleGroups")
  public AutoLayerRuleGroup[] getAutoRuleGroups() {
    return autoRuleGroups;
  }

  @JsonProperty("autoRuleGroups")
  public void setAutoRuleGroups(final AutoLayerRuleGroup[] value) {
    autoRuleGroups = value;
  }

  @JsonProperty("autoSourceLayerDefUid")
  public Integer getAutoSourceLayerDefUid() {
    return autoSourceLayerDefUid;
  }

  @JsonProperty("autoSourceLayerDefUid")
  public void setAutoSourceLayerDefUid(final Integer value) {
    autoSourceLayerDefUid = value;
  }

  /**
   * **WARNING**: this deprecated value is no longer exported since version 1.2.0 Replaced by:
   * `tilesetDefUid`
   */
  @JsonProperty("autoTilesetDefUid")
  public Integer getAutoTilesetDefUid() {
    return autoTilesetDefUid;
  }

  @JsonProperty("autoTilesetDefUid")
  public void setAutoTilesetDefUid(final Integer value) {
    autoTilesetDefUid = value;
  }

  /** Allow editor selections when the layer is not currently active. */
  @JsonProperty("canSelectWhenInactive")
  public boolean getCanSelectWhenInactive() {
    return canSelectWhenInactive;
  }

  @JsonProperty("canSelectWhenInactive")
  public void setCanSelectWhenInactive(final boolean value) {
    canSelectWhenInactive = value;
  }

  /** Opacity of the layer (0 to 1.0) */
  @JsonProperty("displayOpacity")
  public double getDisplayOpacity() {
    return displayOpacity;
  }

  @JsonProperty("displayOpacity")
  public void setDisplayOpacity(final double value) {
    displayOpacity = value;
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

  /** An array of tags to forbid some Entities in this layer */
  @JsonProperty("excludedTags")
  public String[] getExcludedTags() {
    return excludedTags;
  }

  @JsonProperty("excludedTags")
  public void setExcludedTags(final String[] value) {
    excludedTags = value;
  }

  /** Width and height of the grid in pixels */
  @JsonProperty("gridSize")
  public int getGridSize() {
    return gridSize;
  }

  @JsonProperty("gridSize")
  public void setGridSize(final int value) {
    gridSize = value;
  }

  /** Height of the optional "guide" grid in pixels */
  @JsonProperty("guideGridHei")
  public int getGuideGridHei() {
    return guideGridHei;
  }

  @JsonProperty("guideGridHei")
  public void setGuideGridHei(final int value) {
    guideGridHei = value;
  }

  /** Width of the optional "guide" grid in pixels */
  @JsonProperty("guideGridWid")
  public int getGuideGridWid() {
    return guideGridWid;
  }

  @JsonProperty("guideGridWid")
  public void setGuideGridWid(final int value) {
    guideGridWid = value;
  }

  @JsonProperty("hideFieldsWhenInactive")
  public boolean getHideFieldsWhenInactive() {
    return hideFieldsWhenInactive;
  }

  @JsonProperty("hideFieldsWhenInactive")
  public void setHideFieldsWhenInactive(final boolean value) {
    hideFieldsWhenInactive = value;
  }

  /** Hide the layer from the list on the side of the editor view. */
  @JsonProperty("hideInList")
  public boolean getHideInList() {
    return hideInList;
  }

  @JsonProperty("hideInList")
  public void setHideInList(final boolean value) {
    hideInList = value;
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

  /** Alpha of this layer when it is not the active one. */
  @JsonProperty("inactiveOpacity")
  public double getInactiveOpacity() {
    return inactiveOpacity;
  }

  @JsonProperty("inactiveOpacity")
  public void setInactiveOpacity(final double value) {
    inactiveOpacity = value;
  }

  /**
   * An array that defines extra optional info for each IntGrid value.<br>
   * WARNING: the array order is not related to actual IntGrid values! As user can re-order IntGrid
   * values freely, you may value "2" before value "1" in this array.
   */
  @JsonProperty("intGridValues")
  public IntGridValueDefinition[] getIntGridValues() {
    return intGridValues;
  }

  @JsonProperty("intGridValues")
  public void setIntGridValues(final IntGridValueDefinition[] value) {
    intGridValues = value;
  }

  /**
   * Parallax horizontal factor (from -1 to 1, defaults to 0) which affects the scrolling speed of
   * this layer, creating a fake 3D (parallax) effect.
   */
  @JsonProperty("parallaxFactorX")
  public double getParallaxFactorX() {
    return parallaxFactorX;
  }

  @JsonProperty("parallaxFactorX")
  public void setParallaxFactorX(final double value) {
    parallaxFactorX = value;
  }

  /**
   * Parallax vertical factor (from -1 to 1, defaults to 0) which affects the scrolling speed of
   * this layer, creating a fake 3D (parallax) effect.
   */
  @JsonProperty("parallaxFactorY")
  public double getParallaxFactorY() {
    return parallaxFactorY;
  }

  @JsonProperty("parallaxFactorY")
  public void setParallaxFactorY(final double value) {
    parallaxFactorY = value;
  }

  /** If true (default), a layer with a parallax factor will also be scaled up/down accordingly. */
  @JsonProperty("parallaxScaling")
  public boolean getParallaxScaling() {
    return parallaxScaling;
  }

  @JsonProperty("parallaxScaling")
  public void setParallaxScaling(final boolean value) {
    parallaxScaling = value;
  }

  /**
   * X offset of the layer, in pixels (IMPORTANT: this should be added to the `LayerInstance`
   * optional offset)
   */
  @JsonProperty("pxOffsetX")
  public int getPxOffsetX() {
    return pxOffsetX;
  }

  @JsonProperty("pxOffsetX")
  public void setPxOffsetX(final int value) {
    pxOffsetX = value;
  }

  /**
   * Y offset of the layer, in pixels (IMPORTANT: this should be added to the `LayerInstance`
   * optional offset)
   */
  @JsonProperty("pxOffsetY")
  public int getPxOffsetY() {
    return pxOffsetY;
  }

  @JsonProperty("pxOffsetY")
  public void setPxOffsetY(final int value) {
    pxOffsetY = value;
  }

  /**
   * If TRUE, the content of this layer will be used when rendering levels in a simplified way for
   * the world view
   */
  @JsonProperty("renderInWorldView")
  public boolean getRenderInWorldView() {
    return renderInWorldView;
  }

  @JsonProperty("renderInWorldView")
  public void setRenderInWorldView(final boolean value) {
    renderInWorldView = value;
  }

  /** An array of tags to filter Entities that can be added to this layer */
  @JsonProperty("requiredTags")
  public String[] getRequiredTags() {
    return requiredTags;
  }

  @JsonProperty("requiredTags")
  public void setRequiredTags(final String[] value) {
    requiredTags = value;
  }

  /**
   * If the tiles are smaller or larger than the layer grid, the pivot value will be used to
   * position the tile relatively its grid cell.
   */
  @JsonProperty("tilePivotX")
  public double getTilePivotX() {
    return tilePivotX;
  }

  @JsonProperty("tilePivotX")
  public void setTilePivotX(final double value) {
    tilePivotX = value;
  }

  /**
   * If the tiles are smaller or larger than the layer grid, the pivot value will be used to
   * position the tile relatively its grid cell.
   */
  @JsonProperty("tilePivotY")
  public double getTilePivotY() {
    return tilePivotY;
  }

  @JsonProperty("tilePivotY")
  public void setTilePivotY(final double value) {
    tilePivotY = value;
  }

  /**
   * Reference to the default Tileset UID being used by this layer definition.<br>
   * **WARNING**: some layer *instances* might use a different tileset. So most of the time, you
   * should probably use the `__tilesetDefUid` value found in layer instances.<br>
   * Note: since version 1.0.0, the old `autoTilesetDefUid` was removed and merged into this value.
   */
  @JsonProperty("tilesetDefUid")
  public Integer getTilesetDefUid() {
    return tilesetDefUid;
  }

  @JsonProperty("tilesetDefUid")
  public void setTilesetDefUid(final Integer value) {
    tilesetDefUid = value;
  }

  /** Type of the layer as Haxe Enum Possible values: `IntGrid`, `Entities`, `Tiles`, `AutoLayer` */
  @JsonProperty("type")
  public Type getLayerDefinitionType() {
    return layerDefinitionType;
  }

  @JsonProperty("type")
  public void setLayerDefinitionType(final Type value) {
    layerDefinitionType = value;
  }

  /** User defined color for the UI */
  @JsonProperty("uiColor")
  public String getuiColor() {
    return uiColor;
  }

  @JsonProperty("uiColor")
  public void setuiColor(final String value) {
    uiColor = value;
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
}
