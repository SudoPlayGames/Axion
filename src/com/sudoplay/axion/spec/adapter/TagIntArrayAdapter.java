package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagIntArray;
import com.sudoplay.axion.spec.tag.TagList;

public class TagIntArrayAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out, final Axion axion) throws IOException {
    int[] data = ((TagIntArray) tag).get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeInt(data[i]);
    }
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readUTF();
    int len = in.readInt();
    int[] data = new int[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readInt();
    }
    return new TagIntArray(name, data);
  }

}