package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagLong}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagLongAdapter extends TagAdapter<TagLong> {

  private static final Logger LOG = LoggerFactory.getLogger(TagLongAdapter.class);

  @Override
  public void write(final TagLong tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeLong(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagLong read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagLong result = convertToTag((parent instanceof TagList) ? null : in.readString(), in.readLong());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
