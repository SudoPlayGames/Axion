package com.sudoplay.axion.adapter.spec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagByte;
import com.sudoplay.axion.tag.spec.TagList;

public class TagByteAdapter implements TagAdapter {

  @Override
  public void write(Tag tag, DataOutputStream out) throws IOException {
    out.writeByte(((TagByte) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
    if (parent instanceof TagList) {
      return new TagByte(null, in.readByte());
    } else {
      return new TagByte(in.readUTF(), in.readByte());
    }
  }

}
