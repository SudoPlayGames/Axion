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

import com.sudoplay.axion.AxionConfiguration.ProtectionMode;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.ext.adapter.TagBooleanAdapter;
import com.sudoplay.axion.ext.adapter.TagDoubleArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagFloatArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagLongArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagShortArrayAdapter;
import com.sudoplay.axion.ext.adapter.TagStringArrayAdapter;
import com.sudoplay.axion.ext.converter.TagBooleanConverter;
import com.sudoplay.axion.ext.converter.TagDoubleArrayConverter;
import com.sudoplay.axion.ext.converter.TagFloatArrayConverter;
import com.sudoplay.axion.ext.converter.TagLongArrayConverter;
import com.sudoplay.axion.ext.converter.TagShortArrayConverter;
import com.sudoplay.axion.ext.converter.TagStringArrayConverter;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
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

public class Axion {

  private static final Logger LOG = LoggerFactory.getLogger(Axion.class);

  private static final String DEFAULT_INSTANCE_NAME = "AXION_DEFAULT";
  private static final String ORIGINAL_SPEC_INSTANCE_NAME = "AXION_ORIGINAL_SPEC";

  private static final Axion DEFAULT_INSTANCE = new Axion() {
    {
      getConfiguration().registerTagAdapter(1, TagByte.class, new TagByteAdapter());
      getConfiguration().registerTagAdapter(2, TagShort.class, new TagShortAdapter());
      getConfiguration().registerTagAdapter(3, TagInt.class, new TagIntAdapter());
      getConfiguration().registerTagAdapter(4, TagLong.class, new TagLongAdapter());
      getConfiguration().registerTagAdapter(5, TagFloat.class, new TagFloatAdapter());
      getConfiguration().registerTagAdapter(6, TagDouble.class, new TagDoubleAdapter());
      getConfiguration().registerTagAdapter(7, TagByteArray.class, new TagByteArrayAdapter());
      getConfiguration().registerTagAdapter(8, TagString.class, new TagStringAdapter());
      getConfiguration().registerTagAdapter(9, TagList.class, new TagListAdapter());
      getConfiguration().registerTagAdapter(10, TagCompound.class, new TagCompoundAdapter());
      getConfiguration().registerTagAdapter(11, TagIntArray.class, new TagIntArrayAdapter());

      getConfiguration().registerTagAdapter(80, TagBoolean.class, new TagBooleanAdapter());
      getConfiguration().registerTagAdapter(81, TagDoubleArray.class, new TagDoubleArrayAdapter());
      getConfiguration().registerTagAdapter(82, TagFloatArray.class, new TagFloatArrayAdapter());
      getConfiguration().registerTagAdapter(83, TagLongArray.class, new TagLongArrayAdapter());
      getConfiguration().registerTagAdapter(84, TagShortArray.class, new TagShortArrayAdapter());
      getConfiguration().registerTagAdapter(85, TagStringArray.class, new TagStringArrayAdapter());

      getConfiguration().registerTagConverter(TagByte.class, Byte.class, new TagByteConverter());
      getConfiguration().registerTagConverter(TagShort.class, Short.class, new TagShortConverter());
      getConfiguration().registerTagConverter(TagInt.class, Integer.class, new TagIntConverter());
      getConfiguration().registerTagConverter(TagLong.class, Long.class, new TagLongConverter());
      getConfiguration().registerTagConverter(TagFloat.class, Float.class, new TagFloatConverter());
      getConfiguration().registerTagConverter(TagDouble.class, Double.class, new TagDoubleConverter());
      getConfiguration().registerTagConverter(TagByteArray.class, byte[].class, new TagByteArrayConverter());
      getConfiguration().registerTagConverter(TagString.class, String.class, new TagStringConverter());
      getConfiguration().registerTagConverter(TagList.class, List.class, new TagListConverter());
      getConfiguration().registerTagConverter(TagCompound.class, Map.class, new TagCompoundConverter());
      getConfiguration().registerTagConverter(TagIntArray.class, int[].class, new TagIntArrayConverter());

      getConfiguration().registerTagConverter(TagBoolean.class, Boolean.class, new TagBooleanConverter());
      getConfiguration().registerTagConverter(TagDoubleArray.class, double[].class, new TagDoubleArrayConverter());
      getConfiguration().registerTagConverter(TagFloatArray.class, float[].class, new TagFloatArrayConverter());
      getConfiguration().registerTagConverter(TagLongArray.class, long[].class, new TagLongArrayConverter());
      getConfiguration().registerTagConverter(TagShortArray.class, short[].class, new TagShortArrayConverter());
      getConfiguration().registerTagConverter(TagStringArray.class, String[].class, new TagStringArrayConverter());

      getConfiguration().setConfigurationImmutable();
    }
  };

