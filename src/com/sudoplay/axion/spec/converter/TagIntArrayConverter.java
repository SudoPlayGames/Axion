package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagIntArray;

public class TagIntArrayConverter implements TagConverter<TagIntArray, int[]> {

  @Override
  public TagIntArray convert(String name, int[] value) {
    return new TagIntArray(name, value);
  }

  @Override
  public int[] convert(TagIntArray tag) {
    return tag.get();
  }

}
