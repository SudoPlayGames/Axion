package com.sudoplay.axion.ext.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;

public class TagBooleanAdapter implements TagAdapter<TagBoolean> {

  @Override
  public void write(final TagBoolean tag, final DataOutputStream out, final Axion axion) throws IOException {
    out.writeBoolean(tag.get());
  }

  @Override
  public TagBoolean read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : in.readUTF(), in.readBoolean());
  }
}
