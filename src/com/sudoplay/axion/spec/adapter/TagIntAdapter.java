package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagInt}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagIntAdapter extends TagAdapter<TagInt> {

  private static final Logger LOG = LoggerFactory.getLogger(TagIntAdapter.class);

  @Override
  public void write(final TagInt tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeInt(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagInt read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagInt result = convertToTag((parent instanceof TagList) ? null : in.readString(), in.readInt());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
