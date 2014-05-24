package com.sudoplay.axion.adapter.spec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagByteArray;
import com.sudoplay.axion.tag.spec.TagList;

public class TagByteArrayAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out) throws IOException {
    byte[] data = ((TagByteArray) tag).get();
    out.writeInt(data.length);
    out.write(data);
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
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
