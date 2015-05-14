package com.padsterprogramming.projection.model;

/** Collection of named values. */
public interface StringMap<T extends Type> extends Type {
  /** @return The number values within the map. */
  int size();

  /** @return The value keyed by a given string. */
  T get(String key);

  /** @return A value if it is present, otherwise a default value. */
  default T getOrDefault(String key, T defaultValue) {
    T result = get(key);
    return result != null ? result : defaultValue;
  }
}
