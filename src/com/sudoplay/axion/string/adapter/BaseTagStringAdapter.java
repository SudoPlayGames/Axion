package com.sudoplay.axion.string.adapter;

import java.io.IOException;
import java.nio.charset.Charset;

import com.sudoplay.axion.adapter.AxionTagRegistrationException;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class BaseTagStringAdapter<T extends Tag> extends TagAdapter<T> {

  protected static final Charset CHARSET = Charset.forName("UTF-8");
  protected static final byte[] INDENT = "  ".getBytes(CHARSET);
  protected static final byte[] OPEN = "{".getBytes(CHARSET);
  protected static final byte[] CLOSE = "}".getBytes(CHARSET);
  protected static final byte[] SEP = System.lineSeparator().getBytes(CHARSET);

  @Override
  public T read(Tag parent, AxionInputStream in) throws IOException {
    throw new UnsupportedOperationException();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void write(Tag tag, AxionOutputStream out) throws IOException {

    @SuppressWarnings("rawtypes")
    TagAdapter adapter = null;
    try {
      adapter = getAdapterFor(tag.getClass());
      adapter.write(tag, out);
    } catch (AxionTagRegistrationException e) {
      writeDefault(tag, out);
    }

  }

  protected void writeDefault(final Tag tag, final AxionOutputStream out) throws IOException {
    applyIndent(tag, out);
    out.write(tag.toString().getBytes(CHARSET));
    out.write(SEP);
  }

  protected void applyIndent(final Tag tag, final AxionOutputStream out) throws IOException {
    Tag parent = tag.getParent();
    if (parent != null) {
      out.write(INDENT);
      applyIndent(parent, out);
    }
  }

}
