package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;

public class TagLongAdapter implements TagAdapter<TagLong> {

  @Override
  public void write(final TagLong tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeLong(tag.get());
  }

  @Override
  public TagLong read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : in.readUTF(), in.readLong());
  }

}
