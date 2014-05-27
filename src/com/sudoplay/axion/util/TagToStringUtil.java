package com.sudoplay.axion.util;

import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

public class TagToStringUtil {

  protected static final String DEFAULT_INDENT_STRING = "  ";

  protected static String indentString;
  protected static StringBuilder stringBuilder;

  private TagToStringUtil() {
    //
  }

  public static String getString(final Tag tag) {
    return getString(tag, DEFAULT_INDENT_STRING);
  }

  public static String getString(final Tag tag, final String newIndentString) {
    stringBuilder = new StringBuilder();
    indentString = newIndentString;
    int indent = 0;
    _format(tag, indent);
    return stringBuilder.toString();
  }

  protected static void _format(final Tag tag, int indent) {

    applyIndent(indent).append(tag.toString()).append("\n");

    if (tag instanceof TagCompound) {
      applyIndent(indent).append("{").append("\n");
      indent++;
      for (Tag at : ((TagCompound) tag).getAsMap().values()) {
        _format(at, indent);
      }
      applyIndent(indent - 1).append("}").append("\n");
    } else if (tag instanceof TagList) {
      applyIndent(indent).append("{").append("\n");
      indent++;
      for (Tag at : ((TagList) tag).getAsList()) {
        _format(at, indent);
      }
      applyIndent(indent - 1).append("}").append("\n");
    }
  }

  protected static StringBuilder applyIndent(final int amount) {
    for (int i = 0; i < amount; i++) {
      stringBuilder.append(indentString);
    }
    return stringBuilder;
  }

}
