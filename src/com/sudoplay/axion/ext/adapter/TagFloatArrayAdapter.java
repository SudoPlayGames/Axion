package com.sudoplay.axion.ext.adapter;

import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The {@link TagAdapter} used to read and write a {@link TagFloatArray}.
 * <p>
 * Part of the extended, custom specification.
 *
 * @author Jason Taylor
 */
public class TagFloatArrayAdapter extends TagAdapter<TagFloatArray> {

  private static final Logger LOG = LoggerFactory.getLogger(TagFloatArrayAdapter.class);

  @Override
  public TagFloatArray read(Tag parent, AxionInputStream in) throws IOException {
    LOG.trace("Entering read(parent=[{}], in=[{}])", parent, in);
    String name = (parent instanceof TagList) ? null : in.readString();
    int len = in.readInt();
    float[] data = new float[len];
    for (int i = 0; i < len; i++) {
      data[i] = in.readFloat();
    }
    TagFloatArray result = convertToTag(name, data);
    LOG.trace("Leaving read(): [{}]", result);
    return result;
  }

  @Override
  public void write(TagFloatArray tag, AxionOutputStream out) throws IOException {
    LOG.trace("Entering write(tag=[{}], out=[{}])", tag, out);
    float[] data = tag.get();
    int len = data.length;
    out.writeInt(len);
    for (float aData : data) {
      out.writeFloat(aData);
    }
    LOG.trace("Leaving write()");
  }

}
