package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class EntityInstance {
  private int[] grid;
  private String identifier;
  private double[] pivot;
  private String smartColor;
  private String[] tags;
  private TilesetRectangle tile;
  private int worldX;
  private int worldY;
  private int defUid;
  private FieldInstance[] fieldInstances;
  private int height;
  private String iid;
  private int[] px;
  private int width;

  /** Grid-based coordinates (`[x,y]` format) */
  @JsonProperty("__grid")
  public int[] getGrid() {
    return grid;
  }

  @JsonProperty("__grid")
  public void setGrid(final int[] value) {
    grid = value;
  }

  /** Entity definition identifier */
  @JsonProperty("__identifier")
  public String getIdentifier() {
    return identifier;
  }

  @JsonProperty("__identifier")
  public void setIdentifier(final String value) {
    identifier = value;
  }

  /** Pivot coordinates (`[x,y]` format, values are from 0 to 1) of the Entity */
  @JsonProperty("__pivot")
  public double[] getPivot() {
    return pivot;
  }

  @JsonProperty("__pivot")
  public void setPivot(final double[] value) {
    pivot = value;
  }

  /**
   * The entity "smart" color, guessed from either Entity definition, or one its field instances.
   */
  @JsonProperty("__smartColor")
  public String getSmartColor() {
    return smartColor;
  }

  @JsonProperty("__smartColor")
  public void setSmartColor(final String value) {
    smartColor = value;
  }

  /** Array of tags defined in this Entity definition */
  @JsonProperty("__tags")
  public String[] getTags() {
    return tags;
  }

  @JsonProperty("__tags")
  public void setTags(final String[] value) {
    tags = value;
  }

  /**
   * Optional TilesetRect used to display this entity (it could either be the default Entity tile,
   * or some tile provided by a field value, like an Enum).
   */
  @JsonProperty("__tile")
  public TilesetRectangle getTile() {
    return tile;
  }

  @JsonProperty("__tile")
  public void setTile(final TilesetRectangle value) {
    tile = value;
  }

  /** X world coordinate in pixels */
  @JsonProperty("__worldX")
  public int getWorldX() {
    return worldX;
  }

  @JsonProperty("__worldX")
  public void setWorldX(final int value) {
    worldX = value;
  }

  /** Y world coordinate in pixels */
  @JsonProperty("__worldY")
  public int getWorldY() {
    return worldY;
  }

  @JsonProperty("__worldY")
  public void setWorldY(final int value) {
    worldY = value;
  }

  /** Reference of the **Entity definition** UID */
  @JsonProperty("defUid")
  public int getDefUid() {
    return defUid;
  }

  @JsonProperty("defUid")
  public void setDefUid(final int value) {
    defUid = value;
  }

  /** An array of all custom fields and their values. */
  @JsonProperty("fieldInstances")
  public FieldInstance[] getFieldInstances() {
    return fieldInstances;
  }

  @JsonProperty("fieldInstances")
  public void setFieldInstances(final FieldInstance[] value) {
    fieldInstances = value;
  }

  /**
   * Entity height in pixels. For non-resizable entities, it will be the same as Entity definition.
   */
  @JsonProperty("height")
  public int getHeight() {
    return height;
  }

  @JsonProperty("height")
  public void setHeight(final int value) {
    height = value;
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
   * Pixel coordinates (`[x,y]` format) in current level coordinate space. Don't forget optional
   * layer offsets, if they exist!
   */
  @JsonProperty("px")
  public int[] getPx() {
    return px;
  }

  @JsonProperty("px")
  public void setPx(final int[] value) {
    px = value;
  }

  /**
   * Entity width in pixels. For non-resizable entities, it will be the same as Entity definition.
   */
  @JsonProperty("width")
  public int getWidth() {
    return width;
  }

  @JsonProperty("width")
  public void setWidth(final int value) {
    width = value;
  }
}
