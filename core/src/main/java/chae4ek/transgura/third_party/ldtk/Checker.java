package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

/** Checker mode Possible values: `None`, `Horizontal`, `Vertical` */
public enum Checker {
  HORIZONTAL,
  NONE,
  VERTICAL;

  @JsonValue
  public String toValue() {
    switch (this) {
      case HORIZONTAL:
        return "Horizontal";
      case NONE:
        return "None";
      case VERTICAL:
        return "Vertical";
    }
    return null;
  }

  @JsonCreator
  public static Checker forValue(final String value) throws IOException {
    if (value.equals("Horizontal")) return HORIZONTAL;
    if (value.equals("None")) return NONE;
    if (value.equals("Vertical")) return VERTICAL;
    throw new IOException("Cannot deserialize Checker");
  }
}
