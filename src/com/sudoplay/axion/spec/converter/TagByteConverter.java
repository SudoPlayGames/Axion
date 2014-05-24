package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagByte;

public class TagByteConverter implements TagConverter<TagByte, Byte> {

  @Override
  public TagByte convert(String name, Byte value) {
    return new TagByte(name, value);
  }

  @Override
  public Byte convert(TagByte tag) {
    return tag.get();
  }

}
