package com.padsterprogramming.projection.tpl;

import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;

/** Template that generates once-off static text output given a TypeHACK. */
public class TextTemplateWriter {
  public static String write(TextTemplate.ContextNode template, StringMap<Type> data) {
    StringBuilder builder = new StringBuilder();
    template.write(builder, TextTemplate.buildContext(data, null));
    return builder.toString();
  }
}
