package com.sudoplay.axion.adapter.spec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagInt;
import com.sudoplay.axion.tag.spec.TagList;

public class TagIntAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out) throws IOException {
    out.writeInt(((TagInt) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
    if (parent instanceof TagList) {
      return new TagInt(null, in.readInt());
    } else {
      return new TagInt(in.readUTF(), in.readInt());
    }
  }

}
