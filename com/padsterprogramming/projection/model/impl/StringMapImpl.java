package com.padsterprogramming.projection.model.impl;

import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;
import com.padsterprogramming.projection.model.modifiable.ModifiableStringMap;
import com.padsterprogramming.projection.model.observable.Listener;
import com.padsterprogramming.projection.model.observable.ObservableStringMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/** StringMap which can do everything. */
public class StringMapImpl<T extends Type> implements ObservableStringMap<T>, ModifiableStringMap<T> {

  private final HashMap<String, T> values;

  // PICK: Have a version which is initialized with default values?
  public StringMapImpl() {
    this.values = new HashMap<>();
  }

  @Override public int size() {
    return values.size();
  }

  @Override public T get(String key) {
    // PICK: index-out-of-bounds here, and only allow null result on getOrDefault?
    return values.get(key);
  }

  @Override public void set(String key, T value) {
    if (value == null) {
      values.remove(key);
    } else {
      values.put(key, value);
    }
    // TODO - listeners.
  }

  @Override public void addListener(Listener<StringMap<T>> listener) {
    // TODO - listeners.
  }

  @Override public void removeListener(Listener<StringMap<T>> listener) {
    // TODO - listeners.
  }

  public Set<String> keys() {
    return values.keySet();
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder().append('{');
    values.forEach((k, v) -> builder.append(k + ":" + v + ","));
    return builder.append('}').toString();
  }
}
