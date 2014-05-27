package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagFloatArrayAdapter implements TagAdapter<TagFloatArray> {

  @Override
  public TagFloatArray read(Tag parent, AxionInputStream in, Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    float[] data = new float[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readFloat();
    }
    return axion.convertToTag(name, data);
  }

  @Override
  public void write(TagFloatArray tag, AxionOutputStream out, Axion axion) throws IOException {
    float[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeFloat(data[i]);
    }
  }

}
