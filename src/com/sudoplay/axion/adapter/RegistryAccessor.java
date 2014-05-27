package com.sudoplay.axion.adapter;

import com.sudoplay.axion.tag.Tag;

public abstract class RegistryAccessor {

  private TagRegistry registry;

  protected void setRegistry(final TagRegistry newTagRegistry) {
    registry = newTagRegistry;
  }

  protected TagAdapter<Tag> getBaseTagAdapter() {
    return registry.getBaseTagAdapter();
  }

  protected <A extends Tag> TagAdapter<A> getAdapterFor(final int id) {
    return registry.getAdapterFor(id);
  }

  protected <A extends Tag> TagAdapter<A> getAdapterFor(final Class<A> tagClass) {
    return registry.getAdapterFor(tagClass);
  }

  protected <A extends Tag, B> A convertToTag(final String name, final B value) {
    @SuppressWarnings("unchecked")
    TagConverter<A, B> converter = (TagConverter<A, B>) registry.getConverterForValue(value.getClass());
    return converter.convert(name, value);
  }

  protected <A extends Tag, B> B convertToValue(final A tag) {
    @SuppressWarnings("unchecked")
    TagConverter<A, B> converter = (TagConverter<A, B>) registry.getConverterForTag(tag.getClass());
    return converter.convert(tag);
  }

  protected Integer getIdFor(final Class<? extends Tag> tagClass) throws AxionTagRegistrationException {
    return registry.getIdFor(tagClass);
  }

  protected Class<? extends Tag> getClassFor(final int id) throws AxionTagRegistrationException {
    return registry.getClassFor(id);
  }

}
