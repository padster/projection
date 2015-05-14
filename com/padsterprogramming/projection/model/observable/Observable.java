package com.padsterprogramming.projection.model.observable;

import com.padsterprogramming.projection.model.Type;

/** Common interface for all observable types. */
public interface Observable<T> extends Type {
  /** Add a listener to invoke on new changes. */
  void addListener(Listener<T> listener);

  /** Removes a listener if it is listening */
  void removeListener(Listener<T> listener);
}
