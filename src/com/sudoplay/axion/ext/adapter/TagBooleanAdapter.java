package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;

public class TagBooleanAdapter implements TagAdapter<TagBoolean> {

  @Override
  public void write(final TagBoolean tag, final AxionOutputStream out, final Axion axion) throws IOException {
    out.writeBoolean(tag.get());
  }

  @Override
  public TagBoolean read(final Tag parent, final AxionInputStream in, final Axion axion) throws IOException {
    return axion.convertToTag((parent instanceof TagList) ? null : in.readString(), in.readBoolean());
  }
}
