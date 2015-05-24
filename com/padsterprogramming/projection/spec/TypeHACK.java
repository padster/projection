package com.padsterprogramming.projection.spec;

import com.padsterprogramming.projection.model.Primitive;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.util.Primitives;

/** Model interface enerator model (boostrap, can't generate itself ...yet). */
// BIG todo - replace with generated version once set up.
public class TypeHACK extends StringMapImpl<com.padsterprogramming.projection.model.Type> {
  // Constructor with defaults
  public TypeHACK() {
    this(null, null, false);
  }

  // Constructor with everything provided.
  public TypeHACK(String name, String type, boolean repeated) {
    this.set("name", Primitives.<String>of(name));
    this.set("type", Primitives.<String>of(type));
    this.set("repeated", Primitives.of(repeated));
  }

  // TODO - builder? or just modifiable version.

  // "name": "string"
  public String name() {
    return this.observableName().get();
  }
  public Primitive<String> observableName() {
    return (Primitive<String>) this.get("name");
  }

  // "type": "string",
  public String type() {
    return this.observableType().get();
  }
  public Primitive<String> observableType() {
    return (Primitive<String>) this.get("type");
  }

  // "repeated": "boolean"
  public boolean repeated() {
    return this.observableRepeated().get();
  }
  public Primitive<Boolean> observableRepeated() {
    return (Primitive<Boolean>) this.get("repeated");
  }
}
