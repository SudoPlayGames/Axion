package com.sudoplay.axion.ext.adapter;

import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagBoolean}.
 * <p>
 * Part of the extended, custom specification.
 *
 * @author Jason Taylor
 */
public class TagBooleanAdapter extends TagAdapter<TagBoolean> {

  private static final Logger LOG = LoggerFactory.getLogger(TagBooleanAdapter.class);

  @Override
  public void write(final TagBoolean tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    out.writeBoolean(tag.get());
    LOG.trace("Leaving write()");
  }

  @Override
  public TagBoolean read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    TagBoolean result = convertToTag((parent instanceof TagList) ? null : in.readString(), in.readBoolean());
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }
}
