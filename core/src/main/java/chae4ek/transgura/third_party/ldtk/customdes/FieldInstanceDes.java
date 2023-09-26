package chae4ek.transgura.third_party.ldtk.customdes;

import chae4ek.transgura.third_party.ldtk.FieldInstance;
import chae4ek.transgura.third_party.ldtk.GridPoint;
import chae4ek.transgura.third_party.ldtk.TilesetRectangle;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class FieldInstanceDes extends StdDeserializer<FieldInstance> {

  public FieldInstanceDes() {
    this(null);
  }

  public FieldInstanceDes(final Class<?> vc) {
    super(vc);
  }

  @Override
  public FieldInstance deserialize(final JsonParser parser, final DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    final JsonNode node = parser.getCodec().readTree(parser);

    final FieldInstance fieldInstance = new FieldInstance();

    fieldInstance.setIdentifier(node.get("__identifier").asText());
    try (final JsonParser jp = node.get("__tile").traverse(parser.getCodec())) {
      fieldInstance.setTile(jp.readValueAs(TilesetRectangle.class));
    }
    final String type = node.get("__type").asText();
    fieldInstance.setType(type);
    fieldInstance.setDefUid((Integer) node.get("defUid").numberValue());
    try (final JsonParser jp = node.get("realEditorValues").traverse(parser.getCodec())) {
      fieldInstance.setRealEditorValues(jp.readValueAs(Object[].class));
    }

    final Object value;
    try (final JsonParser jp = node.get("__value").traverse(parser.getCodec())) {
      if (type.equals("Point")) {
        value = jp.readValueAs(GridPoint.class);
      } else {
        // TODO: other types
        value = null;
      }
    }

    fieldInstance.setValue(value);

    return fieldInstance;
  }
}
