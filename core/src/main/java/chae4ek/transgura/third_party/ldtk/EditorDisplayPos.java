package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

/** Possible values: `Above`, `Center`, `Beneath` */
public enum EditorDisplayPos {
  ABOVE,
  BENEATH,
  CENTER;

  @JsonValue
  public String toValue() {
    switch (this) {
      case ABOVE:
        return "Above";
      case BENEATH:
        return "Beneath";
      case CENTER:
        return "Center";
    }
    return null;
  }

  @JsonCreator
  public static EditorDisplayPos forValue(final String value) throws IOException {
    if (value.equals("Above")) return ABOVE;
    if (value.equals("Beneath")) return BENEATH;
    if (value.equals("Center")) return CENTER;
    throw new IOException("Cannot deserialize EditorDisplayPos");
  }
}
