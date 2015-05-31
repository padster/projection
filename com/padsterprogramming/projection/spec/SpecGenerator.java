package com.padsterprogramming.projection.spec;

import com.padsterprogramming.projection.model.*;
import com.padsterprogramming.projection.model.impl.ListImpl;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.util.Primitives;
import com.padsterprogramming.projection.tpl.TextTemplate;
import com.padsterprogramming.projection.tpl.TextTemplateParser;
import com.padsterprogramming.projection.tpl.TextTemplateWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/** Java entry class that can be run, utility to help test code. */
public class SpecGenerator {
  // HACK - make genrule instead.
  public static void main(String[] args) throws IOException {
    String specPath = args[0];
    String[] outputPackageAndClass = parseFileName(args[1]);

    StringMap<Type> specData = new SpecReader().parseStringMap(new FileInputStream(args[0]));

    String templatePath = "com/padsterprogramming/projection/spec/basetype.tpl.txt";
    TextTemplate.ContextNode template = TextTemplateParser.parse(resourceToStream(templatePath));

    // HACK - need to do this via bound template method instead.
    StringMap<Type> processed = processData(specData, specPath, outputPackageAndClass[0], outputPackageAndClass[1]);
    String result = TextTemplateWriter.write(template, processed);
    System.out.println(result);
  }

  private static InputStream resourceToStream(String path) {
    return SpecGenerator.class.getClassLoader().getResourceAsStream(path);
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

  // TODO - replace with once-off mapping template.
  private static StringMap<Type> processData(
      StringMap<Type> data, String filePath, String package_, String class_) {
    StringMapImpl<Type> result = new StringMapImpl<>();

    result.set("file", Primitives.of(filePath));
    result.set("WrapperClass", Primitives.of(class_));

    ListImpl<StringMap<Type>> typeList = new ListImpl<>();

    data.forEach((name, typeInput) -> {
      if (name.equals("package")) {
        Primitive<String> packageValue = (Primitive<String>) typeInput;
        // TODO - verify the package in the file is the same as package_ from the output.
        result.set(name, packageValue);
      } else {
        StringMapImpl<Type> typeOutput = new StringMapImpl<>();
        typeOutput.set("name", Primitives.of(name));
        typeOutput.set("UpperName", Primitives.of(upperFirst(name)));

        ListImpl<Type> processedFields = new ListImpl<>();

        StringMap<Type> specType = (StringMap<Type>) typeInput;
        specType.forEach((fieldName, fieldType) -> {
          String typeString = ((Primitive<String>) fieldType).get();

          StringMapImpl<Type> info = new StringMapImpl<>();
          info.set("name", Primitives.of(fieldName));
          info.set("UpperName", Primitives.of(upperFirst(fieldName)));
          info.set("type", Primitives.of(typeString));
          info.set("ObservableType", Primitives.of(typeStringToObservableType(typeString)));
          info.set("UnobservableType", Primitives.of(typeStringToUnobservableType(typeString)));
          info.set("TypeWrapperConstructor", Primitives.of(unobservableToObservableTypeWrapper(typeString, fieldName)));
          info.set("defaultValue", Primitives.of(defaultValue(typeString)));
          processedFields.append(info);
        });

        typeOutput.set("fields", processedFields);
        typeList.append(typeOutput);
      }
    });

    result.set("types", typeList);
    return result;
  }

  private static final String typeStringToObservableType(String typeString) {
    if (typeString.startsWith("StringMap<")) {
      if (!typeString.endsWith(">")) {
        throw new IllegalArgumentException("Unsupported type: " + typeString);
      }
      return typeString;
    }

    // TODO - handle List<> like StringMap above, when needed.

    switch (typeString) {
      case "double":
      case "boolean":
      case "long":
      case "string":
        return "Primitive<" + upperFirst(typeString) + ">";
    }
    // PICK - support checking if the class is a proto, and having a Primitive<Message> for that too?

    return typeString;
  }

  private static final String typeStringToUnobservableType(String typeString) {
    if (typeString.startsWith("StringMap<")) {
      if (!typeString.endsWith(">")) {
        throw new IllegalArgumentException("Unsupported type: " + typeString);
      }
      return typeString;
    }

    // TODO - handle List<> like StringMap above, when needed.

    switch (typeString) {
      case "double":
      case "boolean":
      case "long":
      case "string":
        return upperFirst(typeString);
    }

    return typeString;
  }

  private static final String unobservableToObservableTypeWrapper(String typeString, String variableName) {
    if (typeString.startsWith("StringMap<")) {
      if (!typeString.endsWith(">")) {
        throw new IllegalArgumentException("Unsupported type: " + typeString);
      }
      return variableName;
    }

    switch (typeString) {
      case "double":
      case "boolean":
      case "long":
      case "string":
        return "Primitives.of(" + variableName + ")";
    }

    return variableName;
  }

  private static final String defaultValue(String typeString) {
    switch (typeString) {
      case "double":
        return "0.0";
      case "boolean":
        return "false";
      case "long":
        return "0";
    }
    // PICK - use null and boxed types for everything?
    // PICK - default String to empty string? Lists/Map to empty?
    return "null";
  }

  private static String upperFirst(String input) {
    return Character.toUpperCase(input.charAt(0)) + input.substring(1);
  }

}
