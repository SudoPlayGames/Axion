package com.sudoplay.axion.adapter.spec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagList;
import com.sudoplay.axion.tag.spec.TagShort;

public class TagShortAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out) throws IOException {
    out.writeShort(((TagShort) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
    if (parent instanceof TagList) {
      return new TagShort(null, in.readShort());
    } else {
      return new TagShort(in.readUTF(), in.readShort());
    }
  }

}
