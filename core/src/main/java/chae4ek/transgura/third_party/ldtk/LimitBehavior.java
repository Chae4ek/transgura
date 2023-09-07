package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

/** Possible values: `DiscardOldOnes`, `PreventAdding`, `MoveLastOne` */
public enum LimitBehavior {
  DISCARD_OLD_ONES,
  MOVE_LAST_ONE,
  PREVENT_ADDING;

  @JsonValue
  public String toValue() {
    switch (this) {
      case DISCARD_OLD_ONES:
        return "DiscardOldOnes";
      case MOVE_LAST_ONE:
        return "MoveLastOne";
      case PREVENT_ADDING:
        return "PreventAdding";
    }
    return null;
  }

  @JsonCreator
  public static LimitBehavior forValue(final String value) throws IOException {
    if (value.equals("DiscardOldOnes")) return DISCARD_OLD_ONES;
    if (value.equals("MoveLastOne")) return MOVE_LAST_ONE;
    if (value.equals("PreventAdding")) return PREVENT_ADDING;
    throw new IOException("Cannot deserialize LimitBehavior");
  }
}
