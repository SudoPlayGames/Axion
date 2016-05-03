package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.registry.TypeConverterFactory;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.util.AxionTypeToken;

/**
 * The {@link TypeConverter} used to convert to and from a {@link TagByte}.
 * <p>
 * Part of the original specification.
 *
 * @author Jason Taylor
 */
public class TagByteConverter extends TypeConverter<TagByte, Byte> {

  public static TypeConverterFactory FACTORY = TypeConverterFactory.newFactory(AxionTypeToken.get(Byte.class), new
      TagByteConverter());

  @Override
  public TagByte convert(final String name, final Byte value) {
    return new TagByte(name, value);
  }

  @Override
  public Byte convert(final TagByte tag) {
    return tag.get();
  }

}
