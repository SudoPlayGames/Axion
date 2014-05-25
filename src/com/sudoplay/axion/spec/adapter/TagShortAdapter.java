package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagShort;

public class TagShortAdapter implements TagAdapter<TagShort> {

  @Override
  public void write(final TagShort tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeShort(tag.get());
  }

  @Override
  public TagShort read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : in.readUTF(), in.readShort());
  }

}
