package com.sudoplay.axion.spec.adapter;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagShort;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagShort}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagShortAdapter extends TagAdapter<TagShort> {

  private static final Logger LOG = LoggerFactory.getLogger(TagShortAdapter.class);

  @Override
  public void write(final TagShort tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeShort(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagShort read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagShort result = convertToTag((parent instanceof TagList) ? null : in.readString(), in.readShort());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
