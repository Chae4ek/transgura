package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

public class LdtkCustomCommand {
  private String command;
  private When when;

  @JsonProperty("command")
  public String getCommand() {
    return command;
  }

  @JsonProperty("command")
  public void setCommand(final String value) {
    command = value;
  }

  /** Possible values: `Manual`, `AfterLoad`, `BeforeSave`, `AfterSave` */
  @JsonProperty("when")
  public When getWhen() {
    return when;
  }

  @JsonProperty("when")
  public void setWhen(final When value) {
    when = value;
  }
}
