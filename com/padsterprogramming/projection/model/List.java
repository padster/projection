package com.padsterprogramming.projection.model;

/** Ordered collection of (nullable) items of a type. */
public interface List<T extends Type> extends Type {
  /** @return The number of items in the list. */
  int size();

  /** @return The item at a given index. */
  T get(int index);

  /** @return The item at a given index if not null, otherwise a default value. */
  default T getOrDefault(int index, T defaultValue) {
    T result = get(index);
    return result != null ? result : defaultValue;
  }
}
