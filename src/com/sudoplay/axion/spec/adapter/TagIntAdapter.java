package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagList;

public class TagIntAdapter implements TagAdapter<TagInt> {

  @Override
  public void write(final TagInt tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeInt(tag.get());
  }

  @Override
  public TagInt read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : axion.readString(in), in.readInt());
  }

}
