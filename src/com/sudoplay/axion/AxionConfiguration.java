package com.sudoplay.axion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.AxionConfigurationProtection.ProtectionMode;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagBooleanArray;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.mapper.AxionMapperRegistrationException;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.mapper.NBTObjectMapperRegistry;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.registry.TagRegistry;
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

/**
 * The {@link AxionConfiguration} class manages all the user-definable
 * configuration state for an {@link Axion} instance.
 * 
 * @author Jason Taylor
 */
public class AxionConfiguration implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(AxionConfiguration.class);

  /**
   * An {@link AxionConfiguration} instance configured to read and write NBT
   * using the original specifications; supports the late added TAG_Int_Array.
   */
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

  /**
   * An {@link AxionConfiguration} instance that extends the original NBT
   * specifications to include the boolean class and common array classes.
   */
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

  /**
   * The compression type that an {@link AxionConfiguration} will use to read
   * and write when accessing the underlying streams.
   */
  public static enum CompressionType {
    GZip, Deflater, None
  }

  /**
   * The character encoding type that an {@link AxionConfiguration} will use to
   * read and write strings with the {@link AxionInputStream#readString()} and
   * {@link AxionOutputStream#writeString(String)} methods.
   * 
   * @see AxionConfiguration#setCharacterEncodingType(CharacterEncodingType)
   */
  public static enum CharacterEncodingType {
    MODIFIED_UTF_8, US_ASCII, ISO_8859_1, UTF_8, UTF_16BE, UTF_16LE, UTF_16
  }

  private final TagRegistry tagRegistry;
  private final NBTObjectMapperRegistry mappers;
  private StreamCompressionWrapper streamCompressionWrapper;
  private CharacterEncodingType characterEncodingType;
  private AxionConfigurationProtection configurationProtection;

  /**
   * Creates a new {@link AxionConfiguration} instance with
   * {@link ProtectionMode#Unlocked}.
   */
  protected AxionConfiguration() {
    this(ProtectionMode.Unlocked);
  }

  /**
   * Creates a new {@link AxionConfiguration} instance by duplicating all
   * registered {@link TagAdapter}s, {@link TagConverter}s and
   * {@link NBTObjectMapper}s. The {@link StreamCompressionWrapper} is not
   * duplicated, merely referenced.
   * <p>
   * The new instance created will be {@link ProtectionMode#Unlocked}.
   * 
   * @param toCopy
   *          the {@link AxionConfiguration} to duplicate
   */
  protected AxionConfiguration(final AxionConfiguration toCopy) {
    LOG.debug("Entering AxionConfiguration(toCopy=[{}])", toCopy);
    tagRegistry = toCopy.tagRegistry.clone();
    mappers = toCopy.mappers.clone();
    configurationProtection = new AxionConfigurationProtection(ProtectionMode.Unlocked);
    streamCompressionWrapper = toCopy.streamCompressionWrapper;
    characterEncodingType = toCopy.characterEncodingType;
    LOG.debug("Leaving AxionConfiguration(): [{}]", this);
  }

  /**
   * Creates a new {@link AxionConfiguration} instance with the given
   * {@link ProtectionMode}.
   * 
   * @param newProtectionMode
   */
  protected AxionConfiguration(final ProtectionMode newProtectionMode) {
    LOG.debug("Entering AxionConfiguration(newProtectionMode=[{}])", newProtectionMode);
    tagRegistry = new TagRegistry();
    mappers = new NBTObjectMapperRegistry();
    configurationProtection = new AxionConfigurationProtection(newProtectionMode);
    streamCompressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;
    characterEncodingType = CharacterEncodingType.MODIFIED_UTF_8;
    LOG.debug("Leaving AxionConfiguration(): [{}]", this);
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
  protected AxionConfiguration lock() {
    LOG.debug("[{}] lock()", this);
    configurationProtection.assertMutable();
    configurationProtection.lock();
    LOG.debug("Configuration [{}] locked", this);
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
  protected AxionConfiguration unlock() {
    LOG.debug("[{}] unlock()", this);
    configurationProtection.assertMutable();
    configurationProtection.unlock();
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
  protected AxionConfiguration setImmutable() {
    LOG.debug("[{}] setImmutable()", this);
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    configurationProtection.setImmutable();
    return this;
  }

  /**
   * @return true if protection mode is <b>Locked</b>
   */
  protected boolean isLocked() {
    return configurationProtection.isLocked();
  }

  /**
   * @return true if protection mode is <b>Unlocked</b>
   */
  protected boolean isUnlocked() {
    return configurationProtection.isUnlocked();
  }

  /**
   * @return true if protection mode is <b>Immutable</b>
   */
  protected boolean isImmutable() {
    return configurationProtection.isImmutable();
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
  protected AxionConfiguration setCharacterEncodingType(final CharacterEncodingType newCharacterEncodingType) {
    LOG.debug("[{}] setCharacterEncodingType(newCharacterEncodingType=[{}])", this, newCharacterEncodingType);
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    characterEncodingType = newCharacterEncodingType;
    return this;
  }

  /**
   * Register a {@link TagAdapter} as the base tag adapter.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   * 
   * @param newBaseTagAdapter
   *          the new base tag adapter
   * @throws AxionConfigurationException
   * @throws AxionInstanceException
   * @see #getBaseTagAdapter()
   */
  protected void registerBaseTagAdapter(final TagAdapter<Tag> newBaseTagAdapter) throws AxionConfigurationException, AxionInstanceException {
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
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
   * @throws AxionTagRegistrationException
   * @throws AxionInstanceException
   * @see #getAdapterFor(Class)
   * @see #getAdapterFor(int)
   * @see #getClassFor(int)
   * @see #getConverterFor(Tag)
   * @see #getConverterFor(Object)
   * @see #getIdFor(Class)
   */
  protected <T extends Tag, V> AxionConfiguration registerTag(final int id, final Class<T> tagClass, final Class<V> type, final TagAdapter<T> adapter,
      final TagConverter<T, V> converter) throws AxionTagRegistrationException, AxionInstanceException {
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    tagRegistry.register(id, tagClass, type, adapter, converter);
    return this;
  }

  /**
   * Registers a {@link NBTObjectMapper} for the class type given.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   * 
   * @param type
   *          the type of the object
   * @param mapper
   *          the mapper
   * @return this {@link AxionConfiguration}
   */
  protected <T extends Tag, O> AxionConfiguration registerNBTObjectMapper(final Class<O> type, final NBTObjectMapper<T, O> mapper) {
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    mappers.register(type, mapper);
    return this;
  }

  /**
   * Sets this {@link AxionConfiguration} to use the {@link CompressionType}
   * given.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   * 
   * @param newCompressionType
   *          the compression type to use
   * @return this {@link AxionConfiguration}
   */
  protected AxionConfiguration setCompressionType(final CompressionType newCompressionType) {
    LOG.debug("setCompressionType(newCompressionType=[{}])", newCompressionType);
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    switch (newCompressionType) {
    case Deflater:
      streamCompressionWrapper = StreamCompressionWrapper.DEFLATER_STREAM_COMPRESSION_WRAPPER;
      break;
    case None:
      streamCompressionWrapper = StreamCompressionWrapper.PASSTHROUGH_STREAM_COMPRESSION_WRAPPER;
      break;
    default:
    case GZip:
      streamCompressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;
      break;
    }
    return this;
  }

  /**
   * Returns the {@link TagAdapter} registered as the base tag adapter.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   * 
   * @return the base {@link TagAdapter}
   */
  protected TagAdapter<Tag> getBaseTagAdapter() throws AxionTagRegistrationException {
    return tagRegistry.getBaseTagAdapter();
  }

  /**
   * Returns the int id for the {@link Tag} class given.
   * 
   * @param tagClass
   *          class to get the id for
   * @return the int id for the {@link Tag} class given
   */
  protected int getIdFor(final Class<? extends Tag> tagClass) {
    return tagRegistry.getIdFor(tagClass);
  }

  /**
   * Returns the {@link Tag} class for the int id given.
   * <p>
   * If no class is found, an exception is thrown.
   * 
   * @param id
   *          the id to get the class for
   * @return the {@link Tag} class for the int id given
   * @throws AxionTagRegistrationException
   */
  protected Class<? extends Tag> getClassFor(final int id) throws AxionTagRegistrationException {
    return tagRegistry.getClassFor(id);
  }

  /**
   * Returns the {@link TagAdapter} for the int id given.
   * <p>
   * If no adapter is found, an exception is thrown.
   * 
   * @param id
   *          the id to get the {@link TagAdapter} for
   * @return the {@link TagAdapter} for the int id given
   * @throws AxionTagRegistrationException
   */
  protected <T extends Tag> TagAdapter<T> getAdapterFor(final int id) throws AxionTagRegistrationException {
    return tagRegistry.getAdapterFor(id);
  }

  /**
   * Returns the {@link TagAdapter} for the {@link Tag} class given.
   * <p>
   * If no adapter is found, an exception is thrown.
   * 
   * @param tagClass
   *          {@link Tag} class to get the {@link TagAdapter} for
   * @return the {@link TagAdapter} for the {@link Tag} class given
   * @throws AxionTagRegistrationException
   */
  protected <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) throws AxionTagRegistrationException {
    return tagRegistry.getAdapterFor(tagClass);
  }

  /**
   * Returns the {@link TagConverter} for the {@link Tag} given.
   * <p>
   * If no converter is found, an exception is thrown.
   * 
   * @param tag
   *          {@link Tag} to get the {@link TagConverter} for
   * @return the {@link TagConverter} for the {@link Tag} given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  protected <T extends Tag, V> TagConverter<T, V> getConverterFor(final T tag) throws AxionTagRegistrationException {
    return (TagConverter<T, V>) tagRegistry.getConverterForTag(tag.getClass());
  }

  /**
   * Returns the {@link TagConverter} for the value given.
   * <p>
   * If no converter is found, an exception is thrown.
   * 
   * @param value
   *          value to get the {@link TagConverter} for
   * @return the {@link TagConverter} for the value given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  protected <T extends Tag, V> TagConverter<T, V> getConverterFor(final V value) throws AxionTagRegistrationException {
    return (TagConverter<T, V>) tagRegistry.getConverterForValue(value.getClass());
  }

  /**
   * Returns the {@link NBTObjectMapper} registered for the class type provided.
   * 
   * @param type
   *          class type to get the {@link NBTObjectMapper} for
   * @return the {@link NBTObjectMapper} registered for the class type provided
   * @throws AxionMapperRegistrationException
   */
  protected <T extends Tag, O> NBTObjectMapper<T, O> getMapperFor(final Class<O> type) throws AxionMapperRegistrationException {
    return mappers.getMapperFor(type);
  }

  /**
   * Returns true if the given class has a mapper registered.
   *
   * @param type class
   * @return true if the given class has a mapper registered
   */
  protected boolean hasMapperFor(final Class<?> type) {
    return mappers.hasMapperFor(type);
  }

  /**
   * Wraps an {@link InputStream} using the {@link StreamCompressionWrapper} set
   * with {@link #setCompressionType(CompressionType)}.
   * 
   * @param inputStream
   *          the {@link InputStream} to wrap
   * @return a new {@link AxionInputStream}
   * @throws IOException
   * @see #setCompressionType(CompressionType)
   */
  protected AxionInputStream wrap(final InputStream inputStream) throws IOException {
    return new AxionInputStream(streamCompressionWrapper.wrap(inputStream), CharacterEncoderFactory.create(characterEncodingType));
  }

  /**
   * Wraps an {@link OutputStream} using the {@link StreamCompressionWrapper}
   * set with {@link #setCompressionType(CompressionType)}.
   * 
   * @param outputStream
   *          the {@link OutputStream} to wrap
   * @return a new {@link AxionInputStream}
   * @throws IOException
   * @see #setCompressionType(CompressionType)
   */
  protected AxionOutputStream wrap(final OutputStream outputStream) throws IOException {
    return new AxionOutputStream(streamCompressionWrapper.wrap(outputStream), CharacterEncoderFactory.create(characterEncodingType));
  }

  /**
   * Creates a duplicate instance of this {@link AxionConfiguration}.
   * 
   * @see AxionConfiguration#AxionConfiguration(AxionConfiguration)
   */
  @Override
  protected AxionConfiguration clone() {
    return new AxionConfiguration(this);
  }

}
