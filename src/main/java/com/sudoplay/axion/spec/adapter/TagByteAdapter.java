package com.sudoplay.axion.spec.adapter;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagByte}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagByteAdapter extends TagAdapter<TagByte> {

  private static final Logger LOG = LoggerFactory.getLogger(TagByteAdapter.class);

  @Override
  public void write(TagByte tag, AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeByte(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagByte read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagByte result = new TagByte((parent instanceof TagList) ? null : in.readString(), in.readByte());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
