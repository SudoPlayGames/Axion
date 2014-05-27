package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagShortArray;

public class TagShortArrayConverter extends TagConverter<TagShortArray, short[]> {

  @Override
  public short[] convert(final TagShortArray tag) {
    return tag.get();
  }

  @Override
  public TagShortArray convert(final String name, final short[] value) {
    return new TagShortArray(name, value);
  }

}
