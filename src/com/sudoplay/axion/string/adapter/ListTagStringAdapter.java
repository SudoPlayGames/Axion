package com.sudoplay.axion.string.adapter;

import java.io.IOException;
import java.util.List;

import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class ListTagStringAdapter extends BaseTagStringAdapter<TagList> {

  @Override
  public void write(TagList tag, AxionOutputStream out) throws IOException {
    writeDefault(tag, out);
    applyIndent(tag, out);
    out.write(OPEN);
    out.write(SEP);
    List<Tag> list = ((TagList) tag).getAsList();
    for (Tag t : list) {
      getBaseTagAdapter().write(t, out);
    }
    applyIndent(tag, out);
    out.write(CLOSE);
    out.write(SEP);
  }

}
