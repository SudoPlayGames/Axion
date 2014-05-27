package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagCompoundAdapter implements TagAdapter<TagCompound> {

  @Override
  public void write(final TagCompound tag, final AxionOutputStream out, final Axion axion) throws IOException {
    for (Tag child : tag.getAsMap().values()) {
      axion.writeTag(child, out);
    }
    out.writeByte(0);
  }

  @Override
  public TagCompound read(final Tag parent, final AxionInputStream in, final Axion axion) throws IOException {
    TagCompound tag = new TagCompound((parent instanceof TagList) ? null : in.readString());
    Tag child;
    while ((child = axion.readTag(tag, in)) != null) {
      tag.put(child);
    }
    return tag;
  }

}
