package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class LdtkTableOfContentEntry {
  private String identifier;
  private ReferenceToAnEntityInstance[] instances;

  @JsonProperty("identifier")
  public String getIdentifier() {
    return identifier;
  }

  @JsonProperty("identifier")
  public void setIdentifier(final String value) {
    identifier = value;
  }

  @JsonProperty("instances")
  public ReferenceToAnEntityInstance[] getInstances() {
    return instances;
  }

  @JsonProperty("instances")
  public void setInstances(final ReferenceToAnEntityInstance[] value) {
    instances = value;
  }
}
