package com.padsterprogramming.projection.model.util;

import com.padsterprogramming.projection.model.Primitive;
import com.padsterprogramming.projection.model.impl.PrimitiveImpl;

/** Utilities for dealing with primitives. */
public class Primitives {
  public static <T> Primitive<T> of(T value) {
    return new PrimitiveImpl<T>(value);
  }
}
