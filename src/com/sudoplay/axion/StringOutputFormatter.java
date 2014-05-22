package com.sudoplay.axion;

import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.impl.TagCompound;
import com.sudoplay.axion.tag.impl.TagList;

public class StringOutputFormatter {

  private String indentString = "  ";

  public void setIndentString(final String newIndentString) {
    indentString = newIndentString;
  }

  public String format(Abstract_Tag tag) {
    int indent = 0;
    StringBuilder sb = new StringBuilder();
    _format(tag, sb, indent);
    return sb.toString();
  }

  private StringBuilder _format(final Abstract_Tag tag, final StringBuilder sb, int indent) {

    applyIndent(indent, sb);
    sb.append(tag.toString()).append("\n");

    if (tag instanceof TagCompound) {
      applyIndent(indent, sb).append("{").append("\n");
      indent++;
      for (Abstract_Tag at : ((TagCompound) tag).getAsMap().values()) {
        _format(at, sb, indent);
      }
      applyIndent(indent - 1, sb).append("}").append("\n");
    } else if (tag instanceof TagList) {
      applyIndent(indent, sb).append("{").append("\n");
      indent++;
      for (Abstract_Tag at : ((TagList) tag).getAsList()) {
        _format(at, sb, indent);
      }
      applyIndent(indent - 1, sb).append("}").append("\n");
    }
    return sb;
  }

  private StringBuilder applyIndent(final int amount, final StringBuilder sb) {
    for (int i = 0; i < amount; i++) {
      sb.append(indentString);
    }
    return sb;
  }

}
