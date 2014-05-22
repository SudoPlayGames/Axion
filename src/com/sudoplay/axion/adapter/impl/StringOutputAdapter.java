package com.sudoplay.axion.adapter.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.sudoplay.axion.adapter.Interface_Adapter;
import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.impl.TagCompound;
import com.sudoplay.axion.tag.impl.TagList;

public class StringOutputAdapter implements Interface_Adapter {

  private class DataOutputWrapper {

    private final DataOutput dataOutput;

    public DataOutputWrapper(final DataOutput newDataOutput) {
      dataOutput = newDataOutput;
    }

    public DataOutputWrapper append(final String toAppend) throws IOException {
      dataOutput.writeUTF(toAppend);
      return this;
    }

    public DataOutput getDataOutput() {
      return dataOutput;
    }

  }

  private String indentString = "  ";

  public void setIndentString(final String newIndentString) {
    indentString = newIndentString;
  }

  @Override
  public Abstract_Tag read(Abstract_Tag parent, DataInput in) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void write(Abstract_Tag tag, DataOutput out) throws IOException {
    int indent = 0;
    _format(tag, new DataOutputWrapper(out), indent);
  }

  private void _format(final Abstract_Tag tag, final DataOutputWrapper out, int indent) throws IOException {

    applyIndent(indent, out).append(tag.toString()).append("\n");

    if (tag instanceof TagCompound) {
      applyIndent(indent, out).append("{").append("\n");
      indent++;
      for (Abstract_Tag at : ((TagCompound) tag).getAsMap().values()) {
        _format(at, out, indent);
      }
      applyIndent(indent - 1, out).append("}").append("\n");
    } else if (tag instanceof TagList) {
      applyIndent(indent, out).append("{").append("\n");
      indent++;
      for (Abstract_Tag at : ((TagList) tag).getAsList()) {
        _format(at, out, indent);
      }
      applyIndent(indent - 1, out).append("}").append("\n");
    }
  }

  private DataOutputWrapper applyIndent(final int amount, final DataOutputWrapper out) throws IOException {
    for (int i = 0; i < amount; i++) {
      out.append(indentString);
    }
    return out;
  }

}
