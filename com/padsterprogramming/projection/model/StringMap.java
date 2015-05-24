package com.padsterprogramming.projection.model;

import java.util.function.BiConsumer;

/** Collection of named values. */
public interface StringMap<T extends Type> extends Type {
  /** @return The number values within the map. */
  int size();

  /** @return The value keyed by a given string. */
  T get(String key);

  /** Java 8 functional iteration. */
  void forEach(BiConsumer<String, ? super T> action);

  /** @return A value if it is present, otherwise a default value. */
  default T getOrDefault(String key, T defaultValue) {
    T result = get(key);
    return result != null ? result : defaultValue;
  }
}
