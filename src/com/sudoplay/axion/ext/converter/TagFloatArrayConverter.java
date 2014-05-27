package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagFloatArray;

public class TagFloatArrayConverter extends TagConverter<TagFloatArray, float[]> {

  @Override
  public float[] convert(final TagFloatArray tag) {
    return tag.get();
  }

  @Override
  public TagFloatArray convert(final String name, final float[] value) {
    return new TagFloatArray(name, value);
  }

}
