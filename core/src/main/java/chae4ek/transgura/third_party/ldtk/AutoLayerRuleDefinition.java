package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/**
 * This complex section isn't meant to be used by game devs at all, as these rules are completely
 * resolved internally by the editor before any saving. You should just ignore this part.
 */
public class AutoLayerRuleDefinition {
  private boolean active;
  private double alpha;
  private boolean breakOnMatch;
  private double chance;
  private Checker checker;
  private boolean flipX;
  private boolean flipY;
  private Integer outOfBoundsValue;
  private int[] pattern;
  private boolean perlinActive;
  private double perlinOctaves;
  private double perlinScale;
  private double perlinSeed;
  private double pivotX;
  private double pivotY;
  private int size;
  private int[] tileIds;
  private TileMode tileMode;
  private int tileRandomXMax;
  private int tileRandomXMin;
  private int tileRandomYMax;
  private int tileRandomYMin;
  private int tileXOffset;
  private int tileYOffset;
  private int uid;
  private int xModulo;
  private int xOffset;
  private int yModulo;
  private int yOffset;

  /** If FALSE, the rule effect isn't applied, and no tiles are generated. */
  @JsonProperty("active")
  public boolean getActive() {
    return active;
  }

  @JsonProperty("active")
  public void setActive(final boolean value) {
    active = value;
  }

  @JsonProperty("alpha")
  public double getAlpha() {
    return alpha;
  }

  @JsonProperty("alpha")
  public void setAlpha(final double value) {
    alpha = value;
  }

  /**
   * When TRUE, the rule will prevent other rules to be applied in the same cell if it matches (TRUE
   * by default).
   */
  @JsonProperty("breakOnMatch")
  public boolean getBreakOnMatch() {
    return breakOnMatch;
  }

  @JsonProperty("breakOnMatch")
  public void setBreakOnMatch(final boolean value) {
    breakOnMatch = value;
  }

  /** Chances for this rule to be applied (0 to 1) */
  @JsonProperty("chance")
  public double getChance() {
    return chance;
  }

  @JsonProperty("chance")
  public void setChance(final double value) {
    chance = value;
  }

  /** Checker mode Possible values: `None`, `Horizontal`, `Vertical` */
  @JsonProperty("checker")
  public Checker getChecker() {
    return checker;
  }

  @JsonProperty("checker")
  public void setChecker(final Checker value) {
    checker = value;
  }

  /** If TRUE, allow rule to be matched by flipping its pattern horizontally */
  @JsonProperty("flipX")
  public boolean getFlipX() {
    return flipX;
  }

  @JsonProperty("flipX")
  public void setFlipX(final boolean value) {
    flipX = value;
  }

  /** If TRUE, allow rule to be matched by flipping its pattern vertically */
  @JsonProperty("flipY")
  public boolean getFlipY() {
    return flipY;
  }

  @JsonProperty("flipY")
  public void setFlipY(final boolean value) {
    flipY = value;
  }

  /** Default IntGrid value when checking cells outside of level bounds */
  @JsonProperty("outOfBoundsValue")
  public Integer getOutOfBoundsValue() {
    return outOfBoundsValue;
  }

  @JsonProperty("outOfBoundsValue")
  public void setOutOfBoundsValue(final Integer value) {
    outOfBoundsValue = value;
  }

  /** Rule pattern (size x size) */
  @JsonProperty("pattern")
  public int[] getPattern() {
    return pattern;
  }

  @JsonProperty("pattern")
  public void setPattern(final int[] value) {
    pattern = value;
  }

  /** If TRUE, enable Perlin filtering to only apply rule on specific random area */
  @JsonProperty("perlinActive")
  public boolean getPerlinActive() {
    return perlinActive;
  }

  @JsonProperty("perlinActive")
  public void setPerlinActive(final boolean value) {
    perlinActive = value;
  }

  @JsonProperty("perlinOctaves")
  public double getPerlinOctaves() {
    return perlinOctaves;
  }

  @JsonProperty("perlinOctaves")
  public void setPerlinOctaves(final double value) {
    perlinOctaves = value;
  }

  @JsonProperty("perlinScale")
  public double getPerlinScale() {
    return perlinScale;
  }

  @JsonProperty("perlinScale")
  public void setPerlinScale(final double value) {
    perlinScale = value;
  }

