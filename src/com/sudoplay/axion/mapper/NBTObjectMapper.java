package com.sudoplay.axion.mapper;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;

public interface NBTObjectMapper<T extends Tag, O> {

  public T createTagFrom(final String name, final O object, final Axion axion);

  public O createObjectFrom(final T tag, final Axion axion);

}
