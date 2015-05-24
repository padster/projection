package com.padsterprogramming.projection.spec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.padsterprogramming.projection.model.List;
import com.padsterprogramming.projection.model.Primitive;
import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;
import com.padsterprogramming.projection.model.impl.ListImpl;
import com.padsterprogramming.projection.model.impl.PrimitiveImpl;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.modifiable.ModifiableList;
import com.padsterprogramming.projection.model.modifiable.ModifiableStringMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/** Reads a model spec from file. */
public class SpecReader {
  // TODO - make this more specific, and pull out JSON -> StringMap parser into separate utility.

  /** Parse a JSON object from a stream into a stringmap. Generic only, use generated versions for type-safety. */
  public StringMap<Type> parseStringMap(InputStream input) throws IOException {
    // PICK: Support primitives/lists too?
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode asNode = mapper.readValue(input, ObjectNode.class);
    return convertObject(asNode);
  }

  private Type bestGuess(JsonNode node) {
    if (node == null) {
      return null;
    } else if (node.isObject()) {
      return convertObject((ObjectNode) node);
    } else if (node.isArray()) {
      return convertList((ArrayNode) node);
    } else {
      return bestGuessPrimitive(node);
    }
  }

  private StringMap<Type> convertObject(ObjectNode node) {
    final ModifiableStringMap<Type> result = new StringMapImpl<>();
    node.fields().forEachRemaining(entry -> {
      result.set(entry.getKey(), bestGuess(entry.getValue()));
    });
    return result;
  }

  private List<Type> convertList(ArrayNode node) {
    final ModifiableList<Type> result = new ListImpl<>();
    node.elements().forEachRemaining(entry -> {
      result.append(bestGuess(entry));
    });
    return result;
  }

  private Primitive<?> bestGuessPrimitive(JsonNode node) {
    if (node.isInt()) {
      return new PrimitiveImpl<>(node.intValue());
    } else if (node.isDouble()) {
      return new PrimitiveImpl<>(node.doubleValue());
    } else if (node.isBoolean()) {
      return new PrimitiveImpl<>(node.booleanValue());
    } else {
      return new PrimitiveImpl<>(node.textValue());
    }
  }
}
