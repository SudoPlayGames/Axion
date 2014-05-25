package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagFloatArray;

public class TagFloatArrayConverter implements TagConverter<TagFloatArray, float[]> {

  @Override
  public float[] convert(TagFloatArray tag, Axion axion) {
    return tag.get();
  }

  @Override
  public TagFloatArray convert(String name, float[] value, Axion axion) {
    return new TagFloatArray(name, value);
  }

}
