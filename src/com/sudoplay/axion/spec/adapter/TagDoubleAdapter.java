package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagList;

public class TagDoubleAdapter implements TagAdapter<TagDouble> {

  @Override
  public void write(final TagDouble tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeDouble(tag.get());
  }

  @Override
  public TagDouble read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : axion.readString(in), in.readDouble());
  }

}
