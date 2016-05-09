package com.sudoplay.axion.ext.converter;

import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.registry.TypeConverter;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagDoubleArray} .
 * <p>
 * Part of the extended, custom specification.
 *
 * @author Jason Taylor
 */
public class TagDoubleArrayConverter extends TypeConverter<TagDoubleArray, double[]> {

  @Override
  public double[] convert(final TagDoubleArray tag) {
    return tag.get();
  }

  @Override
  public TagDoubleArray convert(final String name, final double[] value) {
    return new TagDoubleArray(name, value);
  }

}
