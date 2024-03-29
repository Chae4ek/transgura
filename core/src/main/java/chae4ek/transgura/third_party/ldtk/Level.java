package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/**
 * This section contains all the level data. It can be found in 2 distinct forms, depending on
 * Project current settings: - If "*Separate level files*" is **disabled** (default): full level
 * data is *embedded* inside the main Project JSON file, - If "*Separate level files*" is
 * **enabled**: level data is stored in *separate* standalone `.ldtkl` files (one per level). In
 * this case, the main Project JSON file will still contain most level data, except heavy sections,
 * like the `layerInstances` array (which will be null). The `externalRelPath` string points to the
 * `ldtkl` file. A `ldtkl` file is just a JSON file containing exactly what is described below.
 */
public class Level {
  private String bgColor;
  private LevelBackgroundPosition bgPos;
  private NeighbourLevel[] neighbours;
  private String smartColor;
  private String levelBgColor;
  private double bgPivotX;
  private double bgPivotY;
  private BgPos levelBgPos;
  private String bgRelPath;
  private String externalRelPath;
  private FieldInstance[] fieldInstances;
  private String identifier;
  private String iid;
  private LayerInstance[] layerInstances;
  private int pxHei;
  private int pxWid;
  private int uid;
  private boolean useAutoIdentifier;
  private int worldDepth;
  private int worldX;
  private int worldY;

  /**
   * Background color of the level (same as `bgColor`, except the default value is automatically
   * used here if its value is `null`)
   */
  @JsonProperty("__bgColor")
  public String getBgColor() {
    return bgColor;
  }

  @JsonProperty("__bgColor")
  public void setBgColor(final String value) {
    bgColor = value;
  }

  /** Position informations of the background image, if there is one. */
  @JsonProperty("__bgPos")
  public LevelBackgroundPosition getBgPos() {
    return bgPos;
  }

  @JsonProperty("__bgPos")
  public void setBgPos(final LevelBackgroundPosition value) {
    bgPos = value;
  }

  /**
   * An array listing all other levels touching this one on the world map.<br>
   * Only relevant for world layouts where level spatial positioning is manual (ie. GridVania,
   * Free). For Horizontal and Vertical layouts, this array is always empty.
   */
  @JsonProperty("__neighbours")
  public NeighbourLevel[] getNeighbours() {
    return neighbours;
  }

  @JsonProperty("__neighbours")
  public void setNeighbours(final NeighbourLevel[] value) {
    neighbours = value;
  }

  /**
   * The "guessed" color for this level in the editor, decided using either the background color or
   * an existing custom field.
   */
  @JsonProperty("__smartColor")
  public String getSmartColor() {
    return smartColor;
  }

  @JsonProperty("__smartColor")
  public void setSmartColor(final String value) {
    smartColor = value;
  }

  /** Background color of the level. If `null`, the project `defaultLevelBgColor` should be used. */
  @JsonProperty("bgColor")
  public String getLevelBgColor() {
    return levelBgColor;
  }

  @JsonProperty("bgColor")
  public void setLevelBgColor(final String value) {
    levelBgColor = value;
  }

  /** Background image X pivot (0-1) */
  @JsonProperty("bgPivotX")
  public double getBgPivotX() {
    return bgPivotX;
  }

  @JsonProperty("bgPivotX")
  public void setBgPivotX(final double value) {
    bgPivotX = value;
  }

  /** Background image Y pivot (0-1) */
  @JsonProperty("bgPivotY")
  public double getBgPivotY() {
    return bgPivotY;
  }

  @JsonProperty("bgPivotY")
  public void setBgPivotY(final double value) {
    bgPivotY = value;
  }

  /**
   * An enum defining the way the background image (if any) is positioned on the level. See
   * `__bgPos` for resulting position info. Possible values: &lt;`null`&gt;, `Unscaled`, `Contain`,
   * `Cover`, `CoverDirty`, `Repeat`
   */
  @JsonProperty("bgPos")
  public BgPos getLevelBgPos() {
    return levelBgPos;
  }

