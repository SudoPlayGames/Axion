package com.sudoplay.axion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.TagAdapterRegistry;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.converter.TagConverterRegistry;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
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
import com.sudoplay.axion.stream.CharacterEncoder;
import com.sudoplay.axion.stream.StreamCompressionWrapper;

public class AxionConfiguration implements Cloneable {

  protected static final AxionConfiguration SPEC_CONFIGURATION = new AxionConfiguration() {
    {
      registerTagAdapter(1, TagByte.class, TagAdapter.BYTE);
      registerTagAdapter(2, TagShort.class, TagAdapter.SHORT);
      registerTagAdapter(3, TagInt.class, TagAdapter.INT);
      registerTagAdapter(4, TagLong.class, TagAdapter.LONG);
      registerTagAdapter(5, TagFloat.class, TagAdapter.FLOAT);
      registerTagAdapter(6, TagDouble.class, TagAdapter.DOUBLE);
      registerTagAdapter(7, TagByteArray.class, TagAdapter.BYTE_ARRAY);
      registerTagAdapter(8, TagString.class, TagAdapter.STRING);
      registerTagAdapter(9, TagList.class, TagAdapter.LIST);
      registerTagAdapter(10, TagCompound.class, TagAdapter.COMPOUND);
      registerTagAdapter(11, TagIntArray.class, TagAdapter.INT_ARRAY);

      registerTagConverter(TagByte.class, Byte.class, TagConverter.BYTE);
      registerTagConverter(TagShort.class, Short.class, TagConverter.SHORT);
      registerTagConverter(TagInt.class, Integer.class, TagConverter.INT);
      registerTagConverter(TagLong.class, Long.class, TagConverter.LONG);
      registerTagConverter(TagFloat.class, Float.class, TagConverter.FLOAT);
      registerTagConverter(TagDouble.class, Double.class, TagConverter.DOUBLE);
      registerTagConverter(TagByteArray.class, byte[].class, TagConverter.BYTE_ARRAY);
      registerTagConverter(TagString.class, String.class, TagConverter.STRING);
      registerTagConverter(TagList.class, List.class, TagConverter.LIST);
      registerTagConverter(TagCompound.class, Map.class, TagConverter.COMPOUND);
      registerTagConverter(TagIntArray.class, int[].class, TagConverter.INT_ARRAY);

      setCharacterEncodingType(CharacterEncodingType.MODIFIED_UTF_8);
      setCompressionType(CompressionType.GZip);
      setConfigurationImmutable();
    }
  };

