package com.sudoplay.axion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sudoplay.axion.stream.StreamCompressionWrapper;

public class Axion {

  private static final Logger LOG = LoggerFactory.getLogger(Axion.class);

  private static final String DEFAULT_INSTANCE_NAME = "AXION_DEFAULT";

  private static final Axion DEFAULT_INSTANCE = new Axion() {
    {
      registerTagAdapter(1, TagByte.class, new TagByteAdapter());
      registerTagAdapter(2, TagShort.class, new TagShortAdapter());
      registerTagAdapter(3, TagInt.class, new TagIntAdapter());
      registerTagAdapter(4, TagLong.class, new TagLongAdapter());
      registerTagAdapter(5, TagFloat.class, new TagFloatAdapter());
      registerTagAdapter(6, TagDouble.class, new TagDoubleAdapter());
      registerTagAdapter(7, TagByteArray.class, new TagByteArrayAdapter());
      registerTagAdapter(8, TagString.class, new TagStringAdapter());
      registerTagAdapter(9, TagList.class, new TagListAdapter());
      registerTagAdapter(10, TagCompound.class, new TagCompoundAdapter());
      registerTagAdapter(11, TagIntArray.class, new TagIntArrayAdapter());

      registerTagAdapter(80, TagBoolean.class, new TagBooleanAdapter());

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

      registerTagConverter(TagBoolean.class, Boolean.class, new TagBooleanConverter());
    }
  };

  @SuppressWarnings("serial")
  private static final Map<String, Axion> INSTANCES = new HashMap<String, Axion>() {
    {
      put(DEFAULT_INSTANCE_NAME, DEFAULT_INSTANCE);
    }
  };

  public static enum CompressionType {
    GZip, Deflater, None
  }

  private final TagAdapterRegistry adapters = new TagAdapterRegistry();
  private final TagConverterRegistry converters = new TagConverterRegistry();
  private StreamCompressionWrapper compressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;

  private Axion() {
    //
  }

  public static Axion create(final String newName) {
    if (INSTANCES.containsKey(newName)) {
      throw new IllegalArgumentException("Axion instance alread exists with name: " + newName);
    }
    Axion instance = new Axion();
    INSTANCES.put(newName, instance);
    return instance;
  }

  public static Axion get(final String name) {
    return INSTANCES.get(name);
  }

  public static Axion getDefault() {
    return INSTANCES.get(DEFAULT_INSTANCE_NAME);
  }

  public static String getNameFor(final Tag tag) {
    return tag.getClass().getSimpleName();
  }

  public <T extends Tag, V> void registerTagConverter(final Class<T> tagClass, final Class<V> type, final TagConverter<T, V> converter) {
    converters.register(tagClass, type, converter);
  }

  public void registerTagAdapter(final int id, final Class<? extends Tag> tagClass, final TagAdapter adapter) {
    adapters.register(id, tagClass, adapter);
  }

  public void setCompressionType(final CompressionType newCompressionType) {
    switch (newCompressionType) {
    case Deflater:
      compressionWrapper = StreamCompressionWrapper.DEFLATER_STREAM_COMPRESSION_WRAPPER;
    case None:
      compressionWrapper = StreamCompressionWrapper.PASSTHROUGH_STREAM_COMPRESSION_WRAPPER;
    default:
    case GZip:
      compressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;
    }
  }

  public String getNameFor(final int id) {
    return getClassFor(id).getSimpleName();
  }

  public int getIdFor(final Class<? extends Tag> tagClass) {
    return adapters.getIdFor(tagClass);
  }

  public Class<? extends Tag> getClassFor(final int id) {
    return adapters.getClassFor(id);
  }

  public Tag createInstance(final int id, final String newName) {
    return adapters.createInstance(id, newName);
  }

  public Tag createInstance(final Class<? extends Tag> tagClass, final String newName) {
    return adapters.createInstance(tagClass, newName);
  }

  public TagAdapter getAdapterFor(final int id) {
    return adapters.getAdapterFor(id);
  }

  public TagAdapter getAdapterFor(final Class<? extends Tag> tagClass) {
    return adapters.getAdapterFor(tagClass);
  }

  public <T extends Tag, V> V convertToValue(final T tag) {
    return converters.convertToValue(tag, this);
  }

  public <V, T extends Tag> T convertToTag(final String name, final V value) {
    return converters.convertToTag(name, value, this);
  }

  public TagCompound read(final InputStream inputStream) throws IOException {
    Tag result = readTag(null, new DataInputStream(compressionWrapper.wrap(inputStream)));
    if (!(result instanceof TagCompound)) {
      throw new IllegalStateException("Root tag not of type " + TagCompound.class.getSimpleName());
    }
    return (TagCompound) result;
  }

  public void write(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    writeTag(tagCompound, new DataOutputStream(compressionWrapper.wrap(outputStream)));
  }

  public Tag readTag(final Tag parent, final DataInputStream in) throws IOException {
    int id = in.readUnsignedByte();
    if (id == 0) {
      return null;
    } else {
      LOG.trace("reading [{}]", getClassFor(id).getSimpleName());
      Tag tag = getAdapterFor(id).read(parent, in, this);
      LOG.trace("finished reading [{}]", tag);
      return tag;
    }
  }

  public void writeTag(final Tag tag, final DataOutputStream out) throws IOException {
    LOG.trace("writing [{}]", tag);
    int id = getIdFor(tag.getClass());
    out.writeByte(id);
    if (id != 0) {
      if (!(tag.getParent() instanceof TagList)) {
        out.writeUTF(tag.getName());
      }
      getAdapterFor(id).write(tag, out, this);
    }
    LOG.trace("finished writing [{}]", tag);
  }

}
