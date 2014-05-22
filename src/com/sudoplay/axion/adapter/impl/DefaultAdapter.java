package com.sudoplay.axion.adapter.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.adapter.Interface_Adapter;
import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.TagHelper;
import com.sudoplay.axion.tag.impl.TagByte;
import com.sudoplay.axion.tag.impl.TagByteArray;
import com.sudoplay.axion.tag.impl.TagCompound;
import com.sudoplay.axion.tag.impl.TagDouble;
import com.sudoplay.axion.tag.impl.TagEnd;
import com.sudoplay.axion.tag.impl.TagFloat;
import com.sudoplay.axion.tag.impl.TagInt;
import com.sudoplay.axion.tag.impl.TagIntArray;
import com.sudoplay.axion.tag.impl.TagList;
import com.sudoplay.axion.tag.impl.TagLong;
import com.sudoplay.axion.tag.impl.TagShort;
import com.sudoplay.axion.tag.impl.TagString;

public class DefaultAdapter implements Interface_Adapter {

  private static final Logger LOG = LoggerFactory.getLogger(DefaultAdapter.class);

  private static final Map<Byte, Interface_InternalAdapter> ADAPTERS = new HashMap<Byte, Interface_InternalAdapter>();

  private static interface Interface_InternalAdapter {

    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException;

    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException;

  }

