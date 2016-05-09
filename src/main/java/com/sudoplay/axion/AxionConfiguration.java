package com.sudoplay.axion;

import com.sudoplay.axion.AxionConfigurationProtection.ProtectionMode;
import com.sudoplay.axion.converter.*;
import com.sudoplay.axion.ext.tag.*;
import com.sudoplay.axion.registry.*;
import com.sudoplay.axion.spec.tag.*;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.stream.CharacterEncoderFactory;
import com.sudoplay.axion.stream.StreamCompressionWrapper;
import com.sudoplay.axion.system.ConstructorConstructor;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * The {@link AxionConfiguration} class manages all the user-definable configuration state for an {@link Axion}
 * instance.
 *
 * @author Jason Taylor
 */
public class AxionConfiguration implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(AxionConfiguration.class);

  public static AxionConfiguration getSpecConfiguration(Axion axion) {
    return new AxionConfiguration() {{
      registerFactory(axion, new AxionWritableTypeConverterFactory());
      registerFactory(axion, new CollectionTypeConverterFactory());
      registerFactory(axion, new MapTypeConverterFactory());
      registerFactory(axion, new EnumTypeConverterFactory());

      registerBaseTagAdapter(axion, TagAdapter.Spec.BASE);
      registerTag(axion, 1, TagByte.class, Byte.class, TagAdapter.Spec.BYTE, TypeConverter.Spec.BYTE);
      registerTag(axion, 2, TagShort.class, Short.class, TagAdapter.Spec.SHORT, TypeConverter.Spec.SHORT);
      registerTag(axion, 3, TagInt.class, Integer.class, TagAdapter.Spec.INT, TypeConverter.Spec.INT);
      registerTag(axion, 4, TagLong.class, Long.class, TagAdapter.Spec.LONG, TypeConverter.Spec.LONG);
      registerTag(axion, 5, TagFloat.class, Float.class, TagAdapter.Spec.FLOAT, TypeConverter.Spec.FLOAT);
      registerTag(axion, 6, TagDouble.class, Double.class, TagAdapter.Spec.DOUBLE, TypeConverter.Spec.DOUBLE);
      registerTag(axion, 7, TagByteArray.class, byte[].class, TagAdapter.Spec.BYTE_ARRAY, TypeConverter.Spec.BYTE_ARRAY);
      registerTag(axion, 8, TagString.class, String.class, TagAdapter.Spec.STRING, TypeConverter.Spec.STRING);
      registerTag(axion, 9, TagList.class, List.class, TagAdapter.Spec.LIST, TypeConverter.Spec.LIST);
      registerTag(axion, 10, TagCompound.class, Map.class, TagAdapter.Spec.COMPOUND, TypeConverter.Spec.COMPOUND);
      registerTag(axion, 11, TagIntArray.class, int[].class, TagAdapter.Spec.INT_ARRAY, TypeConverter.Spec.INT_ARRAY);

      registerFactory(axion, new ObjectTypeConverterFactory());

      setCharacterEncodingType(CharacterEncodingType.MODIFIED_UTF_8);
      setCompressionType(CompressionType.GZip);
      setImmutable();
    }};
  }

  public static AxionConfiguration getExtConfiguration(Axion axion) {
    return new AxionConfiguration() {{
      registerFactory(axion, new AxionWritableTypeConverterFactory());
      registerFactory(axion, new CollectionTypeConverterFactory());
      registerFactory(axion, new MapTypeConverterFactory());
      registerFactory(axion, new EnumTypeConverterFactory());

      registerBaseTagAdapter(axion, TagAdapter.Spec.BASE);
      registerTag(axion, 1, TagByte.class, Byte.class, TagAdapter.Spec.BYTE, TypeConverter.Spec.BYTE);
      registerTag(axion, 2, TagShort.class, Short.class, TagAdapter.Spec.SHORT, TypeConverter.Spec.SHORT);
      registerTag(axion, 3, TagInt.class, Integer.class, TagAdapter.Spec.INT, TypeConverter.Spec.INT);
      registerTag(axion, 4, TagLong.class, Long.class, TagAdapter.Spec.LONG, TypeConverter.Spec.LONG);
      registerTag(axion, 5, TagFloat.class, Float.class, TagAdapter.Spec.FLOAT, TypeConverter.Spec.FLOAT);
      registerTag(axion, 6, TagDouble.class, Double.class, TagAdapter.Spec.DOUBLE, TypeConverter.Spec.DOUBLE);
      registerTag(axion, 7, TagByteArray.class, byte[].class, TagAdapter.Spec.BYTE_ARRAY, TypeConverter.Spec.BYTE_ARRAY);
      registerTag(axion, 8, TagString.class, String.class, TagAdapter.Spec.STRING, TypeConverter.Spec.STRING);
      registerTag(axion, 9, TagList.class, List.class, TagAdapter.Spec.LIST, TypeConverter.Spec.LIST);
      registerTag(axion, 10, TagCompound.class, Map.class, TagAdapter.Spec.COMPOUND, TypeConverter.Spec.COMPOUND);
      registerTag(axion, 11, TagIntArray.class, int[].class, TagAdapter.Spec.INT_ARRAY, TypeConverter.Spec.INT_ARRAY);

      registerTag(axion, 80, TagBoolean.class, Boolean.class, TagAdapter.Ext.BOOLEAN, TypeConverter.Ext.BOOLEAN);
      registerTag(axion, 81, TagDoubleArray.class, double[].class, TagAdapter.Ext.DOUBLE_ARRAY, TypeConverter.Ext
          .DOUBLE_ARRAY);
      registerTag(axion, 82, TagFloatArray.class, float[].class, TagAdapter.Ext.FLOAT_ARRAY, TypeConverter.Ext
          .FLOAT_ARRAY);
      registerTag(axion, 83, TagLongArray.class, long[].class, TagAdapter.Ext.LONG_ARRAY, TypeConverter.Ext.LONG_ARRAY);
      registerTag(axion, 84, TagShortArray.class, short[].class, TagAdapter.Ext.SHORT_ARRAY, TypeConverter.Ext
          .SHORT_ARRAY);
      registerTag(axion, 85, TagStringArray.class, String[].class, TagAdapter.Ext.STRING_ARRAY, TypeConverter.Ext
          .STRING_ARRAY);
      registerTag(axion, 86, TagBooleanArray.class, boolean[].class, TagAdapter.Ext.BOOLEAN_ARRAY, TypeConverter.Ext
          .BOOLEAN_ARRAY);

      registerFactory(axion, new ObjectTypeConverterFactory());

      setCharacterEncodingType(CharacterEncodingType.MODIFIED_UTF_8);
      setCompressionType(CompressionType.GZip);
      setImmutable();
    }};
  }

  /**
   * The compression type that an {@link AxionConfiguration} will use to read and write when accessing the underlying
   * streams.
   */
  public enum CompressionType {
    GZip, Deflater, None
  }

  /**
   * The character encoding type that an {@link AxionConfiguration} will use to read and write strings with the {@link
   * AxionInputStream#readString()} and {@link AxionOutputStream#writeString(String)} methods.
   *
   * @see AxionConfiguration#setCharacterEncodingType(CharacterEncodingType)
   */
  public enum CharacterEncodingType {
    MODIFIED_UTF_8, US_ASCII, ISO_8859_1, UTF_8, UTF_16BE, UTF_16LE, UTF_16
  }

  private final ConstructorConstructor constructorConstructor;
  private final TagAdapterRegistry tagAdapterRegistry;
  private final TypeConverterRegistry typeConverterRegistry;
  private StreamCompressionWrapper streamCompressionWrapper;
  private CharacterEncodingType characterEncodingType;
  private AxionConfigurationProtection configurationProtection;

  /**
   * Creates a new {@link AxionConfiguration} instance with {@link ProtectionMode#Unlocked}.
   */
  protected AxionConfiguration() {
    this(ProtectionMode.Unlocked);
  }

  /**
   * Creates a new {@link AxionConfiguration} instance by duplicating all registered {@link TagAdapter}s, {@link
   * TypeConverter}s. The {@link StreamCompressionWrapper} is not duplicated, merely referenced.
   * <p>
   * The new instance created will be {@link ProtectionMode#Unlocked}.
   *
   * @param toCopy the {@link AxionConfiguration} to duplicate
   */
  protected AxionConfiguration(
      final Axion axion,
      final AxionConfiguration toCopy
  ) {
    LOG.debug("Entering AxionConfiguration(toCopy=[{}])", toCopy);
    constructorConstructor = new ConstructorConstructor();
    tagAdapterRegistry = toCopy.tagAdapterRegistry.copy(axion);
    typeConverterRegistry = toCopy.typeConverterRegistry.copy(axion);
    configurationProtection = new AxionConfigurationProtection(ProtectionMode.Unlocked);
    streamCompressionWrapper = toCopy.streamCompressionWrapper;
    characterEncodingType = toCopy.characterEncodingType;
    LOG.debug("Leaving AxionConfiguration(): [{}]", this);
  }

  /**
   * Creates a new {@link AxionConfiguration} instance with the given {@link ProtectionMode}.
   *
   * @param newProtectionMode new protection mode
   */
  protected AxionConfiguration(
      final ProtectionMode newProtectionMode
  ) {
    LOG.debug("Entering AxionConfiguration(newProtectionMode=[{}])", newProtectionMode);
    constructorConstructor = new ConstructorConstructor();
    tagAdapterRegistry = new TagAdapterRegistry();
    typeConverterRegistry = new TypeConverterRegistry();
    configurationProtection = new AxionConfigurationProtection(newProtectionMode);
    streamCompressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;
    characterEncodingType = CharacterEncodingType.MODIFIED_UTF_8;
    LOG.debug("Leaving AxionConfiguration(): [{}]", this);
  }

  /**
   * Changes this configuration's protection mode to <b>Locked</b>. Any attempt to change this configuration while it is
   * locked will result in an exception. Use {@link #unlock()} to unlock.
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
   * Changes this configuration's protection mode to <b>Immutable</b>. Once this mode is set, it can't be undone.
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
   * @param newCharacterEncodingType the new encoding type
   * @return this {@link AxionConfiguration}
   */
  protected AxionConfiguration setCharacterEncodingType(
      final CharacterEncodingType newCharacterEncodingType
  ) {
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
   * @param newBaseTagAdapter the new base tag adapter
   * @throws AxionConfigurationException
   * @throws AxionInstanceException
   * @see #getBaseTagAdapter()
   */
  protected AxionConfiguration registerBaseTagAdapter(
      final Axion axion,
      final TagAdapter<Tag> newBaseTagAdapter
  ) throws AxionConfigurationException, AxionInstanceException {
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    tagAdapterRegistry.registerBaseTagAdapter(axion, newBaseTagAdapter);
    return this;
  }

  /**
   * Registers the relationships for a tag.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   *
   * @param id        the id of the tag
   * @param tClass    the class of the tag
   * @param vClass    the class of the type
   * @param adapter   {@link TagAdapter} for the tag
   * @param converter {@link TypeConverter} for the tag
   * @return this {@link AxionConfiguration}
   * @throws AxionTagRegistrationException
   * @throws AxionInstanceException
   * @see #getAdapterFor(Class)
   * @see #getAdapterFor(int)
   * @see #getClassFor(int)
   * @see #getConverter(Class)
   * @see #getConverter(Axion, AxionTypeToken)
   * @see #getIdFor(Class)
   */
  protected <T extends Tag, V> AxionConfiguration registerTag(
      final Axion axion,
      final int id,
      final Class<T> tClass,
      final Class<V> vClass,
      final TagAdapter<T> adapter,
      final TypeConverter<T, V> converter
  ) throws AxionTagRegistrationException, AxionInstanceException {
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    tagAdapterRegistry.register(axion, id, tClass, vClass, adapter);
    typeConverterRegistry.registerTag(axion, tClass, vClass, converter);
    return this;
  }

  /**
   * Registers a {@link TypeConverterFactory} implementation.
   *
   * @param factory factory
   * @return this {@link AxionConfiguration}
   */
  protected AxionConfiguration registerFactory(
      final Axion axion,
      final TypeConverterFactory factory
  ) {
    configurationProtection.assertUnlocked();
    configurationProtection.assertMutable();
    typeConverterRegistry.registerFactory(axion, factory);
    return this;
  }

  /**
   * Sets this {@link AxionConfiguration} to use the {@link CompressionType} given.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   *
   * @param newCompressionType the compression type to use
   * @return this {@link AxionConfiguration}
   */
  protected AxionConfiguration setCompressionType(
      final CompressionType newCompressionType
  ) {
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

  protected ConstructorConstructor getConstructorConstructor() {
    return constructorConstructor;
  }

  /**
   * Returns the {@link TagAdapter} registered as the base tag adapter.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @return the base {@link TagAdapter}
   */
  protected TagAdapter<Tag> getBaseTagAdapter() throws AxionTagRegistrationException {
    return tagAdapterRegistry.getBaseTagAdapter();
  }

  /**
   * Returns the int id for the {@link Tag} class given.
   *
   * @param tagClass class to get the id for
   * @return the int id for the {@link Tag} class given
   */
  protected int getIdFor(
      final Class<? extends Tag> tagClass
  ) {
    return tagAdapterRegistry.getIdFor(tagClass);
  }

  /**
   * Returns the {@link Tag} class for the int id given.
   * <p>
   * If no class is found, an exception is thrown.
   *
   * @param id the id to get the class for
   * @return the {@link Tag} class for the int id given
   * @throws AxionTagRegistrationException
   */
  protected Class<? extends Tag> getClassFor(
      final int id
  ) throws AxionTagRegistrationException {
    return tagAdapterRegistry.getClassFor(id);
  }

  /**
   * Returns the {@link TagAdapter} for the int id given.
   * <p>
   * If no adapter is found, an exception is thrown.
   *
   * @param id the id to get the {@link TagAdapter} for
   * @return the {@link TagAdapter} for the int id given
   * @throws AxionTagRegistrationException
   */
  protected <T extends Tag> TagAdapter<T> getAdapterFor(
      final int id
  ) throws AxionTagRegistrationException {
    return tagAdapterRegistry.getAdapterFor(id);
  }

  /**
   * Returns the {@link TagAdapter} for the {@link Tag} class given.
   * <p>
   * If no adapter is found, an exception is thrown.
   *
   * @param tagClass {@link Tag} class to get the {@link TagAdapter} for
   * @return the {@link TagAdapter} for the {@link Tag} class given
   * @throws AxionTagRegistrationException
   */
  protected <T extends Tag> TagAdapter<T> getAdapterFor(
      final Class<T> tagClass
  ) throws AxionTagRegistrationException {
    return tagAdapterRegistry.getAdapterFor(tagClass);
  }

  /**
   * Returns the {@link TypeConverter} for the {@link Tag} given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param tClass tag class
   * @return the {@link TypeConverter} for the {@link Tag} given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  protected <T extends Tag, V> TypeConverter<T, V> getConverter(
      final Class<T> tClass
  ) throws AxionTagRegistrationException {
    return (TypeConverter<T, V>) typeConverterRegistry.getConverterForTag(tClass);
  }

  /**
   * Returns the {@link TypeConverter} for the value given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param typeToken {@link AxionTypeToken} to get the {@link TypeConverter} for
   * @return the {@link TypeConverter} for the value given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  protected <T extends Tag, V> TypeConverter<T, V> getConverter(
      final Axion axion,
      final AxionTypeToken<V> typeToken
  ) throws AxionTagRegistrationException {
    return (TypeConverter<T, V>) typeConverterRegistry.getConverter(axion, typeToken);
  }

  /**
   * Returns true if the given {@link AxionTypeToken} has a converter registered.
   *
   * @param typeToken typeToken
   * @return true if the given {@link AxionTypeToken} has a converter registered
   */
  protected boolean hasConverter(
      final Axion axion,
      final AxionTypeToken typeToken
  ) {
    return typeConverterRegistry.hasConverter(axion, typeToken);
  }

  /**
   * Returns true if the given tag's class has a converter registered.
   *
   * @param tag tag
   * @param <T> tag type
   * @return true if the given tag's class has a converter registered
   */
  protected <T extends Tag> boolean hasConverter(final T tag) {
    return typeConverterRegistry.hasConverterForTag(tag.getClass());
  }

  /**
   * Wraps an {@link InputStream} using the {@link StreamCompressionWrapper} set with {@link
   * #setCompressionType(CompressionType)}.
   *
   * @param inputStream the {@link InputStream} to wrap
   * @return a new {@link AxionInputStream}
   * @throws IOException
   * @see #setCompressionType(CompressionType)
   */
  protected AxionInputStream wrap(final InputStream inputStream) throws IOException {
    return new AxionInputStream(
        streamCompressionWrapper.wrap(inputStream),
        CharacterEncoderFactory.create(characterEncodingType)
    );
  }

  /**
   * Wraps an {@link OutputStream} using the {@link StreamCompressionWrapper} set with {@link
   * #setCompressionType(CompressionType)}.
   *
   * @param outputStream the {@link OutputStream} to wrap
   * @return a new {@link AxionInputStream}
   * @throws IOException
   * @see #setCompressionType(CompressionType)
   */
  protected AxionOutputStream wrap(final OutputStream outputStream) throws IOException {
    return new AxionOutputStream(
        streamCompressionWrapper.wrap(outputStream),
        CharacterEncoderFactory.create(characterEncodingType)
    );
  }

  protected AxionConfiguration copy(Axion axion) {
    return new AxionConfiguration(axion, this);
  }

}
