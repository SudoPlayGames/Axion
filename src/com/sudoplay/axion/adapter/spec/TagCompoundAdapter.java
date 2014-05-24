package com.sudoplay.axion.adapter.spec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagCompound;
import com.sudoplay.axion.tag.spec.TagList;

public class TagCompoundAdapter implements TagAdapter {

  @Override
  public void write(final Tag tag, final DataOutputStream out) throws IOException {
    for (Tag child : ((TagCompound) tag).getAsMap().values()) {
      Axion.writeTag(child, out);
    }
    out.writeByte(0);
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readUTF();
    TagCompound tag = new TagCompound(name);
    Tag child;
    while ((child = Axion.readTag(tag, in)) != null) {
      tag.put(child.getName(), child);
    }
    return tag;
  }

}
