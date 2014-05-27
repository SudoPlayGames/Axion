package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagByteAdapter extends TagAdapter<TagByte> {

  @Override
  public void write(TagByte tag, AxionOutputStream out) throws IOException {
    out.writeByte(tag.get());
  }

  @Override
  public TagByte read(final Tag parent, final AxionInputStream in) throws IOException {
    return convertToTag((parent instanceof TagList) ? null : in.readString(), in.readByte());
  }

}
