package com.sudoplay.axion.string.adapter;

import java.io.IOException;

import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class DefaultTagStringAdapter<T extends Tag> extends BaseTagStringAdapter<T> {

  @Override
  public void write(Tag tag, AxionOutputStream out) throws IOException {
    writeDefault(tag, out);
  }

}
