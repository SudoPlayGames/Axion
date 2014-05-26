package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;

public class TagStringAdapter implements TagAdapter<TagString> {

  @Override
  public void write(final TagString tag, final AxionOutputStream out, final Axion axion) throws IOException {
    out.writeString(tag.get());
  }

  @Override
  public TagString read(final Tag parent, final AxionInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : in.readString(), in.readString());
  }

}
