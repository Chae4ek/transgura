package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** Nearby level info */
public class NeighbourLevel {
  private String dir;
  private String levelIid;
  private Integer levelUid;

  /**
   * A single lowercase character tipping on the level location (`n`orth, `s`outh, `w`est, `e`ast).
   */
  @JsonProperty("dir")
  public String getDir() {
    return dir;
  }

  @JsonProperty("dir")
  public void setDir(final String value) {
    dir = value;
  }

  /** Neighbour Instance Identifier */
  @JsonProperty("levelIid")
  public String getLevelIid() {
    return levelIid;
  }

  @JsonProperty("levelIid")
  public void setLevelIid(final String value) {
    levelIid = value;
  }

  /**
   * **WARNING**: this deprecated value is no longer exported since version 1.2.0 Replaced by:
   * `levelIid`
   */
  @JsonProperty("levelUid")
  public Integer getLevelUid() {
    return levelUid;
  }

  @JsonProperty("levelUid")
  public void setLevelUid(final Integer value) {
    levelUid = value;
  }
}
