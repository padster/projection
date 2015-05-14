package com.padsterprogramming.projection.model.modifiable;

import com.padsterprogramming.projection.model.Primitive;

/** Primitive which can be actively changed. */
public interface ModifiablePrimitive<T> extends Primitive<T> {
  void set(T newValue);
}
