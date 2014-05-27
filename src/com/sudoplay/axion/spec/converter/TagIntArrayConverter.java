package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagIntArray;

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
