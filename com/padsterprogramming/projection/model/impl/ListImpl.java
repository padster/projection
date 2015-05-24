package com.padsterprogramming.projection.model.impl;

import com.padsterprogramming.projection.model.List;
import com.padsterprogramming.projection.model.Type;
import com.padsterprogramming.projection.model.modifiable.ModifiableList;
import com.padsterprogramming.projection.model.observable.Listener;
import com.padsterprogramming.projection.model.observable.ObservableList;

import java.util.ArrayList;

/** List which can do everything. */
public class ListImpl<T extends Type> implements ObservableList<T>, ModifiableList<T> {

  private final ArrayList<T> values;

  // PICK: Have a version which is initialized with a T... or List<T>?
  public ListImpl() {
    values = new ArrayList<>();
  }

  @Override public int size() {
    return values.size();
  }

  @Override public T get(int index) {
    return values.get(index);
  }

  @Override public void set(int index, T value) {
    values.set(index, value);
    // TODO - listners.
  }

  @Override public void insert(int index, T value) {
    values.add(index, value);
    // TODO - listeners.
  }

  @Override public void remove(int index) {
    values.remove(index);
    // TODO - listeners.
  }

  @Override public void addListener(Listener<List<T>> listener) {
    // TODO - listeners.
  }

  @Override public void removeListener(Listener<List<T>> listener) {
    // TODO - listeners.
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder().append('[');
    values.forEach(v -> builder.append(v + ","));
    return builder.append(']').toString();
  }
}
