package com.sudoplay.axion.registry;

import com.sudoplay.axion.tag.Tag;

/**
 * The {@link RegistryAccessor} class holds a reference to a {@link TagRegistry}
 * and allows sub-classes read-only access to the registry.
 * 
 * @author Jason Taylor
 */
public abstract class RegistryAccessor {

  /**
   * Registry reference.
   */
  private TagRegistry registry;

  /**
   * Sets the {@link TagRegistry} reference to the registry given.
   * 
   * @param newTagRegistry
   *          the new {@link TagRegistry}
   */
  protected void setRegistry(final TagRegistry newTagRegistry) {
    registry = newTagRegistry;
  }

  /**
   * Returns the base {@link TagAdapter}.
   * <p>
   * If no base tag adapter has been registered, an exception is thrown.
   * 
   * @return the base {@link TagAdapter}
   * @see TagRegistry#registerBaseTagAdapter(TagAdapter)
   */
  protected TagAdapter<Tag> getBaseTagAdapter() throws AxionTagRegistrationException {
    return registry.getBaseTagAdapter();
  }

  /**
   * Returns the {@link TagAdapter} registered for the given int id.
   * 
   * @param id
   *          the id of the {@link TagAdapter}
   * @return the {@link TagAdapter} registered for the given int id
   */
  protected <A extends Tag> TagAdapter<A> getAdapterFor(final int id) {
    return registry.getAdapterFor(id);
  }

  /**
   * Returns the {@link TagAdapter} registered for the given tag class.
   * <p>
   * If no adapter is found, an exception is thrown.
   * 
   * @param tagClass
   *          the tag class of the {@link TagAdapter}
   * @return the {@link TagAdapter} registered for the given tag class
   * @throws AxionTagRegistrationException
   */
  protected <A extends Tag> TagAdapter<A> getAdapterFor(final Class<A> tagClass) throws AxionTagRegistrationException {
    return registry.getAdapterFor(tagClass);
  }

  /**
   * Converts a value to its {@link Tag} using the {@link TagConverter}
   * registered for the given value.
   * <p>
   * If no converter is found, an exception is thrown.
   * 
   * @param name
   *          the name of the new tag
   * @param value
   *          the value to convert
   * @return a new {@link Tag} converted from the given value
   * @throws AxionTagRegistrationException
   */
  protected <A extends Tag, B> A convertToTag(final String name, final B value) throws AxionTagRegistrationException {
    @SuppressWarnings("unchecked")
    TagConverter<A, B> converter = (TagConverter<A, B>) registry.getConverterForValue(value.getClass());
    return converter.convert(name, value);
  }

  /**
   * Converts a {@link Tag} to its value using the {@link TagConverter}
   * registered for the given {@link Tag}.
   * <p>
   * If no converter is found, an exception is thrown.
   * 
   * @param tag
   *          the {@link Tag} to convert
   * @return a new value converted from the given {@link Tag}
   * @throws AxionTagRegistrationException
   */
  protected <A extends Tag, B> B convertToValue(final A tag) throws AxionTagRegistrationException {
    @SuppressWarnings("unchecked")
    TagConverter<A, B> converter = (TagConverter<A, B>) registry.getConverterForTag(tag.getClass());
    return converter.convert(tag);
  }

  /**
   * Returns the int id registered for the given {@link Tag} class.
   * <p>
   * If no id is found, an exception is thrown.
   * 
   * @param tagClass
   *          the class to get the id for
   * @return the int id
   * @throws AxionTagRegistrationException
   */
  protected Integer getIdFor(final Class<? extends Tag> tagClass) throws AxionTagRegistrationException {
    return registry.getIdFor(tagClass);
  }

  /**
   * Returns the {@link Tag} class for the given int id.
   * <p>
   * If no class is found, an exception is thrown.
   * 
   * @param id
   *          the id to get the class for
   * @return the {@link Tag} class
   * @throws AxionTagRegistrationException
   */
  protected Class<? extends Tag> getClassFor(final int id) throws AxionTagRegistrationException {
    return registry.getClassFor(id);
  }

}
