package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagByte;

public class TagByteConverter extends TagConverter<TagByte, Byte> {

  @Override
  public TagByte convert(final String name, final Byte value) {
    return new TagByte(name, value);
  }

  @Override
  public Byte convert(final TagByte tag) {
    return tag.get();
  }

}
