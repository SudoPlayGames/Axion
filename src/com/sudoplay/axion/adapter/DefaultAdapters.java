package com.sudoplay.axion.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.standard.Tag;
import com.sudoplay.axion.tag.standard.TagByte;
import com.sudoplay.axion.tag.standard.TagByteArray;
import com.sudoplay.axion.tag.standard.TagCompound;
import com.sudoplay.axion.tag.standard.TagDouble;
import com.sudoplay.axion.tag.standard.TagFloat;
import com.sudoplay.axion.tag.standard.TagInt;
import com.sudoplay.axion.tag.standard.TagIntArray;
import com.sudoplay.axion.tag.standard.TagList;
import com.sudoplay.axion.tag.standard.TagLong;
import com.sudoplay.axion.tag.standard.TagShort;
import com.sudoplay.axion.tag.standard.TagString;

public class DefaultAdapters {

  private static final byte TAG_END_ID = 0;

  private static final Logger LOG = LoggerFactory.getLogger(DefaultAdapters.class);

  public static final Adapter TAG_BYTE_ADAPTER = new Adapter() {
    @Override
    public void write(Tag tag, DataOutputStream out) throws IOException {
      out.writeByte(((TagByte) tag).get());
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagByte(null, in.readByte());
      } else {
        return new TagByte(in.readUTF(), in.readByte());
      }
    }
  };

  public static final Adapter TAG_SHORT_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      out.writeShort(((TagShort) tag).get());
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagShort(null, in.readShort());
      } else {
        return new TagShort(in.readUTF(), in.readShort());
      }
    }
  };

  public static final Adapter TAG_INT_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      out.writeInt(((TagInt) tag).get());
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagInt(null, in.readInt());
      } else {
        return new TagInt(in.readUTF(), in.readInt());
      }
    }
  };

  public static final Adapter TAG_LONG_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      out.writeLong(((TagLong) tag).get());
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagLong(null, in.readLong());
      } else {
        return new TagLong(in.readUTF(), in.readLong());
      }
    }
  };

  public static final Adapter TAG_FLOAT_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      out.writeFloat(((TagFloat) tag).get());
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagFloat(null, in.readFloat());
      } else {
        return new TagFloat(in.readUTF(), in.readFloat());
      }
    }
  };

  public static final Adapter TAG_DOUBLE_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      out.writeDouble(((TagDouble) tag).get());
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagDouble(null, in.readDouble());
      } else {
        return new TagDouble(in.readUTF(), in.readDouble());
      }
    }
  };

  public static final Adapter TAG_BYTE_ARRAY_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      byte[] data = ((TagByteArray) tag).get();
      out.writeInt(data.length);
      out.write(data);
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        byte[] data = new byte[in.readInt()];
        in.readFully(data);
        return new TagByteArray(null, data);
      } else {
        String name = in.readUTF();
        byte[] data = new byte[in.readInt()];
        in.readFully(data);
        return new TagByteArray(name, data);
      }
    }
  };

  public static final Adapter TAG_STRING_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      out.writeUTF(((TagString) tag).get());
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagString(null, in.readUTF());
      } else {
        return new TagString(in.readUTF(), in.readUTF());
      }
    }
  };

  public static final Adapter TAG_LIST_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      TagList tagList = (TagList) tag;
      int size = tagList.size();
      int type = tagList.getType();
      out.writeByte((size == 0) ? TagByte.TAG_ID : type);
      out.writeInt(size);
      Adapter adapter = Axion.getAdapterFor(type);
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
      Adapter adapter = Axion.getAdapterFor(type);
      Tag child;
      for (int i = 0; i < size; i++) {
        LOG.trace("reading #[{}]", i);
        child = adapter.read(tagList, in);
        tagList.add(child);
        LOG.trace("finished reading [{}]", child);
      }
      return tagList;
    }
  };

  public static final Adapter TAG_COMPOUND_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      for (Tag child : ((TagCompound) tag).getAsMap().values()) {
        Axion.writeTag(child, out);
      }
      out.writeByte(TAG_END_ID);
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      String name = (parent instanceof TagList) ? null : in.readUTF();
      TagCompound tag = new TagCompound(name);
      Tag child;
      while ((child = Axion.readTag(tag, in)) != null) {
        tag.put(child.getName(), child);
      }
      return tag;
    }
  };

  public static final Adapter TAG_INT_ARRAY_ADAPTER = new Adapter() {
    @Override
    public void write(final Tag tag, final DataOutputStream out) throws IOException {
      int[] data = ((TagIntArray) tag).get();
      int len = data.length;
      out.writeInt(len);
      for (int i = 0; i < len; i++) {
        out.writeInt(data[i]);
      }
    }

    @Override
    public Tag read(final Tag parent, final DataInputStream in) throws IOException {
      String name = (parent instanceof TagList) ? null : in.readUTF();
      int len = in.readInt();
      int[] data = new int[len];
      for (int i = 0; i < len; i++) {
        data[i] = in.readInt();
      }
      return new TagIntArray(name, data);
    }
  };

}
