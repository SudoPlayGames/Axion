package com.sudoplay.axion.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sudoplay.axion.tag.Tag;

public interface Adapter {

  public Tag read(final Tag parent, final InputStream in) throws IOException;

  public void write(final Tag tag, final OutputStream out) throws IOException;

}