  private static final Axion ORIGINAL_SPEC_INSTANCE = new Axion() {
    {
      getConfiguration().registerTagAdapter(1, TagByte.class, new TagByteAdapter());
      getConfiguration().registerTagAdapter(2, TagShort.class, new TagShortAdapter());
      getConfiguration().registerTagAdapter(3, TagInt.class, new TagIntAdapter());
      getConfiguration().registerTagAdapter(4, TagLong.class, new TagLongAdapter());
      getConfiguration().registerTagAdapter(5, TagFloat.class, new TagFloatAdapter());
      getConfiguration().registerTagAdapter(6, TagDouble.class, new TagDoubleAdapter());
      getConfiguration().registerTagAdapter(7, TagByteArray.class, new TagByteArrayAdapter());
      getConfiguration().registerTagAdapter(8, TagString.class, new TagStringAdapter());
      getConfiguration().registerTagAdapter(9, TagList.class, new TagListAdapter());
      getConfiguration().registerTagAdapter(10, TagCompound.class, new TagCompoundAdapter());
      getConfiguration().registerTagAdapter(11, TagIntArray.class, new TagIntArrayAdapter());

      getConfiguration().registerTagConverter(TagByte.class, Byte.class, new TagByteConverter());
      getConfiguration().registerTagConverter(TagShort.class, Short.class, new TagShortConverter());
      getConfiguration().registerTagConverter(TagInt.class, Integer.class, new TagIntConverter());
      getConfiguration().registerTagConverter(TagLong.class, Long.class, new TagLongConverter());
      getConfiguration().registerTagConverter(TagFloat.class, Float.class, new TagFloatConverter());
      getConfiguration().registerTagConverter(TagDouble.class, Double.class, new TagDoubleConverter());
      getConfiguration().registerTagConverter(TagByteArray.class, byte[].class, new TagByteArrayConverter());
      getConfiguration().registerTagConverter(TagString.class, String.class, new TagStringConverter());
      getConfiguration().registerTagConverter(TagList.class, List.class, new TagListConverter());
      getConfiguration().registerTagConverter(TagCompound.class, Map.class, new TagCompoundConverter());
      getConfiguration().registerTagConverter(TagIntArray.class, int[].class, new TagIntArrayConverter());

      getConfiguration().setConfigurationImmutable();
    }
  };

  @SuppressWarnings("serial")
  private static final Map<String, Axion> INSTANCES = new HashMap<String, Axion>() {
    {
      put(DEFAULT_INSTANCE_NAME, DEFAULT_INSTANCE);
      put(ORIGINAL_SPEC_INSTANCE_NAME, ORIGINAL_SPEC_INSTANCE);
    }
  };

  private AxionConfiguration configuration;

  private Axion() {
    this(new AxionConfiguration(ProtectionMode.Unlocked));
  }

  private Axion(final AxionConfiguration newConfiguration) {
    configuration = newConfiguration;
  }

  public static Axion create(final String newName, final AxionConfiguration newConfiguration) {
    if (INSTANCES.containsKey(newName)) {
      throw new IllegalArgumentException(Axion.class.getSimpleName() + " instance alread exists with name: " + newName);
    }
    Axion instance = new Axion(newConfiguration);
    INSTANCES.put(newName, instance);
    return instance;
  }

  public static Axion get(final String name) {
    return INSTANCES.get(name);
  }

  public static Axion getDefault() {
    return INSTANCES.get(DEFAULT_INSTANCE_NAME);
  }

  public static Axion getOriginal() {
    return INSTANCES.get(ORIGINAL_SPEC_INSTANCE_NAME);
  }

  public AxionConfiguration getConfiguration() {
    return configuration;
  }

  public String getNameFor(final Tag tag) {
    return tag.getClass().getSimpleName();
  }

  public int getIdFor(final Class<? extends Tag> tagClass) {
    return configuration.getIdFor(tagClass);
  }

  public Class<? extends Tag> getClassFor(final int id) {
    return configuration.getClassFor(id);
  }

  public <T extends Tag> T createInstance(final int id, final String newName) {
    return configuration.createInstance(id, newName);
  }

  public <T extends Tag> T createInstance(final Class<T> tagClass, final String newName) {
    return configuration.createInstance(tagClass, newName);
  }

  public <T extends Tag> TagAdapter<T> getAdapterFor(final int id) {
    return configuration.getAdapterFor(id);
  }

  public <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) {
    return configuration.getAdapterFor(tagClass);
  }

  public <T extends Tag, V> V convertToValue(final T tag) {
    return configuration.convertToValue(tag, this);
  }

  public <V, T extends Tag> T convertToTag(final String name, final V value) {
    return configuration.convertToTag(name, value, this);
  }

  public String readString(final DataInputStream dataInputStream) throws IOException {
    return configuration.readString(dataInputStream);
  }

  public void writeString(final DataOutputStream dataOutputStream, final String data) throws IOException {
    configuration.writeString(dataOutputStream, data);
  }

  public TagCompound read(final InputStream inputStream) throws IOException {
    Tag result = readTag(null, new DataInputStream(configuration.wrap(inputStream)));
    if (!(result instanceof TagCompound)) {
      throw new IllegalStateException("Root tag not of type " + TagCompound.class.getSimpleName());
    }
    return (TagCompound) result;
  }

  public void write(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    writeTag(tagCompound, new DataOutputStream(configuration.wrap(outputStream)));
  }

  public Tag readTag(final Tag parent, final DataInputStream in) throws IOException {
    int id = in.readUnsignedByte();
    if (id == 0) {
      return null;
    } else {
      LOG.trace("reading [{}]", configuration.getClassFor(id).getSimpleName());
      Tag tag = configuration.getAdapterFor(id).read(parent, in, this);
      LOG.trace("finished reading [{}]", tag);
      return tag;
    }
  }

  public void writeTag(final Tag tag, final DataOutputStream out) throws IOException {
    LOG.trace("writing [{}]", tag);
    int id = configuration.getIdFor(tag.getClass());
    out.writeByte(id);
    if (id != 0) {
      if (!(tag.getParent() instanceof TagList)) {
        configuration.writeString(out, tag.getName());
      }
      configuration.getAdapterFor(id).write(tag, out, this);
    }
    LOG.trace("finished writing [{}]", tag);
  }

}
