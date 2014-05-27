package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagCompoundAdapter extends TagAdapter<TagCompound> {

  @Override
  public void write(final TagCompound tag, final AxionOutputStream out) throws IOException {
    for (Tag child : tag.getAsMap().values()) {
      getBaseTagAdapter().write(child, out);
    }
    out.writeByte(0);
  }

  @Override
  public TagCompound read(final Tag parent, final AxionInputStream in) throws IOException {
    TagCompound tag = new TagCompound((parent instanceof TagList) ? null : in.readString());
    Tag child;
    while ((child = getBaseTagAdapter().read(tag, in)) != null) {
      tag.put(child);
    }
    return tag;
  }

}
