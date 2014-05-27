package com.sudoplay.axion.ext.adapter;

import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class TagBooleanAdapter extends TagAdapter<TagBoolean> {

  @Override
  public void write(final TagBoolean tag, final AxionOutputStream out) throws IOException {
    out.writeBoolean(tag.get());
  }

  @Override
  public TagBoolean read(final Tag parent, final AxionInputStream in) throws IOException {
    return convertToTag((parent instanceof TagList) ? null : in.readString(), in.readBoolean());
  }
}
