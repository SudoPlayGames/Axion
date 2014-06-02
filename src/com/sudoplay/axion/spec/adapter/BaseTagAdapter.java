package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to as the entry point to read and write a tag
 * hierarchy.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class BaseTagAdapter extends TagAdapter<Tag> {

  private static final Logger LOG = LoggerFactory.getLogger(BaseTagAdapter.class);

  @Override
  public Tag read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.debug("Entering read(parent=[{}], in=[{}])", parent, in);
    int id = in.readUnsignedByte();
    if (id == 0) {
      LOG.debug("Leaving read(): null");
      return null;
    } else {
      Tag tag = getAdapterFor(id).read(parent, in);
      LOG.debug("Leaving read(): [{}]", tag);
      return tag;
    }
  }

  @Override
  public void write(final Tag tag, final AxionOutputStream out) throws IOException {
    LOG.debug("Entering write(tag=[{}], out=[{}])", tag, out);
    int id = getIdFor(tag.getClass());
    out.writeByte(id);
    if (!(tag.getParent() instanceof TagList)) {
      out.writeString(tag.getName());
    }
    getAdapterFor(id).write(tag, out);
    LOG.debug("Leaving write()");
  }

  @Override
  public StringBuilder toString(final Tag tag, final StringBuilder out) {
    return getAdapterFor(tag.getClass()).toString(tag, out);
  }

}
