package com.padsterprogramming.projection.model;

/** Type representing a single value. */
public interface Primitive<T> extends Type {
  /** @return The current value of the primitive. */
  T get();

  /** @return The current value if not null, otherwise a default value. */
  default T getOrDefault(T defaultValue) {
    T result = get();
    return result != null ? result : defaultValue;
  }
}
