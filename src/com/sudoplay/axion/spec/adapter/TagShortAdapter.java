package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagShort;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagShortAdapter extends TagAdapter<TagShort> {

  @Override
  public void write(final TagShort tag, final AxionOutputStream out) throws IOException {
    out.writeShort(tag.get());
  }

  @Override
  public TagShort read(final Tag parent, final AxionInputStream in) throws IOException {
    return convertToTag((parent instanceof TagList) ? null : in.readString(), in.readShort());
  }

}
