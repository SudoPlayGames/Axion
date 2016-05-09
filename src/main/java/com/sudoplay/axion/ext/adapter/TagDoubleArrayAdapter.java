package com.sudoplay.axion.ext.adapter;

import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagDoubleArray}.
 * <p>
 * Part of the extended, custom specification.
 *
 * @author Jason Taylor
 */
public class TagDoubleArrayAdapter extends TagAdapter<TagDoubleArray> {

  private static final Logger LOG = LoggerFactory.getLogger(TagDoubleArrayAdapter.class);

  @Override
  public TagDoubleArray read(Tag parent, AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    double[] data = new double[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readDouble();
    }
    TagDoubleArray result = new TagDoubleArray(name, data);
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

  @Override
  public void write(TagDoubleArray tag, AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    double[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (double aData : data) {
      out.writeDouble(aData);
    }
    LOG.trace("Leaving write()");
  }

}
