package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/**
 * **IMPORTANT**: this type is available as a preview. You can rely on it to update your importers,
 * for when it will be officially available. A World contains multiple levels, and it has its own
 * layout settings.
 */
public class World {
  private int defaultLevelHeight;
  private int defaultLevelWidth;
  private String identifier;
  private String iid;
  private Level[] levels;
  private int worldGridHeight;
  private int worldGridWidth;
  private WorldLayout worldLayout;

  /** Default new level height */
  @JsonProperty("defaultLevelHeight")
  public int getDefaultLevelHeight() {
    return defaultLevelHeight;
  }

  @JsonProperty("defaultLevelHeight")
  public void setDefaultLevelHeight(final int value) {
    defaultLevelHeight = value;
  }

  /** Default new level width */
  @JsonProperty("defaultLevelWidth")
  public int getDefaultLevelWidth() {
    return defaultLevelWidth;
  }

  @JsonProperty("defaultLevelWidth")
  public void setDefaultLevelWidth(final int value) {
    defaultLevelWidth = value;
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

  /** Unique instance identifer */
  @JsonProperty("iid")
  public String getIid() {
    return iid;
  }

  @JsonProperty("iid")
  public void setIid(final String value) {
    iid = value;
  }

  /**
   * All levels from this world. The order of this array is only relevant in `LinearHorizontal` and
   * `linearVertical` world layouts (see `worldLayout` value). Otherwise, you should refer to the
   * `worldX`,`worldY` coordinates of each Level.
   */
  @JsonProperty("levels")
  public Level[] getLevels() {
    return levels;
  }

  @JsonProperty("levels")
  public void setLevels(final Level[] value) {
    levels = value;
  }

  /** Height of the world grid in pixels. */
  @JsonProperty("worldGridHeight")
  public int getWorldGridHeight() {
    return worldGridHeight;
  }

  @JsonProperty("worldGridHeight")
  public void setWorldGridHeight(final int value) {
    worldGridHeight = value;
  }

  /** Width of the world grid in pixels. */
  @JsonProperty("worldGridWidth")
  public int getWorldGridWidth() {
    return worldGridWidth;
  }

  @JsonProperty("worldGridWidth")
  public void setWorldGridWidth(final int value) {
    worldGridWidth = value;
  }

  /**
   * An enum that describes how levels are organized in this project (ie. linearly or in a 2D
   * space). Possible values: `Free`, `GridVania`, `LinearHorizontal`, `LinearVertical`, `null`
   */
  @JsonProperty("worldLayout")
  public WorldLayout getWorldLayout() {
    return worldLayout;
  }

  @JsonProperty("worldLayout")
  public void setWorldLayout(final WorldLayout value) {
    worldLayout = value;
  }
}
