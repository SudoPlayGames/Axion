package com.sudoplay.axion.spec.adapter;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagDouble}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagDoubleAdapter extends TagAdapter<TagDouble> {

  private static final Logger LOG = LoggerFactory.getLogger(TagDoubleAdapter.class);

  @Override
  public void write(final TagDouble tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeDouble(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagDouble read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagDouble result = new TagDouble((parent instanceof TagList) ? null : in.readString(), in.readDouble());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
