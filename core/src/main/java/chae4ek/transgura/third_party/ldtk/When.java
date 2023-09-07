package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

/** Possible values: `Manual`, `AfterLoad`, `BeforeSave`, `AfterSave` */
public enum When {
  AFTER_LOAD,
  AFTER_SAVE,
  BEFORE_SAVE,
  MANUAL;

  @JsonValue
  public String toValue() {
    switch (this) {
      case AFTER_LOAD:
        return "AfterLoad";
      case AFTER_SAVE:
        return "AfterSave";
      case BEFORE_SAVE:
        return "BeforeSave";
      case MANUAL:
        return "Manual";
    }
    return null;
  }

  @JsonCreator
  public static When forValue(final String value) throws IOException {
    if (value.equals("AfterLoad")) return AFTER_LOAD;
    if (value.equals("AfterSave")) return AFTER_SAVE;
    if (value.equals("BeforeSave")) return BEFORE_SAVE;
    if (value.equals("Manual")) return MANUAL;
    throw new IOException("Cannot deserialize When");
  }
}
