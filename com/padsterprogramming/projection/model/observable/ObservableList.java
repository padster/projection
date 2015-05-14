package com.padsterprogramming.projection.model.observable;

import com.padsterprogramming.projection.model.List;
import com.padsterprogramming.projection.model.Type;

/** List which notifies listeners on value changes. */
public interface ObservableList<T extends Type> extends List<T>, Observable<List<T>> {
}
