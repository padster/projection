package com.padsterprogramming.projection.model.modifiable;

import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;

/** StringMap which can be actively changed. */
public interface ModifiableStringMap<T extends Type> extends StringMap<T> {
  // PICK: whether this and delete (plus in List) should return the replaced value.
  void set(String key, T value);

  /**
   * Remove a value for a given key.
   * Within Project Ion, this is guaranteed to be equivalent to setting to null.
   */
  default void remove(String key) {
    this.set(key, null);
  }
}
