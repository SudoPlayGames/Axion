package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagStringAdapter extends TagAdapter<TagString> {

  @Override
  public void write(final TagString tag, final AxionOutputStream out) throws IOException {
    out.writeString(tag.get());
  }

  @Override
  public TagString read(final Tag parent, final AxionInputStream in) throws IOException {
    return convertToTag((parent instanceof TagList) ? null : in.readString(), in.readString());
  }

}
