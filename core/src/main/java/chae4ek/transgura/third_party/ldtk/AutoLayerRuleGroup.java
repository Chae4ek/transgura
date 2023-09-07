package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class AutoLayerRuleGroup {
  private boolean active;
  private Boolean collapsed;
  private boolean isOptional;
  private String name;
  private AutoLayerRuleDefinition[] rules;
  private int uid;
  private boolean usesWizard;

  @JsonProperty("active")
  public boolean getActive() {
    return active;
  }

  @JsonProperty("active")
  public void setActive(final boolean value) {
    active = value;
  }

  /** *This field was removed in 1.0.0 and should no longer be used.* */
  @JsonProperty("collapsed")
  public Boolean getCollapsed() {
    return collapsed;
  }

  @JsonProperty("collapsed")
  public void setCollapsed(final Boolean value) {
    collapsed = value;
  }

  @JsonProperty("isOptional")
  public boolean getIsOptional() {
    return isOptional;
  }

  @JsonProperty("isOptional")
  public void setIsOptional(final boolean value) {
    isOptional = value;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(final String value) {
    name = value;
  }

  @JsonProperty("rules")
  public AutoLayerRuleDefinition[] getRules() {
    return rules;
  }

  @JsonProperty("rules")
  public void setRules(final AutoLayerRuleDefinition[] value) {
    rules = value;
  }

  @JsonProperty("uid")
  public int getUid() {
    return uid;
  }

  @JsonProperty("uid")
  public void setUid(final int value) {
    uid = value;
  }

  @JsonProperty("usesWizard")
  public boolean getUsesWizard() {
    return usesWizard;
  }

  @JsonProperty("usesWizard")
  public void setUsesWizard(final boolean value) {
    usesWizard = value;
  }
}
