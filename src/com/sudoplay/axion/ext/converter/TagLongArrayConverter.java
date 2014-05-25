package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagLongArray;

public class TagLongArrayConverter implements TagConverter<TagLongArray, long[]> {

  @Override
  public long[] convert(TagLongArray tag, Axion axion) {
    return tag.get();
  }

  @Override
  public TagLongArray convert(String name, long[] value, Axion axion) {
    return new TagLongArray(name, value);
  }

}
