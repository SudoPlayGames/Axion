package com.sudoplay.axion.registry;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionInstanceException;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionContract;
import com.sudoplay.axion.util.AxionTypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * The {@link TagConverterRegistry} class is responsible for maintaining relationships between {@link Tag} classes,
 * {@link Tag} value classes, and {@link TagConverter}s and providing lookup methods to access the data via its
 * relationships.
 *
 * @author Jason Taylor
 */
public class TagConverterRegistry implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(TagConverterRegistry.class);

  private final ThreadLocal<Map<AxionTypeToken<?>, FutureConverter<?, ?>>> calls = new
      ThreadLocal<Map<AxionTypeToken<?>, FutureConverter<?, ?>>>() {
        @Override
        protected Map<AxionTypeToken<?>, FutureConverter<?, ?>> initialValue() {
          return new HashMap<>();
        }
      };

  private final Map<AxionTypeToken<?>, TagConverter<? extends Tag, ?>> converterCache = Collections.synchronizedMap
      (new HashMap<>());

  /**
   * Maps {@link Tag} classes to their respective {@link TagConverter}s.
   */
  private final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> tagToConverter = new HashMap<>();

  /**
   * List of {@link TagConverterFactory} implementations.
   */
  private final List<TagConverterFactory> factories = new LinkedList<>();

  /**
   * Creates a new, empty {@link TagConverterRegistry}.
   */
  public TagConverterRegistry() {
    LOG.debug("Created empty TagConverterRegistry [{}]", this);
  }

  /**
   * Creates a new {@link TagConverterRegistry} by copying the registry given.
   * <p>
   * All {@link TagAdapter}s and {@link TagConverter}s are duplicated and assigned a reference to this registry.
   *
   * @param toCopy registry to copy
   * @throws AxionInstanceException
   */
  protected TagConverterRegistry(
      final Axion axion,
      final TagConverterRegistry toCopy
  ) throws AxionInstanceException {

    LOG.debug("Entering TagConverterRegistry(toCopy=[{}])", toCopy);
    for (Entry<Class<? extends Tag>, TagConverter<? extends Tag, ?>> entry : toCopy.tagToConverter.entrySet()) {
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
   * Registers a {@link TagConverterFactory}.
   *
   * @param factory {@link TagConverterFactory}
   * @throws AxionTagRegistrationException
   */
  public void registerFactory(
      final Axion axion,
      final TagConverterFactory factory
  ) throws AxionTagRegistrationException, AxionInstanceException {

    LOG.debug("Entering registerFactory(factory=[{}])", factory);
    AxionContract.assertArgumentNotNull(factory, "factory");
    factories.add(factory.newInstance(axion));
    LOG.debug("Leaving registerFactory()");
  }

  /**
   * Registers a {@link TagConverter} with a tag class, and value class.
   *
   * @param tClass    the class of the {@link Tag}
   * @param vClass    the class of the value represented by the tag
   * @param converter the {@link TagConverter} for the {@link Tag}
   * @throws AxionTagRegistrationException
   */
  public <T extends Tag, V> void registerTag(
      final Class<T> tClass,
      final Class<V> vClass,
      final TagConverter<T, V> converter
  ) throws AxionTagRegistrationException, AxionInstanceException {

    LOG.debug("Entering registerTag(tClass=[{}], vClass=[{}], converter=[{}])", tClass, vClass, converter);
    AxionContract.assertArgumentNotNull(tClass, "tClass");
    AxionContract.assertArgumentNotNull(vClass, "vClass");
    AxionContract.assertArgumentNotNull(converter, "converter");
    tagToConverter.put(tClass, converter);
    AxionTypeToken<V> valueTypeToken = AxionTypeToken.get(vClass);
    TagConverterFactory factory = TagConverterFactory.newFactory(valueTypeToken, converter);
    factories.add(factory);
    LOG.debug("Leaving registerTag()");
  }

  /**
   * Returns the {@link TagConverter} registered to handle the {@link Tag} class given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param tagClass the class of the tag to get the converter for
   * @return the {@link TagConverter} registered to handle the tag class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TagConverter<T, V> getConverterForTag(
      final Class<T> tagClass
  ) throws AxionTagRegistrationException {

    LOG.trace("Entering getConverterForTag(tagClass=[{}])", tagClass);
    if (tagClass == null) {
      return null;
    } else if (!tagToConverter.containsKey(tagClass)) {
      throw new AxionTagRegistrationException("No converter registered for tag class: " + tagClass);
    }
    TagConverter<T, V> result = (TagConverter<T, V>) tagToConverter.get(tagClass);
    LOG.trace("Leaving getConverterForTag(): [{}]", result);
    return result;
  }

  /**
   * Returns a {@link TagConverter} capable of handling the {@link AxionTypeToken} given.
   * <p>
   * If no converter is found, an exception is thrown.
   *
   * @param typeToken the {@link AxionTypeToken} of the value
   * @return the {@link TagConverter} registered to handle the value class given
   * @throws AxionTagRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> TagConverter<T, V> getConverterForValue(
      final Axion axion,
      final AxionTypeToken<V> typeToken
  ) throws AxionTagRegistrationException {
    LOG.trace("Entering getConverterForValue(typeToken=[{}])", typeToken);

    TagConverter<?, ?> cachedConverter = converterCache.get(typeToken);
    if (cachedConverter != null) {
      LOG.trace("Leaving getConverterForValue(): found cached converter -> {}, for type -> {}", cachedConverter,
          typeToken);
      return (TagConverter<T, V>) cachedConverter;
    }
    LOG.trace("Converter for type [{}] not found in cache", typeToken);

    Map<AxionTypeToken<?>, FutureConverter<? extends Tag, ?>> threadCalls = calls.get();
    FutureConverter<T, V> ongoingCall = (FutureConverter<T, V>) threadCalls.get(typeToken);
    if (ongoingCall != null) {
      LOG.trace("Leaving getConverterForValue(): ongoingCall for type -> {}", typeToken);
      return ongoingCall;
    }

    FutureConverter<T, V> call = new FutureConverter<>();
    threadCalls.put(typeToken, call);
    try {
      for (TagConverterFactory factory : factories) {
        TagConverter<T, V> candidate = factory.create(axion, typeToken);
        if (candidate != null) {
          call.setDelegate(candidate);
          converterCache.put(typeToken, candidate);
          LOG.trace("Leaving getConverterForValue(): found new converter -> {}, for type -> {}", candidate, typeToken);
          return candidate;
        }
      }
      throw new AxionTagRegistrationException("No converter found for type: " + typeToken);
    } finally {
      threadCalls.remove(typeToken);
    }
  }

  /**
   * Returns true if the given {@link AxionTypeToken} has a converter registered.
   *
   * @param typeToken typeToken
   * @return true if the given {@link AxionTypeToken} has a converter registered
   */
  public boolean hasConverterForValue(
      Axion axion,
      AxionTypeToken<?> typeToken
  ) {
    if (converterCache.containsKey(typeToken)) {
      return true;
    } else {
      try {
        getConverterForValue(axion, typeToken);
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

  public TagConverterRegistry copy(Axion axion) {
    LOG.debug("Entering copy()");
    TagConverterRegistry copy = new TagConverterRegistry(axion, this);
    LOG.debug("Leaving copy(): [{}]", copy);
    return copy;
  }

  static class FutureConverter<T extends Tag, V> extends TagConverter<T, V> {

    private TagConverter<T, V> delegate;

    public void setDelegate(TagConverter<T, V> tagConverter) {
      AxionContract.assertState(delegate == null, "delegate is already set");
      delegate = tagConverter;
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