  @JsonProperty("perlinSeed")
  public double getPerlinSeed() {
    return perlinSeed;
  }

  @JsonProperty("perlinSeed")
  public void setPerlinSeed(final double value) {
    perlinSeed = value;
  }

  /** X pivot of a tile stamp (0-1) */
  @JsonProperty("pivotX")
  public double getPivotX() {
    return pivotX;
  }

  @JsonProperty("pivotX")
  public void setPivotX(final double value) {
    pivotX = value;
  }

  /** Y pivot of a tile stamp (0-1) */
  @JsonProperty("pivotY")
  public double getPivotY() {
    return pivotY;
  }

  @JsonProperty("pivotY")
  public void setPivotY(final double value) {
    pivotY = value;
  }

  /** Pattern width & height. Should only be 1,3,5 or 7. */
  @JsonProperty("size")
  public int getSize() {
    return size;
  }

  @JsonProperty("size")
  public void setSize(final int value) {
    size = value;
  }

  /** Array of all the tile IDs. They are used randomly or as stamps, based on `tileMode` value. */
  @JsonProperty("tileIds")
  public int[] getTileIds() {
    return tileIds;
  }

  @JsonProperty("tileIds")
  public void setTileIds(final int[] value) {
    tileIds = value;
  }

  /** Defines how tileIds array is used Possible values: `Single`, `Stamp` */
  @JsonProperty("tileMode")
  public TileMode getTileMode() {
    return tileMode;
  }

  @JsonProperty("tileMode")
  public void setTileMode(final TileMode value) {
    tileMode = value;
  }

  /** Max random offset for X tile pos */
  @JsonProperty("tileRandomXMax")
  public int getTileRandomXMax() {
    return tileRandomXMax;
  }

  @JsonProperty("tileRandomXMax")
  public void setTileRandomXMax(final int value) {
    tileRandomXMax = value;
  }

  /** Min random offset for X tile pos */
  @JsonProperty("tileRandomXMin")
  public int getTileRandomXMin() {
    return tileRandomXMin;
  }

  @JsonProperty("tileRandomXMin")
  public void setTileRandomXMin(final int value) {
    tileRandomXMin = value;
  }

  /** Max random offset for Y tile pos */
  @JsonProperty("tileRandomYMax")
  public int getTileRandomYMax() {
    return tileRandomYMax;
  }

  @JsonProperty("tileRandomYMax")
  public void setTileRandomYMax(final int value) {
    tileRandomYMax = value;
  }

  /** Min random offset for Y tile pos */
  @JsonProperty("tileRandomYMin")
  public int getTileRandomYMin() {
    return tileRandomYMin;
  }

  @JsonProperty("tileRandomYMin")
  public void setTileRandomYMin(final int value) {
    tileRandomYMin = value;
  }

  /** Tile X offset */
  @JsonProperty("tileXOffset")
  public int getTileXOffset() {
    return tileXOffset;
  }

  @JsonProperty("tileXOffset")
  public void setTileXOffset(final int value) {
    tileXOffset = value;
  }

  /** Tile Y offset */
  @JsonProperty("tileYOffset")
  public int getTileYOffset() {
    return tileYOffset;
  }

  @JsonProperty("tileYOffset")
  public void setTileYOffset(final int value) {
    tileYOffset = value;
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

  /** X cell coord modulo */
  @JsonProperty("xModulo")
  public int getXModulo() {
    return xModulo;
  }

  @JsonProperty("xModulo")
  public void setXModulo(final int value) {
    xModulo = value;
  }

  /** X cell start offset */
  @JsonProperty("xOffset")
  public int getXOffset() {
    return xOffset;
  }

  @JsonProperty("xOffset")
  public void setXOffset(final int value) {
    xOffset = value;
  }

  /** Y cell coord modulo */
  @JsonProperty("yModulo")
  public int getYModulo() {
    return yModulo;
  }

  @JsonProperty("yModulo")
  public void setYModulo(final int value) {
    yModulo = value;
  }

  /** Y cell start offset */
  @JsonProperty("yOffset")
  public int getYOffset() {
    return yOffset;
  }

  @JsonProperty("yOffset")
  public void setYOffset(final int value) {
    yOffset = value;
  }
}
