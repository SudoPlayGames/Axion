package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagBooleanArray;

public class TagBooleanArrayConverter implements TagConverter<TagBooleanArray, boolean[]> {

  @Override
  public boolean[] convert(TagBooleanArray tag, Axion axion) {
    return tag.get();
  }

  @Override
  public TagBooleanArray convert(String name, boolean[] value, Axion axion) {
    return new TagBooleanArray(name, value);
  }

}