  protected static final AxionConfiguration EXT_CONFIGURATION = new AxionConfiguration(SPEC_CONFIGURATION) {
    {
      registerTagAdapter(80, TagBoolean.class, TagAdapter.BOOLEAN);
      registerTagAdapter(81, TagDoubleArray.class, TagAdapter.DOUBLE_ARRAY);
      registerTagAdapter(82, TagFloatArray.class, TagAdapter.FLOAT_ARRAY);
      registerTagAdapter(83, TagLongArray.class, TagAdapter.LONG_ARRAY);
      registerTagAdapter(84, TagShortArray.class, TagAdapter.SHORT_ARRAY);
      registerTagAdapter(85, TagStringArray.class, TagAdapter.STRING_ARRAY);

      registerTagConverter(TagBoolean.class, Boolean.class, TagConverter.BOOLEAN);
      registerTagConverter(TagDoubleArray.class, double[].class, TagConverter.DOUBLE_ARRAY);
      registerTagConverter(TagFloatArray.class, float[].class, TagConverter.FLOAT_ARRAY);
      registerTagConverter(TagLongArray.class, long[].class, TagConverter.LONG_ARRAY);
      registerTagConverter(TagShortArray.class, short[].class, TagConverter.SHORT_ARRAY);
      registerTagConverter(TagStringArray.class, String[].class, TagConverter.STRING_ARRAY);

      setCharacterEncodingType(CharacterEncodingType.MODIFIED_UTF_8);
      setCompressionType(CompressionType.GZip);
      setConfigurationImmutable();
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

  private final TagAdapterRegistry adapters;
  private final TagConverterRegistry converters;
  private StreamCompressionWrapper streamCompressionWrapper;
  private CharacterEncoder characterEncoder;
  private ProtectionMode configurationProtectionMode;

  protected AxionConfiguration() {
    this(ProtectionMode.Unlocked);
  }

  protected AxionConfiguration(final AxionConfiguration toCopy) {
    adapters = toCopy.adapters.clone();
    converters = toCopy.converters.clone();
    configurationProtectionMode = ProtectionMode.Unlocked;
    streamCompressionWrapper = toCopy.streamCompressionWrapper;
    characterEncoder = toCopy.characterEncoder;
  }

  protected AxionConfiguration(final ProtectionMode newProtectionMode) {
    adapters = new TagAdapterRegistry();
    converters = new TagConverterRegistry();
    configurationProtectionMode = newProtectionMode;
    streamCompressionWrapper = StreamCompressionWrapper.GZIP_STREAM_COMPRESSION_WRAPPER;
    characterEncoder = CharacterEncoder.MODIFIED_UTF_8;
  }

  public void lockConfiguration() {
    assertMutable();
    configurationProtectionMode = ProtectionMode.Locked;
  }

  public void unlockConfiguration() {
    assertMutable();
    configurationProtectionMode = ProtectionMode.Unlocked;
  }

  public void setConfigurationImmutable() {
    configurationProtectionMode = ProtectionMode.Immutable;
  }

  public boolean isLocked() {
    return configurationProtectionMode == ProtectionMode.Locked;
  }

  public boolean isUnlocked() {
    return configurationProtectionMode == ProtectionMode.Unlocked;
  }

  public boolean isImmutable() {
    return configurationProtectionMode == ProtectionMode.Immutable;
  }

  protected void assertUnlocked() {
    if (configurationProtectionMode == ProtectionMode.Locked) {
      throw new IllegalStateException(Axion.class.getSimpleName() + " instance has been locked and can't be modified");
    }
  }

  protected void assertMutable() {
    if (configurationProtectionMode == ProtectionMode.Immutable) {
      throw new IllegalStateException(Axion.class.getSimpleName() + " instance is immutable and can't be modified");
    }
  }

  public void setCharacterEncodingType(final CharacterEncodingType newCharacterEncodingType) {
    assertUnlocked();
    assertMutable();
    switch (newCharacterEncodingType) {
    case ISO_8859_1:
      characterEncoder = CharacterEncoder.ISO_8859_1;
      break;
    case US_ASCII:
      characterEncoder = CharacterEncoder.US_ASCII;
      break;
    case UTF_16:
      characterEncoder = CharacterEncoder.UTF_16;
      break;
    case UTF_16BE:
      characterEncoder = CharacterEncoder.UTF_16BE;
      break;
    case UTF_16LE:
      characterEncoder = CharacterEncoder.UTF_16LE;
      break;
    case UTF_8:
      characterEncoder = CharacterEncoder.UTF_8;
      break;
    default:
    case MODIFIED_UTF_8:
      characterEncoder = CharacterEncoder.MODIFIED_UTF_8;
      break;
    }
  }

  public <T extends Tag, V> void registerTagConverter(final Class<T> tagClass, final Class<V> type, final TagConverter<T, V> converter) {
    assertUnlocked();
    assertMutable();
    converters.register(tagClass, type, converter);
  }

  public <T extends Tag> void registerTagAdapter(final int id, final Class<T> tagClass, final TagAdapter<T> adapter) {
    assertUnlocked();
    assertMutable();
    adapters.register(id, tagClass, adapter);
  }

  public void setCompressionType(final CompressionType newCompressionType) {
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
  }

  protected int getIdFor(final Class<? extends Tag> tagClass) {
    return adapters.getIdFor(tagClass);
  }

  protected Class<? extends Tag> getClassFor(final int id) {
    return adapters.getClassFor(id);
  }

  @SuppressWarnings("unchecked")
  protected <T extends Tag> T createInstance(final int id, final String newName) {
    return (T) adapters.createInstance(id, newName);
  }

  protected <T extends Tag> T createInstance(final Class<T> tagClass, final String newName) {
    return adapters.createInstance(tagClass, newName);
  }

  protected <T extends Tag> TagAdapter<T> getAdapterFor(final int id) {
    return adapters.getAdapterFor(id);
  }

  protected <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) {
    return adapters.getAdapterFor(tagClass);
  }

  protected <T extends Tag, V> V convertToValue(final T tag, final Axion axion) {
    return converters.convertToValue(tag, axion);
  }

  protected <V, T extends Tag> T convertToTag(final String name, final V value, final Axion axion) {
    return converters.convertToTag(name, value, axion);
  }

  protected String readString(final DataInputStream dataInputStream) throws IOException {
    return characterEncoder.read(dataInputStream);
  }

  protected void writeString(final DataOutputStream dataOutputStream, final String data) throws IOException {
    characterEncoder.write(dataOutputStream, data);
  }

  protected InputStream wrap(final InputStream inputStream) throws IOException {
    return streamCompressionWrapper.wrap(inputStream);
  }

  protected OutputStream wrap(final OutputStream outputStream) throws IOException {
    return streamCompressionWrapper.wrap(outputStream);
  }

  @Override
  protected AxionConfiguration clone() {
    return new AxionConfiguration(this);
  }

}
