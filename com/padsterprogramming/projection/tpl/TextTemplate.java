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
      values.forEach(child -> {
        childNode.write(builder, buildContext(child, context));
      });
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
