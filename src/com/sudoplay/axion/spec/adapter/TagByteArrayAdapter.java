package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagByteArray}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagByteArrayAdapter extends TagAdapter<TagByteArray> {

  private static final Logger LOG = LoggerFactory.getLogger(TagByteArrayAdapter.class);

  @Override
  public void write(final TagByteArray tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    byte[] data = (tag.get());
    out.writeInt(data.length);
    out.write(data);
    LOG.trace("Leaving write()");
  }

  @Override
  public TagByteArray read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    String name = (parent instanceof TagList) ? null : in.readString();
    byte[] data = new byte[in.readInt()];
    in.readFully(data);
    TagByteArray result = convertToTag(name, data);
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
