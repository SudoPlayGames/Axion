package com.sudoplay.axion.adapter.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.adapter.Interface_Adapter;
import com.sudoplay.axion.tag.Abstract_Tag;
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

  private static final Map<Byte, Interface_Adapter> ADAPTERS = new HashMap<Byte, Interface_Adapter>();

  private static final Interface_Adapter TAG_END_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      // write nothing
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return TagEnd.INSTANCE;
    }
  };

  private static final Interface_Adapter TAG_BYTE_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      out.writeByte(((TagByte) tag).get());
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return new TagByte(in.readUTF(), in.readByte());
    }
  };

  private static final Interface_Adapter TAG_SHORT_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      out.writeShort(((TagShort) tag).get());
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return new TagShort(in.readUTF(), in.readShort());
    }
  };

  private static final Interface_Adapter TAG_INT_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      out.writeInt(((TagInt) tag).get());
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return new TagInt(in.readUTF(), in.readInt());
    }
  };

  private static final Interface_Adapter TAG_LONG_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      out.writeLong(((TagLong) tag).get());
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return new TagLong(in.readUTF(), in.readLong());
    }
  };

  private static final Interface_Adapter TAG_FLOAT_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      out.writeFloat(((TagFloat) tag).get());
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return new TagFloat(in.readUTF(), in.readFloat());
    }
  };

  private static final Interface_Adapter TAG_DOUBLE_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      out.writeDouble(((TagDouble) tag).get());
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return new TagDouble(in.readUTF(), in.readDouble());
    }
  };

  private static final Interface_Adapter TAG_BYTE_ARRAY_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      byte[] data = ((TagByteArray) tag).get();
      out.writeInt(data.length);
      out.write(data);
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      String name = in.readUTF();
      byte[] data = new byte[in.readInt()];
      in.readFully(data);
      return new TagByteArray(name, data);
    }
  };

  private static final Interface_Adapter TAG_STRING_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      out.writeUTF(((TagString) tag).get());
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      return new TagString(in.readUTF(), in.readUTF());
    }
  };

  private static final Interface_Adapter TAG_LIST_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      TagList tagList = (TagList) tag;
      int size = tagList.size();
      if (size == 0) {
        out.writeByte(TagByte.TAG_ID);
      } else {
        out.writeByte(tagList.getType());
      }
      out.writeInt(size);
      for (int i = 0; i < size; i++) {
        byte id = tagList.get(i).getTagId();
        out.writeByte(id);
        ADAPTERS.get(id).write(tag, out);
      }
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      TagList tag = new TagList(in.readUTF());
      tag.overrideType(in.readByte());
      int len = in.readInt();
      for (int i = 0; i < len; i++) {
        tag.add(ADAPTERS.get(in.readByte()).read(in));
      }
      return tag;
    }
  };

  private static final Interface_Adapter TAG_COMPOUND_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      for (Abstract_Tag child : ((TagCompound) tag).getAsMap().values()) {
        byte id = child.getTagId();
        out.writeByte(id);
        ADAPTERS.get(id).write(child, out);
      }
      out.writeByte(TagEnd.TAG_ID);
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      TagCompound tag = new TagCompound(in.readUTF());
      Abstract_Tag child;
      while ((child = ADAPTERS.get(in.readByte()).read(in)).getTagId() != TagEnd.TAG_ID) {
        tag.getAsMap().put(child.getName(), child);
      }
      return tag;
    }
  };

  private static final Interface_Adapter TAG_INT_ARRAY_ADAPTER = new Interface_Adapter() {
    @Override
    public void write(Abstract_Tag tag, DataOutput out) throws IOException {
      out.writeUTF(tag.getName());
      int[] data = ((TagIntArray) tag).get();
      int len = data.length;
      for (int i = 0; i < len; i++) {
        out.writeInt(data[i]);
      }
    }

    @Override
    public Abstract_Tag read(DataInput in) throws IOException {
      String name = in.readUTF();
      int len = in.readInt();
      int[] data = new int[len];
      for (int i = 0; i < len; i++) {
        data[i] = in.readInt();
      }
      return new TagIntArray(name, data);
    }
  };

  static {
    ADAPTERS.put(TagEnd.TAG_ID, TAG_END_ADAPTER);
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
  public void write(Abstract_Tag tag, DataOutput out) throws IOException {
    byte id = tag.getTagId();
    out.writeByte(id);
    ADAPTERS.get(id).write(tag, out);
  }

  @Override
  public Abstract_Tag read(DataInput in) throws IOException {
    return ADAPTERS.get(in.readByte()).read(in);
  }

}
