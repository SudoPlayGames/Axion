package com.sudoplay.axion.spec.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;

public class TagFloatAdapter implements TagAdapter<TagFloat> {

  @Override
  public void write(final TagFloat tag, final AxionOutputStream out, final Axion axion) throws IOException {
    out.writeFloat(tag.get());
  }

  @Override
  public TagFloat read(final Tag parent, final AxionInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : in.readString(), in.readFloat());
  }

}
