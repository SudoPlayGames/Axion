package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagList;

public class TagFloatAdapter implements TagAdapter<TagFloat> {

  @Override
  public void write(final TagFloat tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeFloat(tag.get());
  }

  @Override
  public TagFloat read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : axion.readString(in), in.readFloat());
  }

}
