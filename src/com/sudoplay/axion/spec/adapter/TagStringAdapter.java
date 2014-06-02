package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagString}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagStringAdapter extends TagAdapter<TagString> {

  private static final Logger LOG = LoggerFactory.getLogger(TagStringAdapter.class);

  @Override
  public void write(final TagString tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeString(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagString read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagString result = convertToTag((parent instanceof TagList) ? null : in.readString(), in.readString());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
