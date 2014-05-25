package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagString;

public class TagStringAdapter implements TagAdapter<TagString> {

  @Override
  public void write(final TagString tag, final DataOutputStream out, final Axion axion) throws IOException {
    axion.writeString(out, tag.get());
  }

  @Override
  public TagString read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : axion.readString(in), axion.readString(in));
  }

}
