package com.sudoplay.axion.adapter.extended;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.tag.extended.TagBoolean;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagList;

public class TagBooleanAdapter implements TagAdapter {

  @Override
  public void write(Tag tag, DataOutputStream out) throws IOException {
    out.writeBoolean(((TagBoolean) tag).get());
  }

  @Override
  public Tag read(Tag parent, DataInputStream in) throws IOException {
    if (parent instanceof TagList) {
      return new TagBoolean(null, in.readBoolean());
    } else {
      return new TagBoolean(in.readUTF(), in.readBoolean());
    }
  }
}
