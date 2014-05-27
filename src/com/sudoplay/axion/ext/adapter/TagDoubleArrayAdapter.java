package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagDoubleArrayAdapter extends TagAdapter<TagDoubleArray> {

  @Override
  public TagDoubleArray read(Tag parent, AxionInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    double[] data = new double[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readDouble();
    }
    return convertToTag(name, data);
  }

  @Override
  public void write(TagDoubleArray tag, AxionOutputStream out) throws IOException {
    double[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeDouble(data[i]);
    }
  }

}
