package com.sudoplay.axion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.adapter.TagRegistry;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagBooleanArray;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.mapper.NBTObjectMapperRegistry;
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
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.stream.CharacterEncoderFactory;
import com.sudoplay.axion.stream.StreamCompressionWrapper;
import com.sudoplay.axion.tag.Tag;

public class AxionConfiguration implements Cloneable {

  protected static final AxionConfiguration SPEC_CONFIGURATION = new AxionConfiguration() {
    {
      registerBaseTagAdapter(TagAdapter.Spec.BASE);
      registerTag(1, TagByte.class, Byte.class, TagAdapter.Spec.BYTE, TagConverter.Spec.BYTE);
      registerTag(2, TagShort.class, Short.class, TagAdapter.Spec.SHORT, TagConverter.Spec.SHORT);
      registerTag(3, TagInt.class, Integer.class, TagAdapter.Spec.INT, TagConverter.Spec.INT);
      registerTag(4, TagLong.class, Long.class, TagAdapter.Spec.LONG, TagConverter.Spec.LONG);
      registerTag(5, TagFloat.class, Float.class, TagAdapter.Spec.FLOAT, TagConverter.Spec.FLOAT);
      registerTag(6, TagDouble.class, Double.class, TagAdapter.Spec.DOUBLE, TagConverter.Spec.DOUBLE);
      registerTag(7, TagByteArray.class, byte[].class, TagAdapter.Spec.BYTE_ARRAY, TagConverter.Spec.BYTE_ARRAY);
      registerTag(8, TagString.class, String.class, TagAdapter.Spec.STRING, TagConverter.Spec.STRING);
      registerTag(9, TagList.class, List.class, TagAdapter.Spec.LIST, TagConverter.Spec.LIST);
      registerTag(10, TagCompound.class, Map.class, TagAdapter.Spec.COMPOUND, TagConverter.Spec.COMPOUND);
      registerTag(11, TagIntArray.class, int[].class, TagAdapter.Spec.INT_ARRAY, TagConverter.Spec.INT_ARRAY);

      setCharacterEncodingType(CharacterEncodingType.MODIFIED_UTF_8);
      setCompressionType(CompressionType.GZip);
      setImmutable();
    }
  };

  protected static final AxionConfiguration EXT_CONFIGURATION = new AxionConfiguration(SPEC_CONFIGURATION) {
    {
      registerTag(80, TagBoolean.class, Boolean.class, TagAdapter.Ext.BOOLEAN, TagConverter.Ext.BOOLEAN);
      registerTag(81, TagDoubleArray.class, double[].class, TagAdapter.Ext.DOUBLE_ARRAY, TagConverter.Ext.DOUBLE_ARRAY);
      registerTag(82, TagFloatArray.class, float[].class, TagAdapter.Ext.FLOAT_ARRAY, TagConverter.Ext.FLOAT_ARRAY);
      registerTag(83, TagLongArray.class, long[].class, TagAdapter.Ext.LONG_ARRAY, TagConverter.Ext.LONG_ARRAY);
      registerTag(84, TagShortArray.class, short[].class, TagAdapter.Ext.SHORT_ARRAY, TagConverter.Ext.SHORT_ARRAY);
      registerTag(85, TagStringArray.class, String[].class, TagAdapter.Ext.STRING_ARRAY, TagConverter.Ext.STRING_ARRAY);
      registerTag(86, TagBooleanArray.class, boolean[].class, TagAdapter.Ext.BOOLEAN_ARRAY, TagConverter.Ext.BOOLEAN_ARRAY);

      setCharacterEncodingType(CharacterEncodingType.MODIFIED_UTF_8);
      setCompressionType(CompressionType.GZip);
      setImmutable();
    }
  };

  public static enum CompressionType {
    GZip, Deflater, None
  }

  public static enum CharacterEncodingType {
    MODIFIED_UTF_8, US_ASCII, ISO_8859_1, UTF_8, UTF_16BE, UTF_16LE, UTF_16
  }

  protected static enum ProtectionMode {
    Unlocked, Locked, Immutable
  }

