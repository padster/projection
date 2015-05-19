package com.padsterprogramming.projection.server.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/** Base entrypoint for the client. */
public class BaseClient implements EntryPoint {
  @Override public void onModuleLoad() {
    Window.alert("Hello!");
  }
}
