package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagBooleanArray;

/**
 * The {@link TagConverter} used to convert to and from a
 * {@link TagBooleanArray}.
 * <p>
 * Part of the extended, custom specification.
 * 
 * @author Jason Taylor
 */
public class TagBooleanArrayConverter extends TagConverter<TagBooleanArray, boolean[]> {

  @Override
  public boolean[] convert(final TagBooleanArray tag) {
    return tag.get();
  }

  @Override
  public TagBooleanArray convert(final String name, final boolean[] value) {
    return new TagBooleanArray(name, value);
  }

}
