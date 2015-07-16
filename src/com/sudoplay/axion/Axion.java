package com.sudoplay.axion;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
import com.sudoplay.axion.AxionConfiguration.CompressionType;
import com.sudoplay.axion.AxionConfigurationProtection.ProtectionMode;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.api.impl.DefaultAxionReader;
import com.sudoplay.axion.api.impl.DefaultAxionWriter;
import com.sudoplay.axion.mapper.AxionMapperRegistrationException;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.DurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the main class for {@link Axion}, a tool for working with NBT.
 *
 * @author Jason Taylor
 */
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

  /**
   * Reference to this instances {@link AxionConfiguration}
   */
  private AxionConfiguration configuration;

  /**
   * Creates a new instance of {@link Axion} with an empty, unlocked configuration.
   */
  private Axion() {
    this(new AxionConfiguration(ProtectionMode.Unlocked));
  }

  /**
   * Creates a new instance of {@link Axion} with the {@link AxionConfiguration} given.
   *
   * @param newConfiguration the {@link AxionConfiguration} for the new instance
   */
  private Axion(final AxionConfiguration newConfiguration) {
    configuration = newConfiguration;
  }

  /**
   * Returns true if Axion has an instance with the given name.
   *
   * @param name instance name
   * @return true if Axion has an instance with the given name
   */
  public static boolean hasInstance(final String name) {
    return INSTANCES.containsKey(name);
  }

  /**
   * Create a new configuration with the given name. If a configuration already exists with the name provided, an
   * exception is thrown.
   * <p>
   * This new configuration will not have a base tag or any other tags registered.
   *
   * @param newName name of the new configuration
   * @return a new configuration
   * @throws AxionInstanceException
   */
  @SuppressWarnings("unused")
  public static Axion createInstance(final String newName) throws AxionInstanceException {
    LOG.debug("Entering createInstance(newName=[{}])", newName);
    if (INSTANCES.containsKey(newName)) {
      LOG.error("Instance already exists with name [{}]", newName);
      throw new AxionInstanceException("Instance already exists with name: " + newName);
    }
    Axion instance = new Axion();
    INSTANCES.put(newName, instance);
    LOG.debug("Leaving createInstance(): [{}]", instance);
    return instance;
  }

  /**
   * Create a new configuration by duplicating the configuration named with the new name given. If a configuration
   * already exists with the name provided, an exception is thrown.
   *
   * @param name    the name of the configuration to duplicate
   * @param newName name of the new configuration
   * @return a new configuration
   * @throws AxionInstanceException
   */
  @SuppressWarnings("unused")
  public static Axion createInstanceFrom(final String name, final String newName) throws AxionInstanceException {
    return createInstanceFrom(Axion.getInstance(name), newName);
  }

  /**
   * Create a new configuration by duplicating the configuration given with the new name given. If a configuration
   * already exists with the name provided, an exception is thrown.
   *
   * @param axion   the configuration to duplicate
   * @param newName name of the new configuration
   * @return a new configuration
   * @throws AxionInstanceException
   */
  @SuppressWarnings("unused")
  public static Axion createInstanceFrom(final Axion axion, final String newName) throws AxionInstanceException {
    LOG.debug("Entering createInstanceFrom(axion=[{}], newName=[{}])", axion, newName);
    if (INSTANCES.containsKey(newName)) {
      LOG.error("Instance already exists with name [{}]", newName);
      throw new AxionInstanceException("Instance alread exists with name: " + newName);
    }
    Axion instance = new Axion(axion.configuration.clone());
    INSTANCES.put(newName, instance);
    LOG.debug("Leaving createInstanceFrom(): [{}]", instance);
    return instance;
  }

  /**
   * Removes and returns the configuration named or null if no configuration is found with the given name.
   *
   * @param name name of the configuration to remove
   * @return the removed configuration, null if it doesn't exist
   * @throws AxionInstanceException
   */
  @SuppressWarnings("unused")
  public static Axion deleteInstance(final String name) throws AxionInstanceException {
    LOG.debug("Entering deleteInstance(name=[{}])", name);
    if (SPEC_INSTANCE_NAME.equals(name) || EXT_INSTANCE_NAME.equals(name)) {
      LOG.error("Can't delete built-in instance [{}]", name);
      throw new AxionInstanceException("Can't delete built-in instance: " + name);
    }
    Axion removed = INSTANCES.remove(name);
    LOG.debug("Leaving deleteInstance(): [{}]", removed);
    return removed;
  }

  /**
   * Returns the Axion instance with the given name or null if no instance exists with that name.
   *
   * @param name name of the instance to get
   * @return the named Axion instance
   */
  @SuppressWarnings("unused")
  public static Axion getInstance(final String name) {
    return INSTANCES.get(name);
  }

  /**
   * Returns the built-in, extended Axion instance configuration.
   *
   * @return the extended Axion instance
   */
  @SuppressWarnings("unused")
  public static Axion getExtInstance() {
    return INSTANCES.get(EXT_INSTANCE_NAME);
  }

  /**
   * Returns the built-in, strict specification Axion instance configuration.
   *
   * @return the strict specification Axion instance
   */
  @SuppressWarnings("unused")
  public static Axion getSpecInstance() {
    return INSTANCES.get(SPEC_INSTANCE_NAME);
  }

  /**
   * Returns an unmodifiable map of all the {@link Axion} instances.
   *
   * @return an unmodifiable map of all the {@link Axion} instances
   */
  @SuppressWarnings("unused")
  public static Map<String, Axion> getInstances() {
    return Collections.unmodifiableMap(INSTANCES);
  }

  /**
   * Returns the {@link AxionConfiguration} for this {@link Axion} instance.
   *
   * @return the {@link AxionConfiguration} for this {@link Axion} instance
   */
  @SuppressWarnings("unused")
  public AxionConfiguration configuration() {
    return configuration;
  }

  /**
   * Changes protection mode to <b>Locked</b>. Any attempt to change the configuration while it is locked will result in
   * an exception. Use {@link #unlock()} to unlock.
   * <p>
   * Can't use when <b>Immutable</b>.
   *
   * @return this {@link Axion} instance
   * @see #unlock()
   * @see #setImmutable()
   */
  @SuppressWarnings("unused")
  public Axion lock() {
    configuration.lock();
    return this;
  }

  /**
   * Changes protection mode to <b>Unlocked</b>.
   * <p>
   * Can't use when <b>Immutable</b>.
   *
   * @return this {@link Axion} instance
   * @see #lock()
   * @see #setImmutable()
   */
  @SuppressWarnings("unused")
  public Axion unlock() {
    configuration.unlock();
    return this;
  }

  /**
   * Changes protection mode to <b>Immutable</b>. Once this mode is set, it can't be undone.
   *
   * @return this {@link Axion} instance
   * @see #lock()
   * @see #unlock()
   */
  @SuppressWarnings("unused")
  public Axion setImmutable() {
    configuration.setImmutable();
    return this;
  }

  public AxionWriter defaultWriter() {
    return new DefaultAxionWriter(this);
  }

  public AxionWriter defaultWriter(TagCompound tagCompound) {
    return new DefaultAxionWriter(tagCompound, this);
  }

  public AxionReader defaultReader(TagCompound tagCompound) {
    return new DefaultAxionReader(tagCompound, this);
  }

  /**
   * Returns the registered id for the {@link Tag} class given.
   * <p>
   * If no id is found, an exception is thrown.
   *
   * @param tagClass tag class to get the id for
   * @return the registered id for the {@link Tag} class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unused")
  public int getIdFor(final Class<? extends Tag> tagClass) throws AxionTagRegistrationException {
    return configuration.getIdFor(tagClass);
  }

  /**
   * @return true if protection mode is <b>Locked</b>
   */
  @SuppressWarnings("unused")
  public boolean isLocked() {
    return configuration.isLocked();
  }

  /**
   * @return true if protection mode is <b>Unlocked</b>
   */
  @SuppressWarnings("unused")
  public boolean isUnlocked() {
    return configuration.isUnlocked();
  }

  /**
   * @return true if protection mode is <b>Immutable</b>
   */
  @SuppressWarnings("unused")
  public boolean isImmutable() {
    return configuration.isImmutable();
  }

  /**
   * Sets the {@link CharacterEncodingType}.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   *
   * @param newCharacterEncodingType the new encoding type
   * @return this {@link Axion} instance
   */
  @SuppressWarnings("unused")
  public Axion setCharacterEncodingType(final CharacterEncodingType newCharacterEncodingType) {
    configuration.setCharacterEncodingType(newCharacterEncodingType);
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
  @SuppressWarnings("unused")
  public Axion registerBaseTagAdapter(final TagAdapter<Tag> newBaseTagAdapter) throws AxionConfigurationException,
      AxionInstanceException {
    configuration.registerBaseTagAdapter(newBaseTagAdapter);
    return this;
  }

  /**
   * Registers the relationships for a tag.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   *
   * @param id        the id of the tag
   * @param tagClass  the class of the tag
   * @param type      the class of the type
   * @param adapter   {@link TagAdapter} for the tag
   * @param converter {@link TagConverter} for the tag
   * @return this {@link Axion} instance
   * @throws AxionTagRegistrationException
   * @throws AxionInstanceException
   * @see #getAdapterFor(Class)
   * @see #getAdapterFor(int)
   * @see #getClassFor(int)
   * @see #getConverterFor(Tag)
   * @see #getConverterFor(Object)
   * @see #getIdFor(Class)
   */
  @SuppressWarnings("unused")
  public <T extends Tag, V> Axion registerTag(final int id, final Class<T> tagClass, final Class<V> type, final
  TagAdapter<T> adapter,
                                              final TagConverter<T, V> converter) throws
      AxionTagRegistrationException, AxionInstanceException {
    configuration.registerTag(id, tagClass, type, adapter, converter);
    return this;
  }

  /**
   * Registers a {@link NBTObjectMapper} for the class type given.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   *
   * @param type   the type of the object
   * @param mapper the mapper
   * @return this {@link Axion} instance
   */
  @SuppressWarnings("unused")
  public <T extends Tag, O> Axion registerNBTObjectMapper(final Class<O> type, final NBTObjectMapper<T, O> mapper) {
    configuration.registerNBTObjectMapper(type, mapper);
    return this;
  }

  /**
   * Sets this {@link AxionConfiguration} to use the {@link CompressionType} given.
   * <p>
   * Can't use when <b>Locked</b> or <b>Immutable</b>.
   *
   * @param newCompressionType the compression type to use
   * @return this {@link Axion} instance
   */
  @SuppressWarnings("unused")
  public Axion setCompressionType(final CompressionType newCompressionType) {
    configuration.setCompressionType(newCompressionType);
    return this;
  }

  /**
   * Returns the {@link TagAdapter} registered as the base tag adapter.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @return the base {@link TagAdapter}
   */
  @SuppressWarnings("unused")
  protected TagAdapter<Tag> getBaseTagAdapter() throws AxionTagRegistrationException {
    return configuration.getBaseTagAdapter();
  }

  /**
   * Returns the registered {@link Tag} class for the id given.
   * <p>
   * If no class is found, an exception is thrown.
   *
   * @param id id to get the tag class for
   * @return the registered {@link Tag} class for the id given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unused")
  public Class<? extends Tag> getClassFor(final int id) throws AxionTagRegistrationException {
    return configuration.getClassFor(id);
  }

  /**
   * Returns the registered {@link TagAdapter} for the id given.
   * <p>
   * If no adapter is found, an exception is thrown.
   *
   * @param id id to get the adapter for
   * @return the registered {@link TagAdapter} for the id given
   */
  @SuppressWarnings("unused")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final int id) throws AxionTagRegistrationException {
    return configuration.getAdapterFor(id);
  }

  /**
   * Returns the registered {@link TagAdapter} for the tag class given.
   * <p>
   * If no adapter is found, an exception is thrown.
   *
   * @param tagClass tag class to get the adapter for
   * @return the registered {@link TagAdapter} for the tag class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unused")
  public <T extends Tag> TagAdapter<T> getAdapterFor(final Class<T> tagClass) throws AxionTagRegistrationException {
    return configuration.getAdapterFor(tagClass);
  }

  /**
   * Returns the registered {@link TagConverter} for the tag given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param tag tag to get the tag converter for
   * @return the registered {@link TagConverter} for the tag given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unused")
  public <T extends Tag, V> TagConverter<T, V> getConverterFor(final T tag) throws AxionTagRegistrationException {
    return configuration.getConverterFor(tag);
  }

  /**
   * Returns the registered {@link TagConverter} for the value given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param value value to get the tag converter for
   * @return the registered {@link TagConverter} for the value given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unused")
  public <T extends Tag, V> TagConverter<T, V> getConverterFor(final V value) throws AxionTagRegistrationException {
    return configuration.getConverterFor(value);
  }

  /**
   * Converts the given tag into its value using the {@link TagConverter} registered for the tag's class.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param tag tag to convert
   * @return the tag's converted value
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> V convertToValue(final T tag) throws AxionTagRegistrationException {
    return (V) configuration.getConverterFor(tag).convert(tag);
  }

  /**
   * Returns true if the given value's class has a converter registered.
   *
   * @param value value
   * @param <V>   value type
   * @return true if the given value's class has a converter registered
   */
  public <V> boolean hasConverterFor(final V value) {
    return configuration.hasConverterFor(value);
  }

  /**
   * Returns true if the given tag's class has a converter registered.
   *
   * @param tag tag
   * @param <T> tag type
   * @return true if the given tag's class has a converter registered
   */
  public <T extends Tag> boolean hasConverterFor(final T tag) {
    return configuration.hasConverterFor(tag);
  }

  /**
   * Converts the given value into its tag using the converter registered for the value's class.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param name  name of the new tag
   * @param value value to convert
   * @return the value's converted tag
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> T createTagWithConverter(final String name, final V value) throws
      AxionTagRegistrationException {
    return (T) configuration.getConverterFor(value).convert(name, value);
  }

  /**
   * Returns the {@link NBTObjectMapper} registered for the class type given.
   * <p>
   * If no mapper is found, an exception is thrown.
   *
   * @param type class type to get the {@link NBTObjectMapper} for
   * @return the {@link NBTObjectMapper} registered for the class type given
   * @throws AxionMapperRegistrationException
   */
  @SuppressWarnings("unused")
  public <T extends Tag, O> NBTObjectMapper<T, O> getMapperFor(final Class<O> type) throws
      AxionMapperRegistrationException {
    return configuration.getMapperFor(type);
  }

  /**
   * Returns true if the given class has a mapper registered.
   *
   * @param type class
   * @return true if the given class has a mapper registered
   */
  public boolean hasMapperFor(final Class<?> type) {
    return configuration.hasMapperFor(type);
  }

  /**
   * Creates an object from a tag using the {@link NBTObjectMapper} registered for the class given as type.
   * <p>
   * If no mapper is found, an exception is thrown.
   *
   * @param tag  tag to create the object from
   * @param type class used to lookup registered mapper
   * @return a new object mapped from the tag given
   * @throws AxionMapperRegistrationException
   */
  public <T extends Tag, O> O createObjectFrom(final T tag, final Class<O> type) throws
      AxionMapperRegistrationException {
    return configuration.getMapperFor(type).createObjectFrom(tag, this);
  }

  /**
   * Creates a tag from an object using the {@link NBTObjectMapper} registered for the object's class.
   * <p>
   * If no mapper is found, an exception is thrown.
   *
   * @param name   name of the new tag
   * @param object object to convert
   * @return a new tag mapped from the object given
   * @throws AxionMapperRegistrationException
   */
  public <T extends Tag, O> T createTagWithMapper(final String name, final O object) throws
      AxionMapperRegistrationException {
    @SuppressWarnings("unchecked")
    NBTObjectMapper<T, O> mapper = (NBTObjectMapper<T, O>) configuration.getMapperFor(object.getClass());
    return mapper.createTagFrom(name, object, this);
  }

  /**
   * Appends a string version of the {@link Tag} given to the {@link StringBuilder} given.
   *
   * @param tag the {@link Tag} to append
   * @param out the {@link StringBuilder} to append to
   * @return the {@link StringBuilder} given
   * @throws AxionTagRegistrationException
   */
  public StringBuilder toString(final Tag tag, final StringBuilder out) throws AxionTagRegistrationException {
    return configuration.getBaseTagAdapter().toString(tag, out);
  }

  /**
   * Converts a {@link Tag} to a string and returns the result.
   *
   * @param tag the {@link Tag} to convert
   * @return the {@link Tag} string
   * @throws AxionTagRegistrationException
   */
  public String toString(final Tag tag) throws AxionTagRegistrationException {
    StringBuilder out = new StringBuilder();
    return configuration.getBaseTagAdapter().toString(tag, out).toString();
  }

  /**
   * Converts a tag into an object by:
   * <p>
   * <ol> <li>first, checking if <code>objectClass</code> is an implementation of AxionWritable, attempting to create a
   * new instance with the nullary constructor and calling <code>read(tag, axionInstance)</code></li> <li>next, checking
   * if there is a mapper registered for the object class</li> <li>next, checking if there is a converter registered for
   * the tag's class</li> <li>finally, throwing an AxionReadException</li> </ol>
   *
   * @param in          tag to read
   * @param objectClass class of the object to return
   * @param <O>         object type
   * @param <E>         tag type
   * @return O object
   * @throws AxionReadException
   */
  @SuppressWarnings("unchecked")
  public <O, E extends Tag> O createFromTag(E in, Class<O> objectClass) {
    if (AxionWritable.class.isAssignableFrom(objectClass) && in.getClass() == TagCompound.class) {
      try {
        Constructor<O> oConstructor = objectClass.getDeclaredConstructor();
        oConstructor.setAccessible(true);
        O oObject = oConstructor.newInstance();
        ((AxionWritable) oObject).read(this.defaultReader((TagCompound) in));
        return oObject;
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
        String message = "Failed to find nullary constructor for class: " + objectClass.toString();
        LOG.error(message, e);
        throw new AxionReadException(message, e);
      }

    } else if (this.hasMapperFor(objectClass)) {
      return this.createObjectFrom(in, objectClass);

    } else if (this.hasConverterFor(in)) {
      return this.convertToValue(in);
    }

    String message = "Class not assignable from AxionWritable, no converter registered, and no mapper registered: " +
        objectClass.toString();
    LOG.error(message);
    throw new AxionReadException(message);
  }

  /**
   * Simply calls write(String, Object) with a null String, creating a tag with no name; handy for TagList elements.
   *
   * @param object the object to convert
   * @param <E>    tag type
   * @return E tag
   * @throws AxionWriteException
   */
  public <E extends Tag> E createTagFrom(Object object) {
    return this.createTagFrom(null, object);
  }

  /**
   * Converts an object to a tag by first, checking if there is a mapper registered for the object's class, then
   * checking if there is a converter registered for the object. If nothing is registered, an AxionWriteException is
   * thrown.
   *
   * @param name   the name of the tag
   * @param object the object to convert
   * @param <E>    tag type
   * @return E tag
   * @throws AxionWriteException
   */
  @SuppressWarnings("unchecked")
  public <O, E extends Tag> E createTagFrom(String name, O object) {

    if (object instanceof AxionWritable) {
      return (E) this.createTagFrom((AxionWritable) object);

    } else if (object instanceof Collection) {
      //TODO

    } else if (object instanceof Map) {
      //TODO

    } else if (this.hasMapperFor(object.getClass())) {
      return this.createTagWithMapper(name, object);

    } else if (this.hasConverterFor(object)) {
      return this.createTagWithConverter(name, object);
    }

    String message = "Class has no converter or mapper registered : " +
        object.getClass().toString();
    LOG.error(message);
    throw new AxionWriteException(message);
  }

  /**
   * Converts an AxionWritable into a Tag of type E by simply calling write(String, AxionWritable) with a null String,
   * creating a null name tag.
   *
   * @param writable the AxionWritable to convert
   * @return TagCompound tag
   * @throws AxionWriteException
   */
  public TagCompound createTagFrom(AxionWritable writable) {
    return this.createTagFrom(null, writable);
  }

  /**
   * Converts an AxionWritable into a TagCompound with name.
   *
   * @param name     name of the tag; null is ok
   * @param writable the AxionWritable to convert
   * @return TagCompound tag
   * @throws AxionWriteException
   */
  public TagCompound createTagFrom(String name, AxionWritable writable) {
    TagCompound tagCompound = new TagCompound();
    writable.write(this.defaultWriter(tagCompound));
    tagCompound.setName(name);
    return tagCompound;
  }

  /**
   * Reads a file into the {@link AxionWritable} given.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @param file          the file to read from
   * @param axionWritable the {@link AxionWritable} to write to
   * @return the {@link AxionWritable} written to
   * @throws IOException
   * @throws AxionTagRegistrationException
   */
  public <T extends AxionWritable> T read(final File file, final T axionWritable) throws IOException,
      AxionTagRegistrationException {
    LOG.debug("Entering read(file=[{}], axionWritable=[{}])", file, axionWritable);
    long start = System.currentTimeMillis();
    axionWritable.read(this.defaultReader(read(file)));
    LOG.info("Read of file [{}] into [{}] completed in [{}]", file, axionWritable, DurationUtil.formatDurationWords
        (System.currentTimeMillis() - start));
    LOG.debug("Leaving read(): [{}]", axionWritable);
    return axionWritable;
  }

  /**
   * Writes an {@link AxionWritable} to the {@link File} given.
   *
   * @param axionWritable the {@link AxionWritable} to write to file
   * @param file          file to write to
   * @throws IOException
   */
  public void write(final AxionWritable axionWritable, final File file) throws IOException {
    LOG.debug("Entering write(axionWritable=[{}], file=[{}])", axionWritable, file);
    long start = System.currentTimeMillis();
    TagCompound tagCompound = new TagCompound();
    axionWritable.write(this.defaultWriter(tagCompound));
    write(tagCompound, file);
    LOG.info("Write of [{}] to file [{}] completed in [{}]", axionWritable, file, DurationUtil.formatDurationWords
        (System.currentTimeMillis() - start));
    LOG.debug("Leaving write()");
  }

  /**
   * Reads and returns a {@link TagCompound} from the {@link File} given.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @param file file to read
   * @return a {@link TagCompound}
   * @throws IOException
   */
  public TagCompound read(final File file) throws IOException, AxionTagRegistrationException {
    LOG.debug("Entering read(file=[{}])", file);
    long start = System.currentTimeMillis();
    FileInputStream fileInputStream = new FileInputStream(file);
    TagCompound result = read(fileInputStream);
    fileInputStream.close();
    LOG.info("Read of file [{}] completed in [{}]", file, DurationUtil.formatDurationWords(System.currentTimeMillis()
        - start));
    LOG.debug("Leaving read(): [{}]", result);
    return result;
  }

  /**
   * Writes the {@link TagCompound} given to the {@link File} given.
   *
   * @param tagCompound the tag to write
   * @param file        the file to write to
   * @throws IOException
   */
  public void write(final TagCompound tagCompound, final File file) throws IOException {
    LOG.debug("Entering write(tagCompound=[{}], file=[{}])", tagCompound, file);
    long start = System.currentTimeMillis();
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    write(tagCompound, fileOutputStream);
    fileOutputStream.close();
    LOG.info("Write of tag [{}] to file [{}] completed in [{}]", tagCompound, file, DurationUtil.formatDurationWords
        (System.currentTimeMillis() - start));
    LOG.debug("Leaving write()");
  }

  /**
   * Reads and returns an {@link AxionWritable} from the {@link InputStream} given.
   *
   * @param inputStream   stream to read from
   * @param axionWritable writable to write to
   * @return the {@link AxionWritable} written to
   * @throws IOException
   */
  public <T extends AxionWritable> T read(final InputStream inputStream, final T axionWritable) throws IOException {
    LOG.debug("Entering read(inputStream=[{}], axionWritable=[{}])", inputStream, axionWritable);
    long start = System.currentTimeMillis();
    axionWritable.read(this.defaultReader(read(inputStream)));
    LOG.info("Read into [{}] completed in [{}]", axionWritable, DurationUtil.formatDurationWords(System
        .currentTimeMillis() - start));
    LOG.debug("Leaving read(): [{}]", axionWritable);
    return axionWritable;
  }

  /**
   * Writes the {@link AxionWritable} given to the {@link OutputStream} given.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @param axionWritable {@link AxionWritable} to write
   * @param outputStream  stream to write to
   * @throws IOException
   * @throws AxionTagRegistrationException
   */
  public void write(final AxionWritable axionWritable, final OutputStream outputStream) throws IOException,
      AxionTagRegistrationException {
    LOG.debug("Entering write(axionWritable=[{}], outputStream=[{}])", axionWritable, outputStream);
    long start = System.currentTimeMillis();
    TagCompound tagCompound = new TagCompound();
    axionWritable.write(this.defaultWriter(tagCompound));
    write(tagCompound, outputStream);
    LOG.info("Write completed in [{}]", DurationUtil.formatDurationWords(System.currentTimeMillis() - start));
    LOG.debug("Leaving write()");
  }

  /**
   * Reads and returns a {@link TagCompound} from the {@link InputStream} given.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @param inputStream the stream to read from
   * @return the {@link TagCompound} read
   * @throws IOException
   * @throws AxionTagRegistrationException
   */
  public TagCompound read(final InputStream inputStream) throws IOException, AxionTagRegistrationException {
    LOG.debug("Entering read(inputStream=[{}])", inputStream);
    long start = System.currentTimeMillis();
    Tag result = readTag(null, configuration.wrap(inputStream));
    if (!(result instanceof TagCompound)) {
      LOG.error("Root tag not of type [{}]", TagCompound.class.getSimpleName());
      throw new AxionReadException("Root tag not of type " + TagCompound.class.getSimpleName());
    }
    LOG.info("Read of [{}] completed in [{}]", result, DurationUtil.formatDurationWords(System.currentTimeMillis() -
        start));
    LOG.debug("Leaving read(): [{}]", result);
    return (TagCompound) result;
  }

  /**
   * Writes the {@link TagCompound} given to the {@link OutputStream} given.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @param tagCompound  the tag to write
   * @param outputStream the stream to write to
   * @throws IOException
   * @throws AxionTagRegistrationException
   */
  public void write(final TagCompound tagCompound, final OutputStream outputStream) throws IOException,
      AxionTagRegistrationException {
    LOG.debug("Entering write(tagCompound=[{}], outputStream=[{}])", tagCompound, outputStream);
    long start = System.currentTimeMillis();
    writeTag(tagCompound, configuration.wrap(outputStream));
    LOG.info("Write completed in [{}]", DurationUtil.formatDurationWords(System.currentTimeMillis() - start));
    LOG.debug("Leaving write()");
  }

  /**
   * Reads and returns a {@link Tag} from the {@link AxionInputStream} given.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @param parent the tag requesting the read
   * @param in     the stream to read from
   * @return the {@link Tag} read
   * @throws IOException
   * @throws AxionTagRegistrationException
   */
  protected Tag readTag(final Tag parent, final AxionInputStream in) throws IOException, AxionTagRegistrationException {
    return configuration.getBaseTagAdapter().read(parent, in);
  }

  /**
   * Writes the {@link Tag} given to the {@link AxionOutputStream} given.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   *
   * @param tag the tag to write
   * @param out the stream to write to
   * @throws IOException
   * @throws AxionTagRegistrationException
   */
  protected void writeTag(final Tag tag, final AxionOutputStream out) throws IOException,
      AxionTagRegistrationException {
    configuration.getBaseTagAdapter().write(tag, out);
  }

}
