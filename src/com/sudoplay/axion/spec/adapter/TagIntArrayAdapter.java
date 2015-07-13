package com.sudoplay.axion.spec.adapter;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagIntArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagIntArray}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagIntArrayAdapter extends TagAdapter<TagIntArray> {

  private static final Logger LOG = LoggerFactory.getLogger(TagIntArrayAdapter.class);

  @Override
  public void write(final TagIntArray tag, final AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    int[] data = (tag.get());
    int len = data.length;
    out.writeInt(len);
    for (int aData : data) {
      out.writeInt(aData);
    }
    LOG.trace("Leaving write()");
  }

  @Override
  public TagIntArray read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    int[] data = new int[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readInt();
    }
    TagIntArray result = convertToTag(name, data);
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

}
