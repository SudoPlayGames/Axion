package com.sudoplay.axion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.TagAdapterRegistry;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.converter.TagConverterRegistry;
import com.sudoplay.axion.ext.adapter.TagBooleanAdapter;
import com.sudoplay.axion.ext.converter.TagBooleanConverter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.spec.adapter.TagByteAdapter;
import com.sudoplay.axion.spec.adapter.TagByteArrayAdapter;
import com.sudoplay.axion.spec.adapter.TagCompoundAdapter;
import com.sudoplay.axion.spec.adapter.TagDoubleAdapter;
import com.sudoplay.axion.spec.adapter.TagFloatAdapter;
import com.sudoplay.axion.spec.adapter.TagIntAdapter;
import com.sudoplay.axion.spec.adapter.TagIntArrayAdapter;
import com.sudoplay.axion.spec.adapter.TagListAdapter;
import com.sudoplay.axion.spec.adapter.TagLongAdapter;
import com.sudoplay.axion.spec.adapter.TagShortAdapter;
import com.sudoplay.axion.spec.adapter.TagStringAdapter;
import com.sudoplay.axion.spec.converter.TagByteArrayConverter;
import com.sudoplay.axion.spec.converter.TagByteConverter;
import com.sudoplay.axion.spec.converter.TagCompoundConverter;
import com.sudoplay.axion.spec.converter.TagDoubleConverter;
import com.sudoplay.axion.spec.converter.TagFloatConverter;
import com.sudoplay.axion.spec.converter.TagIntArrayConverter;
import com.sudoplay.axion.spec.converter.TagIntConverter;
import com.sudoplay.axion.spec.converter.TagListConverter;
import com.sudoplay.axion.spec.converter.TagLongConverter;
import com.sudoplay.axion.spec.converter.TagShortConverter;
import com.sudoplay.axion.spec.converter.TagStringConverter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagIntArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.spec.tag.TagShort;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.tag.TagRegistry;

public class Axion {

  private static final Logger LOG = LoggerFactory.getLogger(Axion.class);

  static {

    /*
     * Standard Tag Adapters
     */
    register(1, TagByte.class, new TagByteAdapter());
    register(2, TagShort.class, new TagShortAdapter());
    register(3, TagInt.class, new TagIntAdapter());
    register(4, TagLong.class, new TagLongAdapter());
    register(5, TagFloat.class, new TagFloatAdapter());
    register(6, TagDouble.class, new TagDoubleAdapter());
    register(7, TagByteArray.class, new TagByteArrayAdapter());
    register(8, TagString.class, new TagStringAdapter());
    register(9, TagList.class, new TagListAdapter());
    register(10, TagCompound.class, new TagCompoundAdapter());
    register(11, TagIntArray.class, new TagIntArrayAdapter());

    /*
     * Standard Tag Converters
     */
    registerTagConverter(TagByte.class, Byte.class, new TagByteConverter());
    registerTagConverter(TagShort.class, Short.class, new TagShortConverter());
    registerTagConverter(TagInt.class, Integer.class, new TagIntConverter());
    registerTagConverter(TagLong.class, Long.class, new TagLongConverter());
    registerTagConverter(TagFloat.class, Float.class, new TagFloatConverter());
    registerTagConverter(TagDouble.class, Double.class, new TagDoubleConverter());
    registerTagConverter(TagByteArray.class, byte[].class, new TagByteArrayConverter());
    registerTagConverter(TagString.class, String.class, new TagStringConverter());
    registerTagConverter(TagList.class, List.class, new TagListConverter());
    registerTagConverter(TagCompound.class, Map.class, new TagCompoundConverter());
    registerTagConverter(TagIntArray.class, int[].class, new TagIntArrayConverter());

    /*
     * Extended Tag Adapters
     */
    register(80, TagBoolean.class, new TagBooleanAdapter());

    /*
     * Extended Tag Converters
     */
    registerTagConverter(TagBoolean.class, Boolean.class, new TagBooleanConverter());

  }

  private Axion() {
    //
  }

  public static void register(final int id, final Class<? extends Tag> tagClass, final TagAdapter adapter) {
    TagRegistry.register(id, tagClass);
    TagAdapterRegistry.register(id, tagClass, adapter);
  }

  public static String getNameFor(final int id) {
    return getClassFor(id).getSimpleName();
  }

  public static String getNameFor(final Tag tag) {
    return tag.getClass().getSimpleName();
  }

  public static int getIdFor(final Class<? extends Tag> tagClass) {
    return TagRegistry.getIdFor(tagClass);
  }

  public static Class<? extends Tag> getClassFor(final int id) {
    return TagRegistry.getClassFor(id);
  }

  public static Tag createInstance(final int id, final String newName) {
    return TagRegistry.createInstance(id, newName);
  }

  public static Tag createInstance(final Class<? extends Tag> tagClass, final String newName) {
    return TagRegistry.createInstance(tagClass, newName);
  }

  public static TagAdapter getAdapterFor(final int id) {
    return TagAdapterRegistry.getAdapterFor(id);
  }

  public static TagAdapter getAdapterFor(final Class<? extends Tag> tagClass) {
    return TagAdapterRegistry.getAdapterFor(tagClass);
  }

  public static <T extends Tag, V> void registerTagConverter(final Class<T> tagClass, final Class<V> type, final TagConverter<T, V> converter) {
    TagConverterRegistry.registerTagConverter(tagClass, type, converter);
  }

  public static <T extends Tag, V> V convertToValue(final T tag) {
    return TagConverterRegistry.convertToValue(tag);
  }

  public static <V, T extends Tag> T convertToTag(final String name, final V value) {
    return TagConverterRegistry.convertToTag(name, value);
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
      LOG.trace("reading [{}]", getClassFor(id).getSimpleName());
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
