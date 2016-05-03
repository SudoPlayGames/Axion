package com.sudoplay.axion.ext.adapter;

import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagLongArray}.
 * <p>
 * Part of the extended, custom specification.
 *
 * @author Jason Taylor
 */
public class TagLongArrayAdapter extends TagAdapter<TagLongArray> {

  private static final Logger LOG = LoggerFactory.getLogger(TagLongArrayAdapter.class);

  @Override
  public TagLongArray read(Tag parent, AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    long[] data = new long[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readLong();
    }
    TagLongArray result = new TagLongArray(name, data);
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

  @Override
  public void write(TagLongArray tag, AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    long[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (long aData : data) {
      out.writeLong(aData);
    }
    LOG.trace("Leaving write()");
  }

}
