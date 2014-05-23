package com.sudoplay.axion.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.tag.TagCompound;
import com.sudoplay.axion.tag.TagList;

public class StringOutputAdapter implements Adapter {

  private class DataOutputWrapper {

    private final PrintWriter out;

    public DataOutputWrapper(final OutputStream newOutputStream, final Charset charset) {
      out = new PrintWriter(new OutputStreamWriter(newOutputStream, charset), true);
    }

    public DataOutputWrapper append(final String toAppend) throws IOException {
      out.print(toAppend);
      return this;
    }

    public DataOutputWrapper endLine() {
      out.println();
      return this;
    }

  }

  private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
  private static final String DEFAULT_INDENT_STRING = "  ";

  private final Charset charset;
  private final String indentString;

  public StringOutputAdapter() {
    this(DEFAULT_CHARSET, DEFAULT_INDENT_STRING);
  }

  public StringOutputAdapter(final String newIndentString) {
    this(DEFAULT_CHARSET, newIndentString);
  }

  public StringOutputAdapter(final Charset newCharset) {
    this(newCharset, DEFAULT_INDENT_STRING);
  }

  public StringOutputAdapter(final Charset newCharset, final String newIndentString) {
    charset = newCharset;
    indentString = newIndentString;
  }

  @Override
  public Tag read(Tag parent, InputStream in) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void write(Tag tag, OutputStream out) throws IOException {
    int indent = 0;
    _format(tag, new DataOutputWrapper(out, charset), indent);
  }

  private void _format(final Tag tag, final DataOutputWrapper out, int indent) throws IOException {

    applyIndent(indent, out).append(tag.toString()).endLine();

    if (tag instanceof TagCompound) {
      applyIndent(indent, out).append("{").endLine();
      indent++;
      for (Tag at : ((TagCompound) tag).getAsMap().values()) {
        _format(at, out, indent);
      }
      applyIndent(indent - 1, out).append("}").endLine();
    } else if (tag instanceof TagList) {
      applyIndent(indent, out).append("{").endLine();
      indent++;
      for (Tag at : ((TagList) tag).getAsList()) {
        _format(at, out, indent);
      }
      applyIndent(indent - 1, out).append("}").endLine();
    }
  }

  private DataOutputWrapper applyIndent(final int amount, final DataOutputWrapper out) throws IOException {
    for (int i = 0; i < amount; i++) {
      out.append(indentString);
    }
    return out;
  }

}
