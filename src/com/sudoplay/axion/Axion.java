package com.sudoplay.axion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.AxionConfiguration.ProtectionMode;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;

public class Axion {

  private static final String EXT_INSTANCE_NAME = "AXION_EXT";
  private static final String SPEC_INSTANCE_NAME = "AXION_SPEC";
  private static final Axion EXT_INSTANCE = new Axion(AxionConfiguration.EXT_CONFIGURATION);
  private static final Axion SPEC_INSTANCE = new Axion(AxionConfiguration.SPEC_CONFIGURATION);

  @SuppressWarnings("serial")
  private static final Map<String, Axion> INSTANCES = new HashMap<String, Axion>() {
    {
      put(EXT_INSTANCE_NAME, EXT_INSTANCE);
      put(SPEC_INSTANCE_NAME, SPEC_INSTANCE);
    }
  };

  private AxionConfiguration configuration;

  private Axion() {
    this(new AxionConfiguration(ProtectionMode.Unlocked));
  }

  private Axion(final AxionConfiguration newConfiguration) {
    configuration = newConfiguration;
  }

  public static Axion create(final String newName) throws AxionInstanceCreationException {
    if (INSTANCES.containsKey(newName)) {
      throw new AxionInstanceCreationException(Axion.class.getSimpleName() + " instance alread exists with name: " + newName);
    }
    Axion instance = new Axion();
    INSTANCES.put(newName, instance);
    return instance;
  }

  public static Axion createFrom(final Axion axion, final String newName) throws AxionInstanceCreationException {
    if (INSTANCES.containsKey(newName)) {
      throw new AxionInstanceCreationException(Axion.class.getSimpleName() + " instance alread exists with name: " + newName);
    }
    Axion instance = new Axion(axion.getConfiguration().clone());
    INSTANCES.put(newName, instance);
    return instance;
  }

  public static Axion destroy(final String name) {
    return INSTANCES.remove(name);
  }

  public static Axion get(final String name) {
    return INSTANCES.get(name);
  }

  public static Axion getExt() {
    return INSTANCES.get(EXT_INSTANCE_NAME);
  }

  public static Axion getSpec() {
    return INSTANCES.get(SPEC_INSTANCE_NAME);
  }

  public static Map<String, Axion> getInstances() {
    return Collections.unmodifiableMap(INSTANCES);
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

  public <T extends Tag> TagAdapter<T> getAdapterFor(final int id) {
    return configuration.getAdapterFor(id);
  }

  public <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) {
    return configuration.getAdapterFor(tagClass);
  }

  public <T extends Tag, V> TagConverter<T, V> getConverterFor(final T tag) {
    return configuration.getConverterFor(tag);
  }

  public <T extends Tag, V> TagConverter<T, V> getConverterFor(final V value) {
    return configuration.getConverterFor(value);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, V> V convertToValue(final T tag) {
    return (V) configuration.getConverterFor(tag).convert(tag);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, V> T convertToTag(final String name, final V value) {
    return (T) configuration.getConverterFor(value).convert(name, value);
  }

  public <T extends Tag, O> O createObjectFrom(final T tag, final Class<O> type) {
    return configuration.createObjectFrom(tag, type, this);
  }

  public <T extends Tag, O> T createTagFrom(final String name, final O object) {
    return configuration.createTagFrom(name, object, this);
  }

  public TagCompound read(final File file) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file);
    TagCompound result = read(fileInputStream);
    fileInputStream.close();
    return result;
  }

  public void write(final TagCompound tagCompound, final File file) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    write(tagCompound, file);
    fileOutputStream.close();
  }

  public TagCompound read(final InputStream inputStream) throws IOException {
    Tag result = readTag(null, configuration.wrap(inputStream));
    if (!(result instanceof TagCompound)) {
      throw new AxionReadException("Root tag not of type " + TagCompound.class.getSimpleName());
    }
    return (TagCompound) result;
  }

  public void write(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    writeTag(tagCompound, configuration.wrap(outputStream));
  }

  protected Tag readTag(final Tag parent, final AxionInputStream in) throws IOException {
    return configuration.getBaseTagAdapter().read(parent, in);
  }

  protected void writeTag(final Tag tag, final AxionOutputStream out) throws IOException {
    configuration.getBaseTagAdapter().write(tag, out);
  }

}
