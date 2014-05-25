package com.sudoplay.axion.ext.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;

public class TagStringArrayAdapter implements TagAdapter<TagStringArray> {

  @Override
  public TagStringArray read(Tag parent, DataInputStream in, Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : axion.readString(in);
    int len = in.readInt();
    String[] data = new String[len];
    for (int i = 0; i < len; i++) {
      data[i] = axion.readString(in);
    }
    return axion.convertToTag(name, data);
  }

  @Override
  public void write(TagStringArray tag, DataOutputStream out, Axion axion) throws IOException {
    String[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      axion.writeString(out, data[i]);
    }
  }

}
