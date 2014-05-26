package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;

public class TagLongAdapter implements TagAdapter<TagLong> {

  @Override
  public void write(final TagLong tag, final AxionOutputStream out, final Axion axion) throws IOException {
    out.writeLong(tag.get());
  }

  @Override
  public TagLong read(final Tag parent, final AxionInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : in.readString(), in.readLong());
  }

}
