package com.sudoplay.axion.converter;

import com.sudoplay.axion.spec.tag.Tag;

public interface TagConverter<T extends Tag, V> {

  public V convert(T tag);

  public T convert(String name, V value);

}
