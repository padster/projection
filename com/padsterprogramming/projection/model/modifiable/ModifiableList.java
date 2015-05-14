package com.padsterprogramming.projection.model.modifiable;

import com.padsterprogramming.projection.model.List;
import com.padsterprogramming.projection.model.Type;

/** List which can be actively modified. */
public interface ModifiableList<T extends Type> extends List<T> {
  void set(int index, T value);

  void insert(int index, T value);
  void remove(int index);

  /** Append - by default, just insert at the current size. */
  default void append(T value) {
    this.insert(this.size(), value);
  }

  // PICK: Add default unshift / append(T...), append(List<T>) etc. methods?
}
