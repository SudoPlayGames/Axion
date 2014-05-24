package com.sudoplay.axion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.adapter.Adapter;
import com.sudoplay.axion.adapter.DefaultAdapters;
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
import com.sudoplay.axion.util.TagUtil;

public class Axion {

  private static final Logger LOG = LoggerFactory.getLogger(Axion.class);

  private static final Map<Class<? extends Tag>, Integer> classToId = new HashMap<Class<? extends Tag>, Integer>();
  private static final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<Integer, Class<? extends Tag>>();
  private static final Map<Class<? extends Tag>, Adapter> classToAdapter = new HashMap<Class<? extends Tag>, Adapter>();
  private static final Map<Integer, Adapter> idToAdapter = new HashMap<Integer, Adapter>();

  static {
    register(1, TagByte.class, DefaultAdapters.TAG_BYTE_ADAPTER);
    register(2, TagShort.class, DefaultAdapters.TAG_SHORT_ADAPTER);
    register(3, TagInt.class, DefaultAdapters.TAG_INT_ADAPTER);
    register(4, TagLong.class, DefaultAdapters.TAG_LONG_ADAPTER);
    register(5, TagFloat.class, DefaultAdapters.TAG_FLOAT_ADAPTER);
    register(6, TagDouble.class, DefaultAdapters.TAG_DOUBLE_ADAPTER);
    register(7, TagByteArray.class, DefaultAdapters.TAG_BYTE_ARRAY_ADAPTER);
    register(8, TagString.class, DefaultAdapters.TAG_STRING_ADAPTER);
    register(9, TagList.class, DefaultAdapters.TAG_LIST_ADAPTER);
    register(10, TagCompound.class, DefaultAdapters.TAG_COMPOUND_ADAPTER);
    register(11, TagIntArray.class, DefaultAdapters.TAG_INT_ARRAY_ADAPTER);
  }

  private Axion() {
    //
  }

  public static void register(final int id, final Class<? extends Tag> tagClass, final Adapter adapter) {
    if (classToId.containsKey(tagClass) || classToAdapter.containsKey(tagClass)) {
      throw new IllegalArgumentException("Tag class already registered: " + tagClass.getSimpleName());
    } else if (idToClass.containsKey(id) || idToAdapter.containsKey(id)) {
      throw new IllegalArgumentException("Tag id already registered: " + id);
    }
    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);
    classToAdapter.put(tagClass, adapter);
    idToAdapter.put(id, adapter);
  }

  public static int getIdFor(final Class<? extends Tag> tagClass) {
    Integer result;
    if ((result = classToId.get(tagClass)) == null) {
      throw new IllegalArgumentException("No id registered for tag class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public static Class<? extends Tag> getClassFor(final int id) {
    Class<? extends Tag> result;
    if ((result = idToClass.get(id)) == null) {
      throw new IllegalArgumentException("No class registered for tag id: " + id);
    }
    return result;
  }

  public static Adapter getAdapterFor(final int id) {
    Adapter result;
    if ((result = idToAdapter.get(id)) == null) {
      throw new IllegalArgumentException("No adapter registered for id: " + id);
    }
    return result;
  }

  public static Adapter getAdapterFor(final Class<? extends Tag> tagClass) {
    Adapter result;
    if ((result = classToAdapter.get(tagClass)) == null) {
      throw new IllegalArgumentException("No adapter registered for class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public static Tag readGZip(final InputStream inputStream) throws IOException {
    return read(new GZIPInputStream(inputStream));
  }

  public void writeGZip(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    write(tagCompound, new GZIPOutputStream(outputStream));
  }

  public static Tag readDeflater(final InputStream inputStream) throws IOException {
    return read(new InflaterInputStream(inputStream));
  }

  public void writeDeflater(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    write(tagCompound, new DeflaterOutputStream(outputStream));
  }

  public static Tag read(final InputStream in) throws IOException {
    return readTag(null, new DataInputStream(in));
  }

  public static void write(final Tag tag, final OutputStream out) throws IOException {
    writeTag(tag, new DataOutputStream(out));
  }

  public static Tag readTag(final Tag parent, final DataInputStream in) throws IOException {
    int id = in.readUnsignedByte();
    if (id == 0) {
      return null;
    } else {
      LOG.trace("reading [{}]", TagUtil.getName(id));
      Tag tag = getAdapterFor(id).read(parent, in);
      LOG.trace("finished reading [{}]", tag);
      return tag;
    }
  }

  public static void writeTag(final Tag tag, final DataOutputStream out) throws IOException {
    LOG.trace("writing [{}]", tag);
    int id = getIdFor(tag.getClass());
    out.writeByte(id);
    if (id != 0) {
      if (!(tag.getParent() instanceof TagList)) {
        out.writeUTF(tag.getName());
      }
      getAdapterFor(id).write(tag, out);
    }
    LOG.trace("finished writing [{}]", tag);
  }

}
