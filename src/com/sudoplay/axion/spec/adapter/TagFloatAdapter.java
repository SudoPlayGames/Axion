package com.sudoplay.axion.spec.adapter;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagFloat}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagFloatAdapter extends TagAdapter<TagFloat> {

  private static final Logger LOG = LoggerFactory.getLogger(TagFloatAdapter.class);

  @Override
  public void write(final TagFloat tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeFloat(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagFloat read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagFloat result = convertToTag((parent instanceof TagList) ? null : in.readString(), in.readFloat());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
