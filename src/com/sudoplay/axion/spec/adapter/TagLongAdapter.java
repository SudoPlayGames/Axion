package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;

public class TagLongAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out) throws IOException {
    out.writeLong(((TagLong) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
    if (parent instanceof TagList) {
      return new TagLong(null, in.readLong());
    } else {
      return new TagLong(in.readUTF(), in.readLong());
    }
  }

}
