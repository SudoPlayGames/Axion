package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagLongArray;

public class TagLongArrayConverter extends TagConverter<TagLongArray, long[]> {

  @Override
  public long[] convert(final TagLongArray tag) {
    return tag.get();
  }

  @Override
  public TagLongArray convert(final String name, final long[] value) {
    return new TagLongArray(name, value);
  }

}
