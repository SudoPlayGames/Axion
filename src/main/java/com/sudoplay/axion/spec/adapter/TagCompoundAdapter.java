package com.sudoplay.axion.spec.adapter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

/**
 * The {@link TagAdapter} used to read and write a {@link TagCompound}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagCompoundAdapter extends TagAdapter<TagCompound> {

  private static final Logger LOG = LoggerFactory.getLogger(TagCompoundAdapter.class);

  @Override
  public void write(final TagCompound tag, final AxionOutputStream out) throws IOException {
    LOG.debug("Entering write(tag=[{}], out=[{}])", tag, out);
    for (Tag child : tag.getAsMap().values()) {
      axion.getBaseTagAdapter().write(child, out);
    }
    out.writeByte(0);
    LOG.debug("Leaving write()");
  }

  @Override
  public TagCompound read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.debug("Entering read(parent=[{}], in=[{}])", parent, in);
    TagCompound tag = new TagCompound((parent instanceof TagList) ? null : in.readString());
    Tag child;
    while ((child = axion.getBaseTagAdapter().read(tag, in)) != null) {
      tag.put(child);
    }
    LOG.debug("Leaving read(): [{}]", tag);
    return tag;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Override
  public StringBuilder toString(Tag tag, StringBuilder out) {
    super.toString(tag, out);
    applyIndent(tag, out).append(OPEN).append(SEP);
    Collection<Tag> collection = ((TagCompound) tag).getAsMap().values();
    for (Tag t : collection) {
      axion.getBaseTagAdapter().toString(t, out);
    }
    applyIndent(tag, out).append(CLOSE).append(SEP);
    return out;
  }

}
