package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagBooleanArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagBooleanArrayAdapter implements TagAdapter<TagBooleanArray> {

  private static final int[] POW = new int[] { 1, 2, 4, 8, 16, 32, 64, 128 };

  @Override
  public TagBooleanArray read(Tag parent, AxionInputStream in, Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int byteLen = in.readInt();
    int boolLen = in.readInt();
    byte[] bytes = new byte[byteLen];
    in.readFully(bytes);
    boolean[] bool = new boolean[boolLen];
    for (int i = 0; i < boolLen; i++) {
      bool[i] = (bytes[bytes.length - i / 8 - 1] & POW[i % 8]) != 0;
    }
    return axion.convertToTag(name, bool);
  }

  @Override
  public void write(TagBooleanArray tag, AxionOutputStream out, Axion axion) throws IOException {
    boolean[] data = tag.get();
    int len = data.length;
    byte[] bytes = new byte[(len + 7) / 8];
    for (int i = 0; i < len; i++) {
      if (data[i]) {
        bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
      }
    }
    out.writeInt(bytes.length);
    out.writeInt(len);
    out.write(bytes);
  }
}
