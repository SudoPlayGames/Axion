package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagAdapter} used to read and write a {@link TagDouble}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
public class TagDoubleAdapter extends TagAdapter<TagDouble> {

  @Override
  public void write(final TagDouble tag, final AxionOutputStream out) throws IOException {
    out.writeDouble(tag.get());
  }

  @Override
  public TagDouble read(final Tag parent, final AxionInputStream in) throws IOException {
    return convertToTag((parent instanceof TagList) ? null : in.readString(), in.readDouble());
  }

}
