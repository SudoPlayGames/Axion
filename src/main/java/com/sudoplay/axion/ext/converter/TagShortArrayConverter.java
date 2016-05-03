package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.registry.TypeConverter;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagShortArray}.
 * <p>
 * Part of the extended, custom specification.
 *
 * @author Jason Taylor
 */
public class TagShortArrayConverter extends TypeConverter<TagShortArray, short[]> {

  @Override
  public short[] convert(final TagShortArray tag) {
    return tag.get();
  }

  @Override
  public TagShortArray convert(final String name, final short[] value) {
    return new TagShortArray(name, value);
  }

}
