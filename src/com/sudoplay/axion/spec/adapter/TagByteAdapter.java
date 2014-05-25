package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagList;

public class TagByteAdapter implements TagAdapter {

  @Override
  public void write(Tag tag, DataOutputStream out, final Axion axion) throws IOException {
    out.writeByte(((TagByte) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    if (parent instanceof TagList) {
      return new TagByte(null, in.readByte());
    } else {
      return new TagByte(in.readUTF(), in.readByte());
    }
  }

}
