package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/**
 * This object is not actually used by LDtk. It ONLY exists to force explicit references to all
 * types, to make sure QuickType finds them and integrate all of them. Otherwise, Quicktype will
 * drop types that are not explicitely used.
 */
public class ForcedRefs {
  private AutoLayerRuleGroup autoLayerRuleGroup;
  private AutoLayerRuleDefinition autoRuleDef;
  private LdtkCustomCommand customCommand;
  private Definitions definitions;
  private EntityDefinition entityDef;
  private EntityInstance entityInstance;
  private ReferenceToAnEntityInstance entityReferenceInfos;
  private EnumDefinition enumDef;
  private EnumValueDefinition enumDefValues;
  private EnumTagValue enumTagValue;
  private FieldDefinition fieldDef;
  private FieldInstance fieldInstance;
  private GridPoint gridPoint;
  private IntGridValueDefinition intGridValueDef;
  private IntGridValueInstance intGridValueInstance;
  private LayerDefinition layerDef;
  private LayerInstance layerInstance;
  private Level level;
  private LevelBackgroundPosition levelBgPosInfos;
  private NeighbourLevel neighbourLevel;
  private LdtkTableOfContentEntry tableOfContentEntry;
  private TileInstance tile;
  private TileCustomMetadata tileCustomMetadata;
  private TilesetDefinition tilesetDef;
  private TilesetRectangle tilesetRect;
  private World world;

  @JsonProperty("AutoLayerRuleGroup")
  public AutoLayerRuleGroup getAutoLayerRuleGroup() {
    return autoLayerRuleGroup;
  }

  @JsonProperty("AutoLayerRuleGroup")
  public void setAutoLayerRuleGroup(final AutoLayerRuleGroup value) {
    autoLayerRuleGroup = value;
  }

  @JsonProperty("AutoRuleDef")
  public AutoLayerRuleDefinition getAutoRuleDef() {
    return autoRuleDef;
  }

  @JsonProperty("AutoRuleDef")
  public void setAutoRuleDef(final AutoLayerRuleDefinition value) {
    autoRuleDef = value;
  }

  @JsonProperty("CustomCommand")
  public LdtkCustomCommand getCustomCommand() {
    return customCommand;
  }

  @JsonProperty("CustomCommand")
  public void setCustomCommand(final LdtkCustomCommand value) {
    customCommand = value;
  }

  @JsonProperty("Definitions")
  public Definitions getDefinitions() {
    return definitions;
  }

  @JsonProperty("Definitions")
  public void setDefinitions(final Definitions value) {
    definitions = value;
  }

  @JsonProperty("EntityDef")
  public EntityDefinition getEntityDef() {
    return entityDef;
  }

  @JsonProperty("EntityDef")
  public void setEntityDef(final EntityDefinition value) {
    entityDef = value;
  }

  @JsonProperty("EntityInstance")
  public EntityInstance getEntityInstance() {
    return entityInstance;
  }

  @JsonProperty("EntityInstance")
  public void setEntityInstance(final EntityInstance value) {
    entityInstance = value;
  }

  @JsonProperty("EntityReferenceInfos")
  public ReferenceToAnEntityInstance getEntityReferenceInfos() {
    return entityReferenceInfos;
  }

  @JsonProperty("EntityReferenceInfos")
  public void setEntityReferenceInfos(final ReferenceToAnEntityInstance value) {
    entityReferenceInfos = value;
  }

  @JsonProperty("EnumDef")
  public EnumDefinition getEnumDef() {
    return enumDef;
  }

  @JsonProperty("EnumDef")
  public void setEnumDef(final EnumDefinition value) {
    enumDef = value;
  }

  @JsonProperty("EnumDefValues")
  public EnumValueDefinition getEnumDefValues() {
    return enumDefValues;
  }

  @JsonProperty("EnumDefValues")
  public void setEnumDefValues(final EnumValueDefinition value) {
    enumDefValues = value;
  }

  @JsonProperty("EnumTagValue")
  public EnumTagValue getEnumTagValue() {
    return enumTagValue;
  }

  @JsonProperty("EnumTagValue")
  public void setEnumTagValue(final EnumTagValue value) {
    enumTagValue = value;
  }

