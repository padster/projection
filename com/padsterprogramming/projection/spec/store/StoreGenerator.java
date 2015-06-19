package com.padsterprogramming.projection.spec.store;

import com.padsterprogramming.projection.model.Primitive;
import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.util.Primitives;
import com.padsterprogramming.projection.spec.SpecReader;
import com.padsterprogramming.projection.tpl.TextTemplate;
import com.padsterprogramming.projection.tpl.TextTemplateParser;
import com.padsterprogramming.projection.tpl.TextTemplateWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/** Generates a storage layer for a given type. */
public class StoreGenerator {

  public static void main(String[] args) throws IOException {
    String specPath = args[0];
    String[] outputPackageAndClass = parseFileName(args[1]);
    String forType = args[2];

    StringMap<Type> specData = new SpecReader().parseStringMap(new FileInputStream(args[0]));

    String templatePath = "com/padsterprogramming/projection/spec/store/store.tpl.txt";
    TextTemplate.ContextNode template = TextTemplateParser.parse(resourceToStream(templatePath));

    // HACK - need to do this via bound template method instead.
    StringMap<Type> processed = processData(
        specData, specPath, outputPackageAndClass[0], outputPackageAndClass[1], forType);
    String result = TextTemplateWriter.write(template, processed);
    System.out.println(result);
  }

  // TODO - replace with once-off mapping template.
  private static StringMap<Type> processData(
      StringMap<Type> data, String filePath, String package_, String class_, String forType) {
    StringMapImpl<Type> result = new StringMapImpl<>();

    result.set("file", Primitives.of(filePath));

    final boolean[] classFound = {false};
    data.forEach((name, typeInput) -> {
      if (name.equals("package")) {
        Primitive<String> packageValue = (Primitive<String>) typeInput;
        // TODO - verify the package in the file is the same as package_ from the output.
        result.set(name, packageValue);
      } else if (name.equals(forType)) {
        classFound[0] = true;
        result.set("JavaClass", Primitives.of(name));
        // HACK - this should be read from the spec instead.
        result.set("FullJavaClass", Primitives.of("Model." + name));
      }
    });

    if (!classFound[0]) {
      throw new IllegalArgumentException("Cannot find type " + forType);
    }

    return result;
  }

  // TODO - common util;
  private static InputStream resourceToStream(String path) {
    InputStream stream = StoreGenerator.class.getClassLoader().getResourceAsStream(path);
    if (stream == null) {
      throw new IllegalArgumentException("File not found: " + path);
    }
    return stream;
  }

  /** @return [package, class name] parsed from the file location. */
  private static String[] parseFileName(String fileName) {
    // TODO - verify a valid file name.
    String ending = ".java";
    int splitPoint = fileName.lastIndexOf("/");
    if (splitPoint == -1 || !fileName.endsWith(ending)) {
      throw new IllegalArgumentException("Output path must be of the form path/to/File.java");
    }

    return new String[] {
        fileName.substring(0, splitPoint).replace('/', '.'),
        fileName.substring(splitPoint + 1, fileName.length() - ending.length())
    };
  }
}
