package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagList;

public class TagDoubleAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeDouble(((TagDouble) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    if (parent instanceof TagList) {
      return new TagDouble(null, in.readDouble());
    } else {
      return new TagDouble(in.readUTF(), in.readDouble());
    }
  }

}
