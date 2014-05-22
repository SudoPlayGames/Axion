package com.sudoplay.axion.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sudoplay.axion.tag.Abstract_Tag;

public interface Interface_Adapter {

  public Abstract_Tag read(final Abstract_Tag parent, final InputStream in) throws IOException;

  public void write(final Abstract_Tag tag, final OutputStream out) throws IOException;

}
