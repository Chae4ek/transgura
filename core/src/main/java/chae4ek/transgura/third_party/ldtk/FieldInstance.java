package chae4ek.transgura.third_party.ldtk;

import chae4ek.transgura.third_party.ldtk.customdes.FieldInstanceDes;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FieldInstanceDes.class)
public class FieldInstance {
  private String identifier;
  private TilesetRectangle tile;
  private String type;
  private Object value;
  private int defUid;
  private Object[] realEditorValues;

  /** Field definition identifier */
  @JsonProperty("__identifier")
  public String getIdentifier() {
    return identifier;
  }

  @JsonProperty("__identifier")
  public void setIdentifier(final String value) {
    identifier = value;
  }

  /**
   * Optional TilesetRect used to display this field (this can be the field own Tile, or some other
   * Tile guessed from the value, like an Enum).
   */
  @JsonProperty("__tile")
  public TilesetRectangle getTile() {
    return tile;
  }

  @JsonProperty("__tile")
  public void setTile(final TilesetRectangle value) {
    tile = value;
  }

  /**
   * Type of the field, such as `Int`, `Float`, `String`, `Enum(my_enum_name)`, `Bool`, etc.<br>
   * NOTE: if you enable the advanced option **Use Multilines type**, you will have "*Multilines*"
   * instead of "*String*" when relevant.
   */
  @JsonProperty("__type")
  public String getType() {
    return type;
  }

  @JsonProperty("__type")
  public void setType(final String value) {
    type = value;
  }

  /**
   * Actual value of the field instance. The value type varies, depending on `__type`:<br>
   * - For **classic types** (ie. Integer, Float, Boolean, String, Text and FilePath), you just get
   * the actual value with the expected type.<br>
   * - For **Color**, the value is an hexadecimal string using "#rrggbb" format.<br>
   * - For **Enum**, the value is a String representing the selected enum value.<br>
   * - For **Point**, the value is a [GridPoint](#ldtk-GridPoint) object.<br>
   * - For **Tile**, the value is a [TilesetRect](#ldtk-TilesetRect) object.<br>
   * - For **EntityRef**, the value is an [EntityReferenceInfos](#ldtk-EntityReferenceInfos) object.
   * <br>
   * <br>
   * If the field is an array, then this `__value` will also be a JSON array.
   */
  @JsonProperty("__value")
  public Object getValue() {
    return value;
  }

  @JsonProperty("__value")
  public void setValue(final Object value) {
    this.value = value;
  }

  /** Reference of the **Field definition** UID */
  @JsonProperty("defUid")
  public int getDefUid() {
    return defUid;
  }

  @JsonProperty("defUid")
  public void setDefUid(final int value) {
    defUid = value;
  }

  /** Editor internal raw values */
  @JsonProperty("realEditorValues")
  public Object[] getRealEditorValues() {
    return realEditorValues;
  }

  @JsonProperty("realEditorValues")
  public void setRealEditorValues(final Object[] value) {
    realEditorValues = value;
  }
}
