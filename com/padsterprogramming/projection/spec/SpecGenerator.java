package com.padsterprogramming.projection.spec;

import com.padsterprogramming.projection.model.*;
import com.padsterprogramming.projection.model.impl.ListImpl;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.util.Primitives;
import com.padsterprogramming.projection.tpl.TextTemplate;
import com.padsterprogramming.projection.tpl.TextTemplateParser;
import com.padsterprogramming.projection.tpl.TextTemplateWriter;

import java.io.IOException;
import java.io.InputStream;

/** Java entry class that can be run, utility to help test code. */
public class SpecGenerator {
  // HACK - make genrule instead.
  public static void main(String[] args) throws IOException {
    String templatePath = "com/padsterprogramming/projection/spec/basetype.tpl.txt";
    TextTemplate.ContextNode template = TextTemplateParser.parse(resourceToStream(templatePath));

    String specPath = "com/padsterprogramming/projection/spec/model.spec";
    StringMap<com.padsterprogramming.projection.model.Type>
        specData = new SpecReader().parseStringMap(resourceToStream(specPath));

    // HACK - need to do this via bound template method instead.
    StringMap<com.padsterprogramming.projection.model.Type> processed = processData(specData, specPath);
    String result = TextTemplateWriter.write(template, processed);
    System.out.println(result);
  }

  private static InputStream resourceToStream(String path) {
    return SpecGenerator.class.getClassLoader().getResourceAsStream(path);
  }

  // HACK - automate via template, see above.
  private static StringMap<com.padsterprogramming.projection.model.Type> processData(
      StringMap<com.padsterprogramming.projection.model.Type> data, String filePath) {
    StringMapImpl<com.padsterprogramming.projection.model.Type> result = new StringMapImpl<>();
    result.set("file", Primitives.of(filePath));

    ((StringMapImpl<com.padsterprogramming.projection.model.Type>) data).keys().forEach(name -> {
      if (name.equals("package")) {
        result.set(name, (Primitive<String>) data.get("package"));
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
