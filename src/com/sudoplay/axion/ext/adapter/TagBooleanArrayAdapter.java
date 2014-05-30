package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagBooleanArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagBooleanArray}. The
 * booleans are read and written as bits; 8 bits per byte.
 * <p>
 * Part of the extended, custom specification.
 * 
 * @author Jason Taylor
 */
public class TagBooleanArrayAdapter extends TagAdapter<TagBooleanArray> {

  private static final int[] POW = new int[] { 1, 2, 4, 8, 16, 32, 64, 128 };

  @Override
  public TagBooleanArray read(Tag parent, AxionInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int boolLen = in.readInt();
    int byteLen = (boolLen + 7) / 8;
    byte[] bytes = new byte[byteLen];
    in.readFully(bytes);
    boolean[] bool = new boolean[boolLen];
    for (int i = 0; i < boolLen; i++) {
      bool[i] = (bytes[bytes.length - i / 8 - 1] & POW[i % 8]) != 0;
    }
    return convertToTag(name, bool);
  }

  @Override
  public void write(TagBooleanArray tag, AxionOutputStream out) throws IOException {
    boolean[] data = tag.get();
    int len = data.length;
    byte[] bytes = new byte[(len + 7) / 8];
    for (int i = 0; i < len; i++) {
      if (data[i]) {
        bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
      }
    }
    out.writeInt(len);
    out.write(bytes);
  }
}
