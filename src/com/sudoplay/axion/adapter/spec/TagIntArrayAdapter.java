package com.sudoplay.axion.adapter.spec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagIntArray;
import com.sudoplay.axion.tag.spec.TagList;

public class TagIntArrayAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out) throws IOException {
    int[] data = ((TagIntArray) tag).get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeInt(data[i]);
    }
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readUTF();
    int len = in.readInt();
    int[] data = new int[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readInt();
    }
    return new TagIntArray(name, data);
  }

}
