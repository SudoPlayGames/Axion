package com.sudoplay.axion.ext.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;

public class TagFloatArrayAdapter implements TagAdapter<TagFloatArray> {

  @Override
  public TagFloatArray read(Tag parent, DataInputStream in, Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readUTF();
    int len = in.readInt();
    float[] data = new float[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readFloat();
    }
    return axion.convertToTag(name, data);
  }

  @Override
  public void write(TagFloatArray tag, DataOutputStream out, Axion axion) throws IOException {
    float[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeFloat(data[i]);
    }
  }

}
