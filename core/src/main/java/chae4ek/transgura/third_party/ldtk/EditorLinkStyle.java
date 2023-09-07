package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

/** Possible values: `ZigZag`, `StraightArrow`, `CurvedArrow`, `ArrowsLine`, `DashedLine` */
public enum EditorLinkStyle {
  ARROWS_LINE,
  CURVED_ARROW,
  DASHED_LINE,
  STRAIGHT_ARROW,
  ZIG_ZAG;

  @JsonValue
  public String toValue() {
    switch (this) {
      case ARROWS_LINE:
        return "ArrowsLine";
      case CURVED_ARROW:
        return "CurvedArrow";
      case DASHED_LINE:
        return "DashedLine";
      case STRAIGHT_ARROW:
        return "StraightArrow";
      case ZIG_ZAG:
        return "ZigZag";
    }
    return null;
  }

  @JsonCreator
  public static EditorLinkStyle forValue(final String value) throws IOException {
    if (value.equals("ArrowsLine")) return ARROWS_LINE;
    if (value.equals("CurvedArrow")) return CURVED_ARROW;
    if (value.equals("DashedLine")) return DASHED_LINE;
    if (value.equals("StraightArrow")) return STRAIGHT_ARROW;
    if (value.equals("ZigZag")) return ZIG_ZAG;
    throw new IOException("Cannot deserialize EditorLinkStyle");
  }
}
