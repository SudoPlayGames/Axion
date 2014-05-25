package com.sudoplay.axion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.TagAdapterRegistry;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.converter.TagConverterRegistry;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.stream.CharacterEncoder;
import com.sudoplay.axion.stream.StreamCompressionWrapper;

public class AxionConfiguration {

  public static enum CompressionType {
    GZip, Deflater, None
  }

  public static enum CharacterEncodingType {
    MODIFIED_UTF_8, US_ASCII, ISO_8859_1, UTF_8, UTF_16BE, UTF_16LE, UTF_16
  }

  protected static enum ProtectionMode {
    Unlocked, Locked, Immutable
  }

  private final TagAdapterRegistry adapters = new TagAdapterRegistry();
  private final TagConverterRegistry converters = new TagConverterRegistry();
  private StreamCompressionWrapper streamCompressionWrapper;
  private CharacterEncoder characterEncoder;
  private ProtectionMode configurationProtectionMode;

  protected AxionConfiguration() {
    this(ProtectionMode.Unlocked);
  }

  protected AxionConfiguration(final ProtectionMode newProtectionMode) {
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

}
