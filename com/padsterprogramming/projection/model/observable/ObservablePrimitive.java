package com.padsterprogramming.projection.model.observable;

import com.padsterprogramming.projection.model.Primitive;

/** Primitive value which notifies listeners on value changes. */
public interface ObservablePrimitive<T> extends Primitive<T>, Observable<T> {
}
