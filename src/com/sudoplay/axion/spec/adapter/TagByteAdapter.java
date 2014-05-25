package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagList;

public class TagByteAdapter implements TagAdapter<TagByte> {

  @Override
  public void write(TagByte tag, DataOutputStream out, final Axion axion) throws IOException {
    out.writeByte(tag.get());
  }

  @Override
  public TagByte read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : axion.readString(in), in.readByte());
  }

}
