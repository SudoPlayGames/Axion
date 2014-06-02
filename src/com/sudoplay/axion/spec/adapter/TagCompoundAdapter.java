package com.sudoplay.axion.spec.adapter;

import java.io.IOException;
import java.util.Collection;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagCompound}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
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

  @Override
  public StringBuilder toString(Tag tag, StringBuilder out) {
    super.toString(tag, out);
    applyIndent(tag, out).append(OPEN).append(SEP);
    Collection<Tag> collection = ((TagCompound) tag).getAsMap().values();
    for (Tag t : collection) {
      getBaseTagAdapter().toString(t, out);
    }
    applyIndent(tag, out).append(CLOSE).append(SEP);
    return out;
  }

}
