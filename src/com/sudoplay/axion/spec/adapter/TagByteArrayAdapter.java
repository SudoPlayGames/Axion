package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagByteArray}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagByteArrayAdapter extends TagAdapter<TagByteArray> {

  @Override
  public void write(final TagByteArray tag, final AxionOutputStream out) throws IOException {
    byte[] data = (tag.get());
    out.writeInt(data.length);
    out.write(data);
  }

  @Override
  public TagByteArray read(final Tag parent, final AxionInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    byte[] data = new byte[in.readInt()];
    in.readFully(data);
    return convertToTag(name, data);
  }

}
