package com.sudoplay.axion.spec.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagList;

public class TagListAdapter implements TagAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(TagListAdapter.class);

  @Override
  public void write(final Tag tag, final DataOutputStream out) throws IOException {
    TagList tagList = (TagList) tag;
    int size = tagList.size();
    int type = tagList.getType();
    out.writeByte((size == 0) ? Axion.getIdFor(TagByte.class) : type);
    out.writeInt(size);
    TagAdapter adapter = Axion.getAdapterFor(type);
    Tag child;
    for (int i = 0; i < size; i++) {
      LOG.trace("writing #[{}]", i);
      child = tagList.get(i);
      adapter.write(child, out);
      LOG.trace("finished writing [{}]", child);
    }
  }

  @Override
  public Tag read(final Tag parent, final DataInputStream in) throws IOException {
    String name = (parent instanceof TagList) ? null : in.readUTF();
    int type = in.readUnsignedByte();
    int size = in.readInt();
    TagList tagList = new TagList(type, name, new ArrayList<Tag>());
    TagAdapter adapter = Axion.getAdapterFor(type);
    Tag child;
    for (int i = 0; i < size; i++) {
      LOG.trace("reading #[{}]", i);
      child = adapter.read(tagList, in);
      tagList.add(child);
      LOG.trace("finished reading [{}]", child);
    }
    return tagList;
  }
}