  private static final Interface_InternalAdapter BASE_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      LOG.trace("writing [{}]", tag);
      byte id = tag.getTagId();
      out.writeByte(id);
      if (id != TagEnd.TAG_ID) {
        if (!(tag.getParent() instanceof TagList)) {
          out.writeUTF(tag.getName());
        }
        ADAPTERS.get(id).write(tag, out);
      }
      LOG.trace("finished writing [{}]", tag);
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      byte id = in.readByte();
      LOG.trace("reading [{}]", TagHelper.getName(id));
      if (id == TagEnd.TAG_ID) {
        LOG.trace("finished reading [{}]", TagEnd.TAG_NAME);
        return TagEnd.INSTANCE;
      } else {
        Abstract_Tag tag = ADAPTERS.get(id).read(parent, in);
        LOG.trace("finished reading [{}]", tag);
        return tag;
      }
    }
  };

  private static final Interface_InternalAdapter TAG_BYTE_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutputStream out) throws IOException {
      out.writeByte(((TagByte) tag).get());
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagByte(null, in.readByte());
      } else {
        return new TagByte(in.readUTF(), in.readByte());
      }
    }
  };

  private static final Interface_InternalAdapter TAG_SHORT_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      out.writeShort(((TagShort) tag).get());
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagShort(null, in.readShort());
      } else {
        return new TagShort(in.readUTF(), in.readShort());
      }
    }
  };

  private static final Interface_InternalAdapter TAG_INT_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      out.writeInt(((TagInt) tag).get());
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagInt(null, in.readInt());
      } else {
        return new TagInt(in.readUTF(), in.readInt());
      }
    }
  };

  private static final Interface_InternalAdapter TAG_LONG_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      out.writeLong(((TagLong) tag).get());
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagLong(null, in.readLong());
      } else {
        return new TagLong(in.readUTF(), in.readLong());
      }
    }
  };

  private static final Interface_InternalAdapter TAG_FLOAT_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      out.writeFloat(((TagFloat) tag).get());
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagFloat(null, in.readFloat());
      } else {
        return new TagFloat(in.readUTF(), in.readFloat());
      }
    }
  };

  private static final Interface_InternalAdapter TAG_DOUBLE_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      out.writeDouble(((TagDouble) tag).get());
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagDouble(null, in.readDouble());
      } else {
        return new TagDouble(in.readUTF(), in.readDouble());
      }
    }
  };

  private static final Interface_InternalAdapter TAG_BYTE_ARRAY_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      byte[] data = ((TagByteArray) tag).get();
      out.writeInt(data.length);
      out.write(data);
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
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

  private static final Interface_InternalAdapter TAG_STRING_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      out.writeUTF(((TagString) tag).get());
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      if (parent instanceof TagList) {
        return new TagString(null, in.readUTF());
      } else {
        return new TagString(in.readUTF(), in.readUTF());
      }
    }
  };

  private static final Interface_InternalAdapter TAG_LIST_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      TagList tagList = (TagList) tag;
      int size = tagList.size();
      byte type = tagList.getType();
      if (size == 0) {
        out.writeByte(TagByte.TAG_ID);
      } else {
        out.writeByte(type);
      }
      out.writeInt(size);
      Interface_InternalAdapter adapter = ADAPTERS.get(type);
      Abstract_Tag child;
      for (int i = 0; i < size; i++) {
        LOG.trace("writing #[{}]", i);
        child = tagList.get(i);
        adapter.write(child, out);
        LOG.trace("finished writing [{}]", child);
      }
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      String name = (parent instanceof TagList) ? null : in.readUTF();
      byte type = in.readByte();
      int size = in.readInt();
      TagList tagList = new TagList(name);
      tagList.overrideType(type);
      Interface_InternalAdapter adapter = ADAPTERS.get(type);
      Abstract_Tag child;
      for (int i = 0; i < size; i++) {
        LOG.trace("reading #[{}]", i);
        child = adapter.read(tagList, in);
        tagList.add(child);
        LOG.trace("finished reading [{}]", child);
      }
      return tagList;
    }
  };

  private static final Interface_InternalAdapter TAG_COMPOUND_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      for (Abstract_Tag child : ((TagCompound) tag).getAsMap().values()) {
        BASE_ADAPTER.write(child, out);
      }
      out.writeByte(TagEnd.TAG_ID);
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      String name = (parent instanceof TagList) ? null : in.readUTF();
      TagCompound tag = new TagCompound(name);
      Abstract_Tag child;
      while ((child = BASE_ADAPTER.read(tag, in)).getTagId() != TagEnd.TAG_ID) {
        tag.put(child.getName(), child);
      }
      return tag;
    }
  };

  private static final Interface_InternalAdapter TAG_INT_ARRAY_ADAPTER = new Interface_InternalAdapter() {
    @Override
    public void write(final Abstract_Tag tag, final DataOutputStream out) throws IOException {
      int[] data = ((TagIntArray) tag).get();
      int len = data.length;
      out.writeInt(len);
      for (int i = 0; i < len; i++) {
        out.writeInt(data[i]);
      }
    }

    @Override
    public Abstract_Tag read(final Abstract_Tag parent, final DataInputStream in) throws IOException {
      String name = (parent instanceof TagList) ? null : in.readUTF();
      int len = in.readInt();
      int[] data = new int[len];
      for (int i = 0; i < len; i++) {
        data[i] = in.readInt();
      }
      return new TagIntArray(name, data);
    }
  };

  static {
    ADAPTERS.put(TagByte.TAG_ID, TAG_BYTE_ADAPTER);
    ADAPTERS.put(TagShort.TAG_ID, TAG_SHORT_ADAPTER);
    ADAPTERS.put(TagInt.TAG_ID, TAG_INT_ADAPTER);
    ADAPTERS.put(TagLong.TAG_ID, TAG_LONG_ADAPTER);
    ADAPTERS.put(TagFloat.TAG_ID, TAG_FLOAT_ADAPTER);
    ADAPTERS.put(TagDouble.TAG_ID, TAG_DOUBLE_ADAPTER);
    ADAPTERS.put(TagByteArray.TAG_ID, TAG_BYTE_ARRAY_ADAPTER);
    ADAPTERS.put(TagString.TAG_ID, TAG_STRING_ADAPTER);
    ADAPTERS.put(TagList.TAG_ID, TAG_LIST_ADAPTER);
    ADAPTERS.put(TagCompound.TAG_ID, TAG_COMPOUND_ADAPTER);
    ADAPTERS.put(TagIntArray.TAG_ID, TAG_INT_ARRAY_ADAPTER);
  }

  @Override
  public void write(Abstract_Tag tag, OutputStream out) throws IOException {
    BASE_ADAPTER.write(tag, new DataOutputStream(out));
  }

  @Override
  public Abstract_Tag read(final Abstract_Tag parent, InputStream in) throws IOException {
    return BASE_ADAPTER.read(parent, new DataInputStream(in));
  }

}
