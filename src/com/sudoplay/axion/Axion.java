package com.sudoplay.axion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.adapter.Interface_Adapter;
import com.sudoplay.axion.adapter.impl.DefaultAdapter;
import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.TagFactory;
import com.sudoplay.axion.tag.impl.TagEnd;

public class Axion {

  private static final Logger LOG = LoggerFactory.getLogger(Axion.class);
  
  private Interface_Adapter adapter;

  public Axion() {
    adapter = new DefaultAdapter();
  }

  public Abstract_Tag read(final DataInput dataInput) throws IOException {
    /*byte id = dataInput.readByte();
    if (id == TagEnd.TAG_ID) {
      return new TagEnd();
    } else {
      String name = dataInput.readUTF();
      Abstract_Tag tag = TagFactory.create(id, name);
      tag.read(this, dataInput);
      return tag;
    }*/
    return adapter.read(dataInput);
  }

  public void write(final Abstract_Tag tag, final DataOutput dataOutput) throws IOException {
    /*dataOutput.writeByte(tag.getTagId());
    if (tag.getTagId() != TagEnd.TAG_ID) {
      dataOutput.writeUTF(tag.getName());
      tag.write(this, dataOutput);
    }*/
    adapter.write(tag, dataOutput);
  }

}
