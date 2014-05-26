package com.sudoplay.axion.spec.adapter;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;

public class TagListAdapter implements TagAdapter<TagList> {

  private static final Logger LOG = LoggerFactory.getLogger(TagListAdapter.class);

  @Override
  public void write(final TagList tag, final AxionOutputStream out, final Axion axion) throws IOException {
    int size = tag.size();
    int type = axion.getIdFor(tag.getType());
    out.writeByte(type);
    out.writeInt(size);
    TagAdapter<Tag> adapter = axion.getAdapterFor(type);
    Tag child;
    for (int i = 0; i < size; i++) {
      LOG.trace("writing #[{}]", i);
      child = tag.get(i);
      adapter.write(child, out, axion);
      LOG.trace("finished writing [{}]", child);
    }
  }

  @Override
  public TagList read(final Tag parent, final AxionInputStream in, final Axion axion) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readString();
    Class<? extends Tag> type = axion.getClassFor(in.readUnsignedByte());
    int size = in.readInt();
    TagList tagList = new TagList(type, name, new ArrayList<Tag>());
    TagAdapter<? extends Tag> adapter = axion.getAdapterFor(type);
    Tag child;
    for (int i = 0; i < size; i++) {
      LOG.trace("reading #[{}]", i);
      child = adapter.read(tagList, in, axion);
      tagList.add(child);
      LOG.trace("finished reading [{}]", child);
    }
    return tagList;
  }
}
