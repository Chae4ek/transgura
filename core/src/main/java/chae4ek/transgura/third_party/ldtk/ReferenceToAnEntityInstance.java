package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** This object describes the "location" of an Entity instance in the project worlds. */
public class ReferenceToAnEntityInstance {
  private String entityIid;
  private String layerIid;
  private String levelIid;
  private String worldIid;

  /** IID of the refered EntityInstance */
  @JsonProperty("entityIid")
  public String getEntityIid() {
    return entityIid;
  }

  @JsonProperty("entityIid")
  public void setEntityIid(final String value) {
    entityIid = value;
  }

  /** IID of the LayerInstance containing the refered EntityInstance */
  @JsonProperty("layerIid")
  public String getLayerIid() {
    return layerIid;
  }

  @JsonProperty("layerIid")
  public void setLayerIid(final String value) {
    layerIid = value;
  }

  /** IID of the Level containing the refered EntityInstance */
  @JsonProperty("levelIid")
  public String getLevelIid() {
    return levelIid;
  }

  @JsonProperty("levelIid")
  public void setLevelIid(final String value) {
    levelIid = value;
  }

  /** IID of the World containing the refered EntityInstance */
  @JsonProperty("worldIid")
  public String getWorldIid() {
    return worldIid;
  }

  @JsonProperty("worldIid")
  public void setWorldIid(final String value) {
    worldIid = value;
  }
}
