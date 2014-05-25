package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagString;

public class TagStringAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeUTF(((TagString) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    if (parent instanceof TagList) {
      return new TagString(null, in.readUTF());
    } else {
      return new TagString(in.readUTF(), in.readUTF());
    }
  }

}
