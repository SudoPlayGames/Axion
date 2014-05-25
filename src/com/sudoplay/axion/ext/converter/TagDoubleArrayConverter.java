package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.ext.tag.TagDoubleArray;

public class TagDoubleArrayConverter implements TagConverter<TagDoubleArray, double[]> {

  @Override
  public double[] convert(final TagDoubleArray tag, final Axion axion) {
    return tag.get();
  }

  @Override
  public TagDoubleArray convert(final String name, final double[] value, final Axion axion) {
    return new TagDoubleArray(name, value);
  }

}
