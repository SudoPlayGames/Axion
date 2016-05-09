package com.sudoplay.axion.registry;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionInstanceException;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionContract;
import com.sudoplay.axion.util.AxionTypeToken;
import com.sudoplay.axion.util.Primitives;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * The {@link TypeConverterRegistry} class is responsible for maintaining relationships between {@link Tag} classes,
 * {@link Tag} value classes, and {@link TypeConverter}s and providing lookup methods to access the data via its
 * relationships.
 *
 * @author Jason Taylor
 */
public class TypeConverterRegistry implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(TypeConverterRegistry.class);

  private final ThreadLocal<Map<AxionTypeToken<?>, FutureConverter<?, ?>>> calls = new
      ThreadLocal<Map<AxionTypeToken<?>, FutureConverter<?, ?>>>() {
        @Override
        protected Map<AxionTypeToken<?>, FutureConverter<?, ?>> initialValue() {
          return new HashMap<>();
        }
      };

  private final Map<AxionTypeToken<?>, TypeConverter<? extends Tag, ?>> converterCache = Collections.synchronizedMap
      (new HashMap<>());

  /**
   * Maps {@link Tag} classes to their respective {@link TypeConverter}s.
   */
  private final Map<Class<? extends Tag>, TypeConverter<? extends Tag, ?>> tagToConverter = new HashMap<>();

  /**
   * List of {@link TypeConverterFactory} implementations.
   */
  private final List<TypeConverterFactory> factories = new LinkedList<>();

  /**
   * Creates a new, empty {@link TypeConverterRegistry}.
   */
  public TypeConverterRegistry() {
    LOG.debug("Created empty TagConverterRegistry [{}]", this);
  }

  /**
   * Creates a new {@link TypeConverterRegistry} by copying the registry given.
   * <p>
   * All {@link TagAdapter}s and {@link TypeConverter}s are duplicated and assigned a reference to this registry.
   *
   * @param toCopy registry to copy
   * @throws AxionInstanceException
   */
  protected TypeConverterRegistry(
      final Axion axion,
      final TypeConverterRegistry toCopy
  ) throws AxionInstanceException {

    LOG.debug("Entering TagConverterRegistry(toCopy=[{}])", toCopy);
    for (Entry<Class<? extends Tag>, TypeConverter<? extends Tag, ?>> entry : toCopy.tagToConverter.entrySet()) {
      tagToConverter.put(entry.getKey(), entry.getValue().newInstance(axion));
    }
    factories.addAll(
        toCopy.factories
            .stream()
            .map(factory -> factory.newInstance(axion))
            .collect(Collectors.toList())
    );
    LOG.debug("Leaving TagConverterRegistry(): [{}]", this);
  }

  /**
   * Registers a {@link TypeConverterFactory}.
   *
   * @param factory {@link TypeConverterFactory}
   * @throws AxionTagRegistrationException
   */
  public void registerFactory(
      final Axion axion,
      final TypeConverterFactory factory
  ) throws AxionTagRegistrationException, AxionInstanceException {

    LOG.debug("Entering registerFactory(factory=[{}])", factory);
    AxionContract.assertArgumentNotNull(factory, "factory");
    factories.add(factory.newInstance(axion));
    LOG.debug("Leaving registerFactory()");
  }

  /**
   * Registers a {@link TypeConverter} with a tag class, and value class.
   *
   * @param tClass    the class of the {@link Tag}
   * @param vClass    the class of the value represented by the tag
   * @param converter the {@link TypeConverter} for the {@link Tag}
   * @throws AxionTagRegistrationException
   */
  public <T extends Tag, V> void registerTag(
      final Axion axion,
      final Class<T> tClass,
      final Class<V> vClass,
      final TypeConverter<T, V> converter
  ) throws AxionTagRegistrationException, AxionInstanceException {

    LOG.debug("Entering registerTag(tClass=[{}], vClass=[{}], converter=[{}])", tClass, vClass, converter);
    AxionContract.assertArgumentNotNull(tClass, "tClass");
    AxionContract.assertArgumentNotNull(vClass, "vClass");
    AxionContract.assertArgumentNotNull(converter, "converter");
    converter.setAxion(axion);
    tagToConverter.put(tClass, converter);
    AxionTypeToken<V> valueTypeToken = AxionTypeToken.get(vClass);
    TypeConverterFactory factory = TypeConverterFactory.newFactory(valueTypeToken, converter);
    factories.add(factory);
    LOG.debug("Leaving registerTag()");
  }

  /**
   * Returns the {@link TypeConverter} registered to handle the {@link Tag} class given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param tagClass the class of the tag to get the converter for
   * @return the {@link TypeConverter} registered to handle the tag class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TypeConverter<T, V> getConverterForTag(
      final Class<T> tagClass
  ) throws AxionTagRegistrationException {

    LOG.trace("Entering getConverter(tagClass=[{}])", tagClass);
    if (tagClass == null) {
      return null;
    } else if (!tagToConverter.containsKey(tagClass)) {
      throw new AxionTagRegistrationException("No converter registered for tag class: " + tagClass);
    }
    TypeConverter<T, V> result = (TypeConverter<T, V>) tagToConverter.get(tagClass);
    LOG.trace("Leaving getConverter(): [{}]", result);
    return result;
  }

  /**
   * Returns a {@link TypeConverter} capable of handling the {@link AxionTypeToken} given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param typeToken the {@link AxionTypeToken} of the value
   * @return the {@link TypeConverter} registered to handle the value class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TypeConverter<T, V> getConverter(
      final Axion axion,
      final AxionTypeToken<V> typeToken
  ) throws AxionTagRegistrationException {
    LOG.trace("Entering getConverter(typeToken=[{}])", typeToken);

    AxionTypeToken<V> newTypeToken = typeToken;
    if (Primitives.isPrimitive(typeToken.getType())) {
      Class<V> c = (Class<V>) typeToken.getRawType();
      newTypeToken = AxionTypeToken.get(Primitives.wrap(c));
    }

    TypeConverter<?, ?> cachedConverter = converterCache.get(newTypeToken);
    if (cachedConverter != null) {
      LOG.trace(
          "Leaving getConverter(): found cached converter -> {}, for type -> {}",
          cachedConverter,
          newTypeToken
      );
      return (TypeConverter<T, V>) cachedConverter;
    }
    LOG.trace("Converter for type [{}] not found in cache", newTypeToken);

    Map<AxionTypeToken<?>, FutureConverter<? extends Tag, ?>> threadCalls = calls.get();
    FutureConverter<T, V> ongoingCall = (FutureConverter<T, V>) threadCalls.get(newTypeToken);
    if (ongoingCall != null) {
      LOG.trace("Leaving getConverter(): ongoingCall for type -> {}", newTypeToken);
      return ongoingCall;
    }

    FutureConverter<T, V> call = new FutureConverter<>();
    threadCalls.put(newTypeToken, call);
    try {
      for (TypeConverterFactory factory : factories) {
        TypeConverter<T, V> candidate = factory.create(axion, newTypeToken);
        if (candidate != null) {
          call.setDelegate(candidate);
          converterCache.put(newTypeToken, candidate);
          LOG.trace("Leaving getConverter(): found new converter -> {}, for type -> {}", candidate, newTypeToken);
          return candidate;
        }
      }
      throw new AxionTagRegistrationException("No converter found for type: " + newTypeToken);
    } finally {
      threadCalls.remove(newTypeToken);
    }
  }

  /**
   * Returns true if the given {@link AxionTypeToken} has a converter registered.
   *
   * @param typeToken typeToken
   * @return true if the given {@link AxionTypeToken} has a converter registered
   */
  public boolean hasConverter(
      Axion axion,
      AxionTypeToken<?> typeToken
  ) {
    if (converterCache.containsKey(typeToken)) {
      return true;
    } else {
      try {
        getConverter(axion, typeToken);
        return true;
      } catch (AxionTagRegistrationException e) {
        return false;
      }
    }
  }

  /**
   * Returns true if the given class has a converter registered.
   *
   * @param tagClass class
   * @return true if the given class has a converter registered
   */
  public boolean hasConverterForTag(Class<? extends Tag> tagClass) {
    return tagToConverter.containsKey(tagClass);
  }

  public TypeConverterRegistry copy(Axion axion) {
    LOG.debug("Entering copy()");
    TypeConverterRegistry copy = new TypeConverterRegistry(axion, this);
    LOG.debug("Leaving copy(): [{}]", copy);
    return copy;
  }

  static class FutureConverter<T extends Tag, V> extends TypeConverter<T, V> {

    private TypeConverter<T, V> delegate;

    public void setDelegate(TypeConverter<T, V> typeConverter) {
      AxionContract.assertState(delegate == null, "delegate is already set");
      delegate = typeConverter;
    }

    @Override
    public V convert(T tag) {
      AxionContract.assertState(delegate != null, "delegate is not set");
      return delegate.convert(tag);
    }

    @Override
    public T convert(String name, V value) {
      AxionContract.assertState(delegate != null, "delegate is not set");
      return delegate.convert(name, value);
    }
  }

}
