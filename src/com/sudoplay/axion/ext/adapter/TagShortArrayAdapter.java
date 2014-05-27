package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagShortArrayAdapter extends TagAdapter<TagShortArray> {

  @Override
  public TagShortArray read(Tag parent, AxionInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    short[] data = new short[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readShort();
    }
    return convertToTag(name, data);
  }

  @Override
  public void write(TagShortArray tag, AxionOutputStream out) throws IOException {
    short[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeShort(data[i]);
    }
  }

}
