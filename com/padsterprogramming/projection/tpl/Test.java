package com.padsterprogramming.projection.tpl;

import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.util.Primitives;

import java.io.InputStream;

/** Simple test execution of the template parser and runner. */
public class Test {
  public static void main(String[] args) {
    System.out.println("Running...\n");

    String templatePath = "com/padsterprogramming/projection/tpl/test.tpl.txt";
    TextTemplate.ContextNode parsed  = TextTemplateParser.parse(resourceToStream(templatePath));

    StringMapImpl<Type> input = new StringMapImpl<>();
    input.set("name", Primitives.of("NAME-O"));
    input.set("check", Primitives.of(true));
    String result = TextTemplateWriter.write(parsed, input);
    System.out.println(result);
  }

  // TODO - common util;
  private static InputStream resourceToStream(String path) {
    InputStream stream = Test.class.getClassLoader().getResourceAsStream(path);
    if (stream == null) {
      throw new IllegalArgumentException("File not found: " + path);
    }
    return stream;
  }
}
