package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.ext.tag.TagDoubleArray;

public class TagDoubleArrayConverter extends TagConverter<TagDoubleArray, double[]> {

  @Override
  public double[] convert(final TagDoubleArray tag) {
    return tag.get();
  }

  @Override
  public TagDoubleArray convert(final String name, final double[] value) {
    return new TagDoubleArray(name, value);
  }

}
