package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

public enum BgPos {
  CONTAIN,
  COVER,
  COVER_DIRTY,
  REPEAT,
  UNSCALED;

  @JsonValue
  public String toValue() {
    switch (this) {
      case CONTAIN:
        return "Contain";
      case COVER:
        return "Cover";
      case COVER_DIRTY:
        return "CoverDirty";
      case REPEAT:
        return "Repeat";
      case UNSCALED:
        return "Unscaled";
    }
    return null;
  }

  @JsonCreator
  public static BgPos forValue(final String value) throws IOException {
    if (value.equals("Contain")) return CONTAIN;
    if (value.equals("Cover")) return COVER;
    if (value.equals("CoverDirty")) return COVER_DIRTY;
    if (value.equals("Repeat")) return REPEAT;
    if (value.equals("Unscaled")) return UNSCALED;
    throw new IOException("Cannot deserialize BgPos");
  }
}
