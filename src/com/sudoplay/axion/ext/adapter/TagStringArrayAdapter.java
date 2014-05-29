package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagStringArray}.
 * <p>
 * Part of the extended, custom specification.
 * 
 * @author Jason Taylor
 */
public class TagStringArrayAdapter extends TagAdapter<TagStringArray> {

  @Override
  public TagStringArray read(final Tag parent, final AxionInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    String[] data = new String[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readString();
    }
    return convertToTag(name, data);
  }

  @Override
  public void write(final TagStringArray tag, final AxionOutputStream out) throws IOException {
    String[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (int i = 0; i < len; i++) {
      out.writeString(data[i]);
    }
  }

}
