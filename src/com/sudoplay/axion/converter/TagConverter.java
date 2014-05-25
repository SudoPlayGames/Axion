package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.Tag;

public interface TagConverter<T extends Tag, V> {

  public V convert(final T tag, final Axion axion);

  public T convert(final String name, final V value, final Axion axion);

}
