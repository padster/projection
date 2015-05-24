package com.padsterprogramming.projection.tpl;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/** Parses a text template (.tpl.txt) stream into a tree for execution. */
public class TextTemplateParser {
  private static final Pattern MOUSTACHE_PATTERN = Pattern.compile("\\{\\{|\\}\\}");

  public static TextTemplate.ContextNode parse(InputStream input) {
    return new TextTemplateParser(input).parseNode(null);
  }

  private final Scanner scanner;

  // Do not construct one yourself!
  private TextTemplateParser(InputStream input) {
    this.scanner = new Scanner(input);
    scanner.useDelimiter(MOUSTACHE_PATTERN);
  }

  private TextTemplate.ContextNode parseNode(String termination) {
    TextTemplate.ContextNode parsed = new TextTemplate.ContextNode();

    boolean inCommand = false;
    while(scanner.hasNext()) {
      String next = scanner.next();
      if (next.equals(termination)) {
        return parsed;
      }

      if (!inCommand) {
        parsed.addChild(TextTemplate.createTextNode(next));
      } else {
        parsed.addChild(parseCommand(next));
      }
      inCommand = !inCommand;
    }

    if (termination != null) {
      throw new IllegalArgumentException("Unexpected termination, unclosed: " + termination);
    }
    return parsed;
  }

  private TextTemplate.Node parseCommand(String command) {
    if (command.charAt(0) == '#') {
      return parseForLoop(command.substring(1));
    } else {
      // Assume it's a lookup
      return new TextTemplate.LookupNode(command);
    }
  }

  private TextTemplate.Node parseForLoop(String fieldName) {
    TextTemplate.ContextNode child = parseNode("/" + fieldName);
    return new TextTemplate.LoopNode(fieldName, child);
  }
}
