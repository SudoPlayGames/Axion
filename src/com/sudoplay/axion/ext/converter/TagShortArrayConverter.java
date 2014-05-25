package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagShortArray;

public class TagShortArrayConverter implements TagConverter<TagShortArray, short[]> {

  @Override
  public short[] convert(TagShortArray tag, Axion axion) {
    return tag.get();
  }

  @Override
  public TagShortArray convert(String name, short[] value, Axion axion) {
    return new TagShortArray(name, value);
  }

}
