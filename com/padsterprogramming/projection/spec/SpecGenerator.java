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

    StringMap<com.padsterprogramming.projection.model.Type>
        specData = new SpecReader().parseStringMap(new FileInputStream(args[0]));

    String templatePath = "com/padsterprogramming/projection/spec/basetype.tpl.txt";
    TextTemplate.ContextNode template = TextTemplateParser.parse(resourceToStream(templatePath));

    // HACK - need to do this via bound template method instead.
    StringMap<com.padsterprogramming.projection.model.Type> processed =
        processData(specData, specPath, outputPackageAndClass[0], outputPackageAndClass[1]);
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

  // HACK - automate via template, see above.
  private static StringMap<com.padsterprogramming.projection.model.Type> processData(
      StringMap<com.padsterprogramming.projection.model.Type> data, String filePath, String package_, String class_) {
    StringMapImpl<com.padsterprogramming.projection.model.Type> result = new StringMapImpl<>();
    result.set("file", Primitives.of(filePath));
    result.set("WrapperClass", Primitives.of(class_));

    ((StringMapImpl<com.padsterprogramming.projection.model.Type>) data).keys().forEach(name -> {
      if (name.equals("package")) {
        Primitive<String> packageValue = (Primitive<String>) data.get("package");
        // TODO - verify the package in the file is the same as package_ from the output.
        result.set(name, packageValue);
      } else {
        result.set("name", Primitives.of(name));
        result.set("UpperName", Primitives.of(upperFirst(name)));

        StringMap<Type> typeData = (StringMap<Type>) data.get(name);
        List<Type> typeFieldList = (List<Type>) typeData.get("field");
        StringMap<Type> fieldsForType = (StringMap<Type>) typeFieldList.get(0);

        ListImpl<Type> processedFields = new ListImpl<>();
        fieldsForType.forEach((fieldName, value) -> {
          String v = ((Primitive<String>) value).get();
          String ufv = upperFirst(v);
          StringMapImpl<Type> fieldType = new StringMapImpl<>();
          fieldType.set("name", Primitives.of(fieldName));
          fieldType.set("UpperName", Primitives.of(upperFirst(fieldName)));
          fieldType.set("type", Primitives.of(v));
          fieldType.set("UpperType", Primitives.of(upperFirst(v)));
          fieldType.set("TypeWrapper", Primitives.of(String.format("Primitive<%s>", ufv)));
          fieldType.set("TypeWrapperConstructor", Primitives.of(String.format("mew Primitive<%s>(%s)", ufv, name)));
          processedFields.append(fieldType);
        });
        result.set("field", processedFields);
      }
    });
    return result;
  }

  private static String upperFirst(String input) {
    return Character.toUpperCase(input.charAt(0)) + input.substring(1);
  }

}