  private final TagRegistry tagRegistry;
  private final NBTObjectMapperRegistry mappers;
  private StreamCompressionWrapper streamCompressionWrapper;
  private CharacterEncodingType characterEncodingType;
  private ProtectionMode configurationProtectionMode;

  protected AxionConfiguration() {
    this(ProtectionMode.Unlocked);
  }

  protected AxionConfiguration(final AxionConfiguration toCopy) {
    tagRegistry = toCopy.tagRegistry.clone();
    mappers = toCopy.mappers.clone();
    configurationProtectionMode = ProtectionMode.Unlocked;
    streamCompressionWrapper = toCopy.streamCompressionWrapper;
    characterEncodingType = toCopy.characterEncodingType;
  }

  protected AxionConfiguration(final ProtectionMode newProtectionMode) {
    tagRegistry = new TagRegistry();
    mappers = new NBTObjectMapperRegistry();
    configurationProtectionMode = newProtectionMode;
    streamCompressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;
    characterEncodingType = CharacterEncodingType.MODIFIED_UTF_8;
  }

  /**
   * Changes this configuration's protection mode to <b>Locked</b>. Any attempt
   * to change this configuration while it is locked will result in an
   * exception. Use {@link #unlock()} to unlock.
   * <p>
   * Can't use when <b>Immutable</b>.
   * 
   * @return this {@link AxionConfiguration}
   * @see #unlock()
   * @see #setImmutable()
   */
  public AxionConfiguration lock() {
    assertMutable();
    configurationProtectionMode = ProtectionMode.Locked;
    return this;
  }

  /**
   * Changes this configuration's protection mode to <b>Unlocked</b>.
   * <p>
   * Can't use when <b>Immutable</b>.
   * 
   * @return this {@link AxionConfiguration}
   * @see #lock()
   * @see #setImmutable()
   */
  public AxionConfiguration unlock() {
    assertMutable();
    configurationProtectionMode = ProtectionMode.Unlocked;
    return this;
  }

  /**
   * Changes this configuration's protection mode to <b>Immutable</b>. Once this
   * mode is set, it can't be undone.
   * 
   * @return this {@link AxionConfiguration}
   * @see #lock()
   * @see #unlock()
   */
  public AxionConfiguration setImmutable() {
    assertUnlocked();
    assertMutable();
    configurationProtectionMode = ProtectionMode.Immutable;
    return this;
  }

  /**
   * @return true if protection mode is <b>Locked</b>
   */
  public boolean isLocked() {
    return configurationProtectionMode == ProtectionMode.Locked;
  }

  /**
   * @return true if protection mode is <b>Unlocked</b>
   */
  public boolean isUnlocked() {
    return configurationProtectionMode == ProtectionMode.Unlocked;
  }

  /**
   * @return true if protection mode is <b>Immutable</b>
   */
  public boolean isImmutable() {
    return configurationProtectionMode == ProtectionMode.Immutable;
  }

  /**
   * @throws AxionConfigurationException
   *           if protection mode is <b>Locked</b>
   */
  protected void assertUnlocked() throws AxionConfigurationException {
    if (configurationProtectionMode == ProtectionMode.Locked) {
      throw new AxionConfigurationException(Axion.class.getSimpleName() + " instance has been locked and can't be modified");
    }
  }

  /**
   * @throws AxionConfigurationException
   *           if protection mode is <b>Immutable</b>
   */
  protected void assertMutable() throws AxionConfigurationException {
    if (configurationProtectionMode == ProtectionMode.Immutable) {
      throw new AxionConfigurationException(Axion.class.getSimpleName() + " instance is immutable and can't be modified");
    }
  }

  /**
   * Sets the {@link CharacterEncodingType}.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   * 
   * @param newCharacterEncodingType
   *          the new encoding type
   * @return this {@link AxionConfiguration}
   */
  public AxionConfiguration setCharacterEncodingType(final CharacterEncodingType newCharacterEncodingType) {
    assertUnlocked();
    assertMutable();
    characterEncodingType = newCharacterEncodingType;
    return this;
  }

