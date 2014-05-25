package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagByteArray;

public class TagByteArrayConverter implements TagConverter<TagByteArray, byte[]> {

  @Override
  public TagByteArray convert(final String name, final byte[] value, final Axion axion) {
    return new TagByteArray(name, value);
  }

  @Override
  public byte[] convert(final TagByteArray tag, final Axion axion) {
    return tag.get();
  }

}
