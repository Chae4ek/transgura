package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/**
 * If you're writing your own LDtk importer, you should probably just ignore *most* stuff in the
 * `defs` section, as it contains data that are mostly important to the editor. To keep you away
 * from the `defs` section and avoid some unnecessary JSON parsing, important data from definitions
 * is often duplicated in fields prefixed with a double underscore (eg. `__identifier` or `__type`).
 * The 2 only definition types you might need here are **Tilesets** and **Enums**.
 *
 * <p>A structure containing all the definitions of this project
 */
public class Definitions {
  private EntityDefinition[] entities;
  private EnumDefinition[] enums;
  private EnumDefinition[] externalEnums;
  private LayerDefinition[] layers;
  private FieldDefinition[] levelFields;
  private TilesetDefinition[] tilesets;

  /** All entities definitions, including their custom fields */
  @JsonProperty("entities")
  public EntityDefinition[] getEntities() {
    return entities;
  }

  @JsonProperty("entities")
  public void setEntities(final EntityDefinition[] value) {
    entities = value;
  }

  /** All internal enums */
  @JsonProperty("enums")
  public EnumDefinition[] getEnums() {
    return enums;
  }

  @JsonProperty("enums")
  public void setEnums(final EnumDefinition[] value) {
    enums = value;
  }

  /**
   * Note: external enums are exactly the same as `enums`, except they have a `relPath` to point to
   * an external source file.
   */
  @JsonProperty("externalEnums")
  public EnumDefinition[] getExternalEnums() {
    return externalEnums;
  }

  @JsonProperty("externalEnums")
  public void setExternalEnums(final EnumDefinition[] value) {
    externalEnums = value;
  }

  /** All layer definitions */
  @JsonProperty("layers")
  public LayerDefinition[] getLayers() {
    return layers;
  }

  @JsonProperty("layers")
  public void setLayers(final LayerDefinition[] value) {
    layers = value;
  }

  /** All custom fields available to all levels. */
  @JsonProperty("levelFields")
  public FieldDefinition[] getLevelFields() {
    return levelFields;
  }

  @JsonProperty("levelFields")
  public void setLevelFields(final FieldDefinition[] value) {
    levelFields = value;
  }

  /** All tilesets */
  @JsonProperty("tilesets")
  public TilesetDefinition[] getTilesets() {
    return tilesets;
  }

  @JsonProperty("tilesets")
  public void setTilesets(final TilesetDefinition[] value) {
    tilesets = value;
  }
}
