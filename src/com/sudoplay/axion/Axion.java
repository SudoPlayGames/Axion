package com.sudoplay.axion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.AxionConfiguration.ProtectionMode;
import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.spec.tag.Tag;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;

public class Axion {

  private static final Logger LOG = LoggerFactory.getLogger(Axion.class);

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

  public static Axion create(final String newName) {
    if (INSTANCES.containsKey(newName)) {
      throw new IllegalArgumentException(Axion.class.getSimpleName() + " instance alread exists with name: " + newName);
    }
    Axion instance = new Axion();
    INSTANCES.put(newName, instance);
    return instance;
  }

  public static Axion createFrom(final Axion axion, final String newName) {
    if (INSTANCES.containsKey(newName)) {
      throw new IllegalArgumentException(Axion.class.getSimpleName() + " instance alread exists with name: " + newName);
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

  public static Axion getDefault() {
    return INSTANCES.get(EXT_INSTANCE_NAME);
  }

  public static Axion getOriginal() {
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
