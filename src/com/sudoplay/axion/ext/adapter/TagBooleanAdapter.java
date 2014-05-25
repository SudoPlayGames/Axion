package com.sudoplay.axion.ext.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;

public class TagBooleanAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeBoolean(((TagBoolean) tag).get());
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    if (parent instanceof TagList) {
      return new TagBoolean(null, in.readBoolean());
    } else {
      return new TagBoolean(in.readUTF(), in.readBoolean());
    }
  }
}
