package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

/** Possible values: `Rectangle`, `Ellipse`, `Tile`, `Cross` */
public enum RenderMode {
  CROSS,
  ELLIPSE,
  RECTANGLE,
  TILE;

  @JsonValue
  public String toValue() {
    switch (this) {
      case CROSS:
        return "Cross";
      case ELLIPSE:
        return "Ellipse";
      case RECTANGLE:
        return "Rectangle";
      case TILE:
        return "Tile";
    }
    return null;
  }

  @JsonCreator
  public static RenderMode forValue(final String value) throws IOException {
    if (value.equals("Cross")) return CROSS;
    if (value.equals("Ellipse")) return ELLIPSE;
    if (value.equals("Rectangle")) return RECTANGLE;
    if (value.equals("Tile")) return TILE;
    throw new IOException("Cannot deserialize RenderMode");
  }
}
