package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

/** Defines how tileIds array is used Possible values: `Single`, `Stamp` */
public enum TileMode {
  SINGLE,
  STAMP;

  @JsonValue
  public String toValue() {
    switch (this) {
      case SINGLE:
        return "Single";
      case STAMP:
        return "Stamp";
    }
    return null;
  }

  @JsonCreator
  public static TileMode forValue(final String value) throws IOException {
    if (value.equals("Single")) return SINGLE;
    if (value.equals("Stamp")) return STAMP;
    throw new IOException("Cannot deserialize TileMode");
  }
}
