package com.sudoplay.axion.string.adapter;

import java.io.IOException;
import java.util.Collection;

import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class CompoundTagStringAdapter extends BaseTagStringAdapter<TagCompound> {

  @Override
  public void write(TagCompound tag, AxionOutputStream out) throws IOException {
    writeDefault(tag, out);
    applyIndent(tag, out);
    out.write(OPEN);
    out.write(SEP);
    Collection<Tag> collection = ((TagCompound) tag).getAsMap().values();
    for (Tag t : collection) {
      getBaseTagAdapter().write(t, out);
    }
    applyIndent(tag, out);
    out.write(CLOSE);
    out.write(SEP);
  }

}