  @JsonProperty("FieldDef")
  public FieldDefinition getFieldDef() {
    return fieldDef;
  }

  @JsonProperty("FieldDef")
  public void setFieldDef(final FieldDefinition value) {
    fieldDef = value;
  }

  @JsonProperty("FieldInstance")
  public FieldInstance getFieldInstance() {
    return fieldInstance;
  }

  @JsonProperty("FieldInstance")
  public void setFieldInstance(final FieldInstance value) {
    fieldInstance = value;
  }

  @JsonProperty("GridPoint")
  public GridPoint getGridPoint() {
    return gridPoint;
  }

  @JsonProperty("GridPoint")
  public void setGridPoint(final GridPoint value) {
    gridPoint = value;
  }

  @JsonProperty("IntGridValueDef")
  public IntGridValueDefinition getIntGridValueDef() {
    return intGridValueDef;
  }

  @JsonProperty("IntGridValueDef")
  public void setIntGridValueDef(final IntGridValueDefinition value) {
    intGridValueDef = value;
  }

  @JsonProperty("IntGridValueInstance")
  public IntGridValueInstance getIntGridValueInstance() {
    return intGridValueInstance;
  }

  @JsonProperty("IntGridValueInstance")
  public void setIntGridValueInstance(final IntGridValueInstance value) {
    intGridValueInstance = value;
  }

  @JsonProperty("LayerDef")
  public LayerDefinition getLayerDef() {
    return layerDef;
  }

  @JsonProperty("LayerDef")
  public void setLayerDef(final LayerDefinition value) {
    layerDef = value;
  }

  @JsonProperty("LayerInstance")
  public LayerInstance getLayerInstance() {
    return layerInstance;
  }

  @JsonProperty("LayerInstance")
  public void setLayerInstance(final LayerInstance value) {
    layerInstance = value;
  }

  @JsonProperty("Level")
  public Level getLevel() {
    return level;
  }

  @JsonProperty("Level")
  public void setLevel(final Level value) {
    level = value;
  }

  @JsonProperty("LevelBgPosInfos")
  public LevelBackgroundPosition getLevelBgPosInfos() {
    return levelBgPosInfos;
  }

  @JsonProperty("LevelBgPosInfos")
  public void setLevelBgPosInfos(final LevelBackgroundPosition value) {
    levelBgPosInfos = value;
  }

  @JsonProperty("NeighbourLevel")
  public NeighbourLevel getNeighbourLevel() {
    return neighbourLevel;
  }

  @JsonProperty("NeighbourLevel")
  public void setNeighbourLevel(final NeighbourLevel value) {
    neighbourLevel = value;
  }

  @JsonProperty("TableOfContentEntry")
  public LdtkTableOfContentEntry getTableOfContentEntry() {
    return tableOfContentEntry;
  }

  @JsonProperty("TableOfContentEntry")
  public void setTableOfContentEntry(final LdtkTableOfContentEntry value) {
    tableOfContentEntry = value;
  }

  @JsonProperty("Tile")
  public TileInstance getTile() {
    return tile;
  }

  @JsonProperty("Tile")
  public void setTile(final TileInstance value) {
    tile = value;
  }

  @JsonProperty("TileCustomMetadata")
  public TileCustomMetadata getTileCustomMetadata() {
    return tileCustomMetadata;
  }

  @JsonProperty("TileCustomMetadata")
  public void setTileCustomMetadata(final TileCustomMetadata value) {
    tileCustomMetadata = value;
  }

  @JsonProperty("TilesetDef")
  public TilesetDefinition getTilesetDef() {
    return tilesetDef;
  }

  @JsonProperty("TilesetDef")
  public void setTilesetDef(final TilesetDefinition value) {
    tilesetDef = value;
  }

  @JsonProperty("TilesetRect")
  public TilesetRectangle getTilesetRect() {
    return tilesetRect;
  }

  @JsonProperty("TilesetRect")
  public void setTilesetRect(final TilesetRectangle value) {
    tilesetRect = value;
  }

  @JsonProperty("World")
  public World getWorld() {
    return world;
  }

  @JsonProperty("World")
  public void setWorld(final World value) {
    world = value;
  }
}
