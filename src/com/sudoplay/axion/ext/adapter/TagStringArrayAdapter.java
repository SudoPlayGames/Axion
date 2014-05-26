package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;

public class TagStringArrayAdapter implements TagAdapter<TagStringArray> {

  @Override
  public TagStringArray read(Tag parent, AxionInputStream in, Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    String[] data = new String[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readString();
    }
    return axion.convertToTag(name, data);
  }

  @Override
  public void write(TagStringArray tag, AxionOutputStream out, Axion axion) throws IOException {
    String[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeString(data[i]);
    }
  }

}
