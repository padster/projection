package com.padsterprogramming.projection.model.observable;

import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;

/** StringMap which notifies listeners on value changes. */
public interface ObservableStringMap<T extends Type> extends StringMap<T>, Observable<StringMap<T>> {
}
