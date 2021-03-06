package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagByteArray;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagByteArray}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagByteArrayConverter extends TypeConverter<TagByteArray, byte[]> {

  @Override
  public TagByteArray convert(final String name, final byte[] value) {
    return new TagByteArray(name, value);
  }

  @Override
  public byte[] convert(final TagByteArray tag) {
    return tag.get();
  }

}
