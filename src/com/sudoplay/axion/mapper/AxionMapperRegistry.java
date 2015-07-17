package com.sudoplay.axion.mapper;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionContract;
import com.sudoplay.axion.util.AxionTypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The {@link AxionMapperRegistry} is responsible for storing the object type to {@link AxionMapper} relationship and
 * providing a lookup method to retrieve the mapper from its type.
 *
 * @author Jason Taylor
 */
public class AxionMapperRegistry implements Cloneable {

  private static final Logger LOG = LoggerFactory.getLogger(AxionMapperRegistry.class);

  private final ThreadLocal<Map<AxionTypeToken<?>, FutureMapper<?, ?>>> calls = new ThreadLocal<Map<AxionTypeToken<?>, FutureMapper<?, ?>>>() {
    @Override
    protected Map<AxionTypeToken<?>, FutureMapper<?, ?>> initialValue() {
      return new HashMap<>();
    }
  };

  private final Map<AxionTypeToken<?>, AxionMapper<? extends Tag, ?>> mapperCache = Collections.synchronizedMap(new
      HashMap<>());

  private List<AxionMapperFactory> factories = new LinkedList<>();

  private Set<AxionTypeToken<?>> checkedTypes = new HashSet<>();

  /**
   * Creates a new {@link AxionMapperRegistry}.
   */
  public AxionMapperRegistry() {
    LOG.debug("Created empty NBTObjectMapperRegistry [{}]", this);
  }

  /**
   * Creates a new {@link AxionMapper} by duplicating the registry given.
   * <p>
   * Note that this duplicates the backing map, but doesn't actually duplicate the factories.
   *
   * @param toCopy registry to copy
   */
  protected AxionMapperRegistry(final AxionMapperRegistry toCopy) {
    LOG.debug("Entering NBTObjectMapperRegistry(toCopy=[{}])", toCopy);
    factories.addAll(toCopy.factories);
    LOG.debug("Leaving NBTObjectMapperRegistry(): [{}]", this);
  }

  /**
   * Register a new {@link AxionMapperFactory} for the type given.
   * <p>
   * The axionMapperFactory parameter can't be null.
   *
   * @param axionMapperFactory the {@link AxionMapperFactory} to register
   * @throws AxionMapperRegistrationException
   */
  public void register(final AxionMapperFactory axionMapperFactory) throws AxionMapperRegistrationException {
    LOG.debug("Entering register(axionMapperFactory=[{}])", axionMapperFactory);
    AxionContract.assertArgumentNotNull(axionMapperFactory, "axionMapperFactory");
    factories.add(axionMapperFactory);
    LOG.debug("Leaving register()");
  }

  /**
   * Returns the {@link AxionMapper} registered for the type given.
   * <p>
   * If no mapper is found, an exception is thrown.
   *
   * @param type the type to get the {@link AxionMapper} for
   * @return the {@link AxionMapper} registered for the type given
   * @throws AxionMapperRegistrationException
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag, V> AxionMapper<T, V> getMapperFor(
      final AxionTypeToken<V> type,
      final Axion axion
  ) throws AxionMapperRegistrationException {
    LOG.debug("Entering getMapperFor(type=[{}])", type);

    AxionMapper<?, ?> cached = mapperCache.get(type);
    if (cached != null) {
      LOG.debug("Leaving getMapperFor(): {}", cached);
      return (AxionMapper<T, V>) cached;
    }

    Map<AxionTypeToken<?>, FutureMapper<?, ?>> threadCalls = calls.get();
    FutureMapper<T, V> ongoingCall = (FutureMapper<T, V>) threadCalls.get(type);
    if (ongoingCall != null) {
      LOG.debug("Leaving getMapperFor(): {}", ongoingCall);
      return ongoingCall;
    }

    FutureMapper<T, V> call = new FutureMapper<>();
    threadCalls.put(type, call);
    try {
      for (AxionMapperFactory factory : factories) {
        AxionMapper<T, V> candidate = factory.create(axion, type);
        if (candidate != null) {
          call.setDelegate(candidate);
          mapperCache.put(type, candidate);
          LOG.debug("Leaving getMapperFor(): {}", candidate);
          return candidate;
        }
      }
      throw new AxionMapperRegistrationException("No mapper found for type: " + type);
    } finally {
      threadCalls.remove(type);
    }
  }

  /**
   * Duplicates this {@link AxionMapperRegistry} by using a copy constructor.
   *
   * @see #AxionMapperRegistry(AxionMapperRegistry)
   */
  @SuppressWarnings("CloneDoesntCallSuperClone")
  @Override
  public AxionMapperRegistry clone() {
    LOG.debug("Entering clone()");
    AxionMapperRegistry clone = new AxionMapperRegistry(this);
    LOG.debug("Leaving clone(): [{}]", clone);
    return clone;
  }

  /**
   * Returns true if the registry has a mapper for the given class.
   *
   * @param type class
   * @return true if the registry has a mapper for the given class
   */
  public boolean hasMapperFor(AxionTypeToken<?> type, Axion axion) {
    if (mapperCache.containsKey(type)) {
      return true;
    } else if (checkedTypes.contains(type)) {
      return false;
    } else {
      checkedTypes.add(type);
      try {
        getMapperFor(type, axion);
        return true;
      } catch (AxionMapperRegistrationException e) {
        return false;
      }
    }
  }

  static class FutureMapper<T extends Tag, V> implements AxionMapper<T, V> {

    private AxionMapper<T, V> delegate;

    public void setDelegate(AxionMapper<T, V> mapper) {
      if (delegate != null) {
        throw new AssertionError();
      }
      delegate = mapper;
    }

    @Override
    public T createTagFrom(String name, V object, Axion axion) {
      AxionContract.assertState(delegate != null, "delegate is not set");
      return delegate.createTagFrom(name, object, axion);
    }

    @Override
    public V createObjectFrom(T tag, Axion axion) {
      AxionContract.assertState(delegate != null, "delegate is not set");
      return delegate.createObjectFrom(tag, axion);
    }
  }
}
