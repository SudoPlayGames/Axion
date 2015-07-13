package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.spec.tag.TagIntArray;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagIntArray}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagIntArrayConverter extends TagConverter<TagIntArray, int[]> {

  @Override
  public TagIntArray convert(final String name, final int[] value) {
    return new TagIntArray(name, value);
  }

  @Override
  public int[] convert(final TagIntArray tag) {
    return tag.get();
  }

}
