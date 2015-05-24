package com.padsterprogramming.projection.model.impl;

import com.padsterprogramming.projection.model.modifiable.ModifiablePrimitive;
import com.padsterprogramming.projection.model.observable.Listener;
import com.padsterprogramming.projection.model.observable.ObservablePrimitive;

/** Primitive which can do everything. */
public class PrimitiveImpl<T> implements ObservablePrimitive<T>, ModifiablePrimitive<T> {
  private T value;

  public PrimitiveImpl() {
    this(null);
  }

  public PrimitiveImpl(T initialValue) {
    this.value = initialValue;
  }

  @Override public T get() {
    return value;
  }

  @Override public void set(T newValue) {
    T oldValue = value;
    this.value = newValue;
    // TODO - listeners.
  }

  @Override public void addListener(Listener<T> listener) {
    // TODO - listeners.
  }

  @Override public void removeListener(Listener<T> listener) {
    // TODO - listeners.
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder().append('(');
    builder.append(value);
    return builder.append(')').toString();
  }
}