  /**
   * Register a {@link TagAdapter<Tag>} as the base tag adapter.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   * 
   * @param newBaseTagAdapter
   *          the new base tag adapter
   * @see #getBaseTagAdapter()
   */
  public void registerBaseTagAdapter(final TagAdapter<Tag> newBaseTagAdapter) {
    assertUnlocked();
    assertMutable();
    tagRegistry.registerBaseTagAdapter(newBaseTagAdapter);
  }

  /**
   * Registers the relationships for a tag.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   * 
   * @param id
   *          the id of the tag
   * @param tagClass
   *          the class of the tag
   * @param type
   *          the class of the type
   * @param adapter
   *          {@link TagAdapter} for the tag
   * @param converter
   *          {@link TagConverter} for the tag
   * @return this {@link AxionConfiguration}
   * @see #getAdapterFor(Class)
   * @see #getAdapterFor(int)
   * @see #getClassFor(int)
   * @see #getConverterFor(Tag)
   * @see #getConverterFor(Object)
   * @see #getIdFor(Class)
   */
  public <T extends Tag, V> AxionConfiguration registerTag(final int id, final Class<T> tagClass, final Class<V> type, final TagAdapter<T> adapter,
      final TagConverter<T, V> converter) {
    assertUnlocked();
    assertMutable();
    tagRegistry.register(id, tagClass, type, adapter, converter);
    return this;
  }

  /**
   * Registers a {@link NBTObjectMapper} for the class type given.
   * 
   * @param type
   *          the type of the object
   * @param mapper
   *          the mapper
   * @return this {@link AxionConfiguration}
   */
  public <T extends Tag, O> AxionConfiguration registerNBTObjectMapper(final Class<O> type, final NBTObjectMapper<T, O> mapper) {
    assertUnlocked();
    assertMutable();
    mappers.register(type, mapper);
    return this;
  }

  public AxionConfiguration setCompressionType(final CompressionType newCompressionType) {
    assertUnlocked();
    assertMutable();
    switch (newCompressionType) {
    case Deflater:
      streamCompressionWrapper = StreamCompressionWrapper.DEFLATER_STREAM_COMPRESSION_WRAPPER;
    case None:
      streamCompressionWrapper = StreamCompressionWrapper.PASSTHROUGH_STREAM_COMPRESSION_WRAPPER;
    default:
    case GZip:
      streamCompressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;
    }
    return this;
  }

  protected TagAdapter<Tag> getBaseTagAdapter() {
    return tagRegistry.getBaseTagAdapter();
  }

  protected int getIdFor(final Class<? extends Tag> tagClass) {
    return tagRegistry.getIdFor(tagClass);
  }

  protected Class<? extends Tag> getClassFor(final int id) {
    return tagRegistry.getClassFor(id);
  }

  protected <T extends Tag> TagAdapter<T> getAdapterFor(final int id) {
    return tagRegistry.getAdapterFor(id);
  }

  protected <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) {
    return tagRegistry.getAdapterFor(tagClass);
  }

  @SuppressWarnings("unchecked")
  protected <T extends Tag, V> TagConverter<T, V> getConverterFor(final T tag) {
    return (TagConverter<T, V>) tagRegistry.getConverterForTag(tag.getClass());
  }

  @SuppressWarnings("unchecked")
  protected <T extends Tag, V> TagConverter<T, V> getConverterFor(final V value) {
    return (TagConverter<T, V>) tagRegistry.getConverterForValue(value.getClass());
  }

  protected <T extends Tag, O> O createObjectFrom(final T tag, final Class<O> type, final Axion axion) {
    return mappers.createObjectFrom(tag, type, axion);
  }

  protected <T extends Tag, O> T createTagFrom(final String name, final O object, final Axion axion) {
    return mappers.createTagFrom(name, object, axion);
  }

  protected AxionInputStream wrap(final InputStream inputStream) throws IOException {
    return new AxionInputStream(streamCompressionWrapper.wrap(inputStream), CharacterEncoderFactory.create(characterEncodingType));
  }

  protected AxionOutputStream wrap(final OutputStream outputStream) throws IOException {
    return new AxionOutputStream(streamCompressionWrapper.wrap(outputStream), CharacterEncoderFactory.create(characterEncodingType));
  }

  @Override
  protected AxionConfiguration clone() {
    return new AxionConfiguration(this);
  }

}
