package com.sudoplay.axion.spec.adapter;

import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link TagAdapter} used to read and write a {@link TagList}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagListAdapter extends TagAdapter<TagList> {

  private static final Logger LOG = LoggerFactory.getLogger(TagListAdapter.class);

  @Override
  public void write(final TagList tag, final AxionOutputStream out) throws IOException {
    LOG.debug("Entering write(tag=[{}], out=[{}])", tag, out);
    int size = tag.size();
    int type = getIdFor(tag.getType());
    out.writeByte(type);
    out.writeInt(size);
    TagAdapter<Tag> adapter = getAdapterFor(type);
    Tag child;
    for (int i = 0; i < size; i++) {
      child = tag.get(i);
      adapter.write(child, out);
    }
    LOG.debug("Leaving write()");
  }

  @Override
  public TagList read(final Tag parent, final AxionInputStream in) throws IOException {
    LOG.debug("Entering read(parent=[{}], in=[{}])", parent, in);
    String name = (parent instanceof TagList) ? null : in.readString();
    Class<? extends Tag> type = getClassFor(in.readUnsignedByte());
    int size = in.readInt();
    TagList tagList = new TagList(type, name, new ArrayList<>());
    TagAdapter<? extends Tag> adapter = getAdapterFor(type);
    Tag child;
    for (int i = 0; i < size; i++) {
      child = adapter.read(tagList, in);
      tagList.add(child);
    }
    LOG.debug("Leaving read(): [{}]", tagList);
    return tagList;
  }

  @Override
  public StringBuilder toString(final Tag tag, final StringBuilder out) {
    super.toString(tag, out);
    applyIndent(tag, out).append(OPEN).append(SEP);
    List<Tag> list = ((TagList) tag).getAsList();
    for (Tag t : list) {
      getBaseTagAdapter().toString(t, out);
    }
    applyIndent(tag, out).append(CLOSE).append(SEP);
    return out;
  }

}
