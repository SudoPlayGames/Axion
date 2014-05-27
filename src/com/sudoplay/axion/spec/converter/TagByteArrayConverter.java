package com.sudoplay.axion.spec.converter;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagByteArray;

public class TagByteArrayConverter extends TagConverter<TagByteArray, byte[]> {

  @Override
  public TagByteArray convert(final String name, final byte[] value) {
    return new TagByteArray(name, value);
  }

  @Override
  public byte[] convert(final TagByteArray tag) {
    return tag.get();
  }

}
