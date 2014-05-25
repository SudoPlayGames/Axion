package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagIntArray;

public class TagIntArrayConverter implements TagConverter<TagIntArray, int[]> {

  @Override
  public TagIntArray convert(final String name, final int[] value, final Axion axion) {
    return new TagIntArray(name, value);
  }

  @Override
  public int[] convert(final TagIntArray tag, final Axion axion) {
    return tag.get();
  }

}
