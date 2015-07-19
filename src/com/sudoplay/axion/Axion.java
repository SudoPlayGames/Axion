package com.sudoplay.axion;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
import com.sudoplay.axion.AxionConfiguration.CompressionType;
import com.sudoplay.axion.AxionConfigurationProtection.ProtectionMode;
import com.sudoplay.axion.api.AxionReader;
import com.sudoplay.axion.api.AxionWritable;
import com.sudoplay.axion.api.AxionWriter;
import com.sudoplay.axion.api.impl.DefaultAxionReader;
import com.sudoplay.axion.api.impl.DefaultAxionWriter;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.registry.TypeConverterFactory;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.system.InstanceCreator;
import com.sudoplay.axion.system.ObjectConstructor;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;
import com.sudoplay.axion.util.DurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

  @SuppressWarnings("serial")
  private static final Map<String, Axion> INSTANCES = new HashMap<>();

  /**
   * Reference to this instances {@link AxionConfiguration}
   */
  private AxionConfiguration configuration;

  /**
   * Creates a new instance of {@link Axion} with an empty, unlocked configuration.
   */
  private Axion() {
    configuration = new AxionConfiguration(ProtectionMode.Unlocked);
  }

  private Axion(final AxionConfiguration configuration) {
    this.configuration = configuration.copy(this);
  }

  /**
   * Creates a new instance of {@link Axion}.
   *
   * @param toCopy the {@link Axion} to copy
   */
  private Axion(final Axion toCopy) {
    configuration = toCopy.configuration.copy(this);
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
      throw new AxionInstanceException("Instance already exists with name: " + newName);
    }
    Axion instance = new Axion(axion);
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
    Axion axion = INSTANCES.get(EXT_INSTANCE_NAME);
    if (axion == null) {
      axion = new Axion();
      axion.configuration = AxionConfiguration.getExtConfiguration(axion);
      INSTANCES.put(EXT_INSTANCE_NAME, axion);
    }
    return axion;
  }

  /**
   * Returns the built-in, strict specification Axion instance configuration.
   *
   * @return the strict specification Axion instance
   */
  @SuppressWarnings("unused")
  public static Axion getSpecInstance() {
    Axion axion = INSTANCES.get(SPEC_INSTANCE_NAME);
    if (axion == null) {
      axion = new Axion();
      axion.configuration = AxionConfiguration.getSpecConfiguration(axion);
      INSTANCES.put(SPEC_INSTANCE_NAME, axion);
    }
    return axion;
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

  public AxionWriter newWriter() {
    return new DefaultAxionWriter(this);
  }

  public AxionWriter newWriter(TagCompound tagCompound) {
    return new DefaultAxionWriter(tagCompound, this);
  }

  public AxionReader newReader(TagCompound tagCompound) {
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
   * Register an instance creator.
   *
   * @param vClass          class
   * @param instanceCreator instanceCreator
   * @param <V>             value type
   * @return this {@link Axion} instance
   */
  public <V> Axion registerInstanceCreator(Class<V> vClass, InstanceCreator<V> instanceCreator) {
    configuration.getConstructorConstructor().registerInstanceCreator(vClass, instanceCreator);
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
  public Axion registerBaseTagAdapter(
      final TagAdapter<Tag> newBaseTagAdapter
  ) throws AxionConfigurationException, AxionInstanceException {
    configuration.registerBaseTagAdapter(this, newBaseTagAdapter);
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
   * @param converter {@link TypeConverter} for the tag
   * @return this {@link Axion} instance
   * @throws AxionTagRegistrationException
   * @throws AxionInstanceException
   * @see #getAdapterFor(Class)
   * @see #getAdapterFor(int)
   * @see #getClassFor(int)
   * @see #getConverterForTag(Class)
   * @see #getConverterForValue(AxionTypeToken)
   * @see #getIdFor(Class)
   */
  @SuppressWarnings("unused")
  public <T extends Tag, V> Axion registerTag(
      final int id,
      final Class<T> tagClass,
      final Class<V> type,
      final TagAdapter<T> adapter,
      final TypeConverter<T, V> converter
  ) throws AxionTagRegistrationException, AxionInstanceException {
    configuration.registerTag(this, id, tagClass, type, adapter, converter);
    return this;
  }

  /**
   * Register a converter.
   *
   * @param vClass    class
   * @param converter converter
   * @param <T>       tag type
   * @param <V>       value type
   * @return this {@link Axion} instance
   */
  public <T extends Tag, V> Axion registerConverter(
      Class<V> vClass,
      TypeConverter<T, V> converter
  ) {
    configuration.registerFactory(this, TypeConverterFactory.newFactory(AxionTypeToken.get(vClass), converter));
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
  public Axion setCompressionType(
      final CompressionType newCompressionType
  ) {
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
  public TagAdapter<Tag> getBaseTagAdapter() throws AxionTagRegistrationException {
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
  public Class<? extends Tag> getClassFor(
      final int id
  ) throws AxionTagRegistrationException {
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
  public <T extends Tag> TagAdapter<T> getAdapterFor(
      final int id
  ) throws AxionTagRegistrationException {
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
  public <T extends Tag> TagAdapter<T> getAdapterFor(
      final Class<T> tagClass
  ) throws AxionTagRegistrationException {
    return configuration.getAdapterFor(tagClass);
  }

  /**
   * Returns the registered {@link TypeConverter} for the tag class given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param tClass tag class
   * @return the registered {@link TypeConverter} for the tag given
   * @throws AxionTagRegistrationException
   */
  public <T extends Tag, V> TypeConverter<T, V> getConverterForTag(
      final Class<T> tClass
  ) throws AxionTagRegistrationException {
    return configuration.getConverterForTag(tClass);
  }

  public <T extends Tag, V> TypeConverter<T, V> getConverterForValue(
      final Class<V> vClass
  ) throws AxionTagRegistrationException {
    return configuration.getConverterForValue(this, AxionTypeToken.get(vClass));
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TypeConverter<T, V> getConverterFor(
      final Class<?> aClass
  ) {
    if (Tag.class.isAssignableFrom(aClass)) {
      return configuration.getConverterForTag((Class<T>) aClass);
    } else {
      return configuration.getConverterForValue(this, AxionTypeToken.get((Class<V>) aClass));
    }
  }

  /**
   * Returns the registered {@link TypeConverter} for the value given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param typeToken typeToken
   * @return the registered {@link TypeConverter} for the type given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unused")
  public <T extends Tag, V> TypeConverter<T, V> getConverterForValue(
      final AxionTypeToken<V> typeToken
  ) throws AxionTagRegistrationException {
    return configuration.getConverterForValue(this, typeToken);
  }

  /**
   * Returns true if the given value's class has a converter registered.
   *
   * @param typeToken typeToken
   * @param <V>       value type
   * @return true if the given value's class has a converter registered
   */
  public <V> boolean hasConverterForValue(final AxionTypeToken<V> typeToken) {
    return configuration.hasConverterForValue(this, typeToken);
  }

  /**
   * Returns true if the given tag's class has a converter registered.
   *
   * @param tag tag
   * @param <T> tag type
   * @return true if the given tag's class has a converter registered
   */
  public <T extends Tag> boolean hasConverterForTag(final T tag) {
    return configuration.hasConverterForTag(tag);
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
    axionWritable.read(this.newReader(read(file)));
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
    axionWritable.write(this.newWriter(tagCompound));
    write(tagCompound, file);
    LOG.info(
        "Write of [{}] to file [{}] completed in [{}]",
        axionWritable, file, DurationUtil.formatDurationWords(System.currentTimeMillis() - start)
    );
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
    axionWritable.read(this.newReader(read(inputStream)));
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
    axionWritable.write(this.newWriter(tagCompound));
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
    Tag result = adapt(null, configuration.wrap(inputStream));
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
    adapt(tagCompound, configuration.wrap(outputStream));
    LOG.info("Write completed in [{}]", DurationUtil.formatDurationWords(System.currentTimeMillis() - start));
    LOG.debug("Leaving write()");
  }

  /**
   * Returns an {@link ObjectConstructor} for the given type.
   *
   * @param typeToken typeToken
   * @param <V>       value type
   * @return an {@link ObjectConstructor} for the given type
   */
  public <V> ObjectConstructor<V> getConstructorConstructor(AxionTypeToken<V> typeToken) {
    return configuration.getConstructorConstructor().get(typeToken);
  }

  /**
   * Attempts to create an instance of V via its {@link AxionTypeToken}.
   *
   * @param typeToken typeToken
   * @param <V>       value type
   * @return new instance of V
   */
  public <V> V create(AxionTypeToken<V> typeToken) {
    return configuration.getConstructorConstructor().get(typeToken).construct();
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
  public Tag adapt(
      final Tag parent,
      final AxionInputStream in
  ) throws IOException, AxionTagRegistrationException {
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
  public void adapt(
      final Tag tag,
      final AxionOutputStream out
  ) throws IOException, AxionTagRegistrationException {
    configuration.getBaseTagAdapter().write(tag, out);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, V> T convertValue(
      final V value
  ) {
    return this._convertValue((String) null, value);
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag, V> T convertValue(
      final String name,
      final V value
  ) {
    return this._convertValue(name, value);
  }

  /**
   * Converts a value to a tag.
   *
   * @param name  tag name; can be null
   * @param value the value to convert
   * @param <T>   tag type
   * @param <V>   value type
   * @return T tag
   */
  @SuppressWarnings("unchecked")
  private <T extends Tag, V> T _convertValue(
      final String name,
      final V value
  ) {
    Class<V> vClass = (Class<V>) value.getClass();
    AxionTypeToken<V> typeToken = AxionTypeToken.get(vClass);
    TypeConverter<T, V> typeConverter = configuration.getConverterForValue(this, typeToken);
    return typeConverter.convert(name, value);
  }

  /**
   * Converts a registered tag to its value.
   *
   * @param tag tag
   * @param <T> tag type
   * @param <V> value type
   * @return tag's value
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> V convertTag(
      final T tag
  ) {
    Class<T> tClass = (Class<T>) tag.getClass();
    TypeConverter<T, V> typeConverter = configuration.getConverterForTag(tClass);
    return typeConverter.convert(tag);
  }

  public <T extends Tag, V> V convertTag(
      final T tag,
      final Class<V> vClass
  ) {
    TypeConverter<T, V> typeConverter = configuration.getConverterForValue(this, AxionTypeToken.get(vClass));
    return typeConverter.convert(tag);
  }

  /**
   * Converts a tag to a value with the given type.
   *
   * @param tag       the tag
   * @param typeToken typeToken
   * @param <T>       tag type
   * @param <V>       value type
   * @return V value
   */
  public <T extends Tag, V> V convertTag(
      final T tag,
      final AxionTypeToken<V> typeToken
  ) {
    TypeConverter<T, V> typeConverter = configuration.getConverterForValue(this, typeToken);
    return typeConverter.convert(tag);
  }

}
