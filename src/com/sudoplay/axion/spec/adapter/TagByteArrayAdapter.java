package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagList;

public class TagByteArrayAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out, final Axion axion) throws IOException {
    byte[] data = ((TagByteArray) tag).get();
    out.writeInt(data.length);
    out.write(data);
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in, final Axion axion) throws IOException {
    if (parent instanceof TagList) {
      byte[] data = new byte[in.readInt()];
      in.readFully(data);
      return new TagByteArray(null, data);
    } else {
      String name = in.readUTF();
      byte[] data = new byte[in.readInt()];
      in.readFully(data);
      return new TagByteArray(name, data);
    }
  }

}
