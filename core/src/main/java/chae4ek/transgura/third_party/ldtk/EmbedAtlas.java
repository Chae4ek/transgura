package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

public enum EmbedAtlas {
  LDTK_ICONS;

  @JsonValue
  public String toValue() {
    switch (this) {
      case LDTK_ICONS:
        return "LdtkIcons";
    }
    return null;
  }

  @JsonCreator
  public static EmbedAtlas forValue(final String value) throws IOException {
    if (value.equals("LdtkIcons")) return LDTK_ICONS;
    throw new IOException("Cannot deserialize EmbedAtlas");
  }
}
