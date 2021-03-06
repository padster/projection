package com.padsterprogramming.projection.tpl;

import com.padsterprogramming.projection.model.List;
import com.padsterprogramming.projection.model.Primitive;
import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;
import com.padsterprogramming.projection.model.impl.ListImpl;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.util.Primitives;

import java.util.ArrayList;

/** Parsed information for a text template. */
public class TextTemplate {

  public interface Node {
    void write(StringBuilder builder, StringMap<Type> context);
  }

  public static Node createTextNode(final String text) {
    return (builder, context) -> {
      builder.append(text);
    };
  }

  public static class ContextNode implements Node {
    private final ArrayList<Node> nodes = new ArrayList<>();

    public void addChild(Node node) {
      nodes.add(node);
    }

    @Override public void write(StringBuilder builder, final StringMap<Type> context) {
      nodes.forEach(node -> node.write(builder, context));
    }
  }

  public static class LoopNode implements Node {
    private final String path;
    private final Node childNode;

    public LoopNode(String path, Node childNode) {
      if (path.indexOf(".") != -1) {
        // TODO - support this.
        throw new IllegalArgumentException("Sub-path lookup (" + path + ") not supported yet :(");
      }
      this.path = path;
      this.childNode = childNode;
    }

    @Override public void write(StringBuilder builder, StringMap<Type> context) {
      List<? extends Type> values = (List<? extends Type>) contextLookup(context, path);
      if (values != null && !values.isEmpty()) {
        values.forEach(child -> {
          childNode.write(builder, buildContext(child, context));
        });
      } else {
        // TODO - add template options for empty lists.
      }
    }
  }

  public static class LookupNode implements Node {
    private final String path;

    public LookupNode(String path) {
      if (path.indexOf(".") != -1) {
        // TODO - support this.
        throw new IllegalArgumentException("Sub-path lookup (" + path + ") not supported yet :(");
      }
      this.path = path;
    }

    @Override public void write(StringBuilder builder, StringMap<Type> context) {
      Object field = contextLookup(context, path);
      builder.append(field == null ? "null" : field.toString()); // TODO - needed?
    }
  }

  /** Flips on a lookup. */
  public static class IfNode implements Node {
    private final String path;
    private final Node trueNode;
    // TODO - false node.

    public IfNode(String path, Node trueNode) {
      if (path.indexOf(".") != -1) {
        // TODO - support this.
        throw new IllegalArgumentException("Sub-path lookup (" + path + ") not supported yet :(");
      }
      this.path = path;
      this.trueNode = trueNode;
    }

    @Override public void write(StringBuilder builder, StringMap<Type> context) {
      // TODO - more complex if statements.
      Object field = contextLookup(context, path);
      if (field != null && !(field instanceof Boolean)) {
        throw new IllegalArgumentException("If statement for path " + path + " must be boolean, not " + field.getClass().getName());
      }
      if (field != null && ((Boolean)field).booleanValue()) {
        trueNode.write(builder, context);
      } else {
        // TODO - else statement.
      }
    }
  }

  // Utilities
  static StringMapImpl<Type> buildContext(Type child, StringMap<Type> parent) {
    StringMapImpl<Type> result = new StringMapImpl<>();
    result.set("this", child);
    result.set("super", parent);
    return result;
  }

  static Object contextLookup(StringMap<Type> context, String path) {
    if (context == null) {
      return null;
    }
    StringMapImpl<Type> parent = (StringMapImpl<Type>) context.get("super");
    Type child = context.get("this");

    if (child != null && child instanceof StringMap) {
      Object field = ((StringMap<Type>) child).getOrDefault(path, Primitives.of(null));
      if (field == null) {
        return field;
      }
      if (field instanceof Primitive) {
        return ((Primitive<?>) field).get();
      }
      return field;
    }
    return contextLookup(parent, path);
  }
}