  @JsonProperty("bgPos")
  public void setLevelBgPos(final BgPos value) {
    levelBgPos = value;
  }

  /** The *optional* relative path to the level background image. */
  @JsonProperty("bgRelPath")
  public String getBgRelPath() {
    return bgRelPath;
  }

  @JsonProperty("bgRelPath")
  public void setBgRelPath(final String value) {
    bgRelPath = value;
  }

  /**
   * This value is not null if the project option "*Save levels separately*" is enabled. In this
   * case, this **relative** path points to the level Json file.
   */
  @JsonProperty("externalRelPath")
  public String getExternalRelPath() {
    return externalRelPath;
  }

  @JsonProperty("externalRelPath")
  public void setExternalRelPath(final String value) {
    externalRelPath = value;
  }

  /** An array containing this level custom field values. */
  @JsonProperty("fieldInstances")
  public FieldInstance[] getFieldInstances() {
    return fieldInstances;
  }

  @JsonProperty("fieldInstances")
  public void setFieldInstances(final FieldInstance[] value) {
    fieldInstances = value;
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

  /** Unique instance identifier */
  @JsonProperty("iid")
  public String getIid() {
    return iid;
  }

  @JsonProperty("iid")
  public void setIid(final String value) {
    iid = value;
  }

  /**
   * An array containing all Layer instances. **IMPORTANT**: if the project option "*Save levels
   * separately*" is enabled, this field will be `null`.<br>
   * This array is **sorted in display order**: the 1st layer is the top-most and the last is
   * behind.
   */
  @JsonProperty("layerInstances")
  public LayerInstance[] getLayerInstances() {
    return layerInstances;
  }

  @JsonProperty("layerInstances")
  public void setLayerInstances(final LayerInstance[] value) {
    layerInstances = value;
  }

  /** Height of the level in pixels */
  @JsonProperty("pxHei")
  public int getPxHei() {
    return pxHei;
  }

  @JsonProperty("pxHei")
  public void setPxHei(final int value) {
    pxHei = value;
  }

  /** Width of the level in pixels */
  @JsonProperty("pxWid")
  public int getPxWid() {
    return pxWid;
  }

  @JsonProperty("pxWid")
  public void setPxWid(final int value) {
    pxWid = value;
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

  /**
   * If TRUE, the level identifier will always automatically use the naming pattern as defined in
   * `Project.levelNamePattern`. Becomes FALSE if the identifier is manually modified by user.
   */
  @JsonProperty("useAutoIdentifier")
  public boolean getUseAutoIdentifier() {
    return useAutoIdentifier;
  }

  @JsonProperty("useAutoIdentifier")
  public void setUseAutoIdentifier(final boolean value) {
    useAutoIdentifier = value;
  }

  /**
   * Index that represents the "depth" of the level in the world. Default is 0, greater means
   * "above", lower means "below".<br>
   * This value is mostly used for display only and is intended to make stacking of levels easier to
   * manage.
   */
  @JsonProperty("worldDepth")
  public int getWorldDepth() {
    return worldDepth;
  }

  @JsonProperty("worldDepth")
  public void setWorldDepth(final int value) {
    worldDepth = value;
  }

  /**
   * World X coordinate in pixels.<br>
   * Only relevant for world layouts where level spatial positioning is manual (ie. GridVania,
   * Free). For Horizontal and Vertical layouts, the value is always -1 here.
   */
  @JsonProperty("worldX")
  public int getWorldX() {
    return worldX;
  }

  @JsonProperty("worldX")
  public void setWorldX(final int value) {
    worldX = value;
  }

  /**
   * World Y coordinate in pixels.<br>
   * Only relevant for world layouts where level spatial positioning is manual (ie. GridVania,
   * Free). For Horizontal and Vertical layouts, the value is always -1 here.
   */
  @JsonProperty("worldY")
  public int getWorldY() {
    return worldY;
  }

  @JsonProperty("worldY")
  public void setWorldY(final int value) {
    worldY = value;
  }
}
