package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagLongArrayAdapter extends TagAdapter<TagLongArray> {

  @Override
  public TagLongArray read(Tag parent, AxionInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    long[] data = new long[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readLong();
    }
    return convertToTag(name, data);
  }

  @Override
  public void write(TagLongArray tag, AxionOutputStream out) throws IOException {
    long[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeLong(data[i]);
    }
  }

}
