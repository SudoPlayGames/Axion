package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;

public class TagByteArrayAdapter implements TagAdapter<TagByteArray> {

  @Override
  public void write(final TagByteArray tag, final AxionOutputStream out, final Axion axion) throws IOException {
    byte[] data = (tag.get());
    out.writeInt(data.length);
    out.write(data);
  }

  @Override
  public TagByteArray read(final Tag parent, final AxionInputStream in, final Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    byte[] data = new byte[in.readInt()];
    in.readFully(data);
    return axion.convertToTag(name, data);
  }

}
