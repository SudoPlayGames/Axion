package com.sudoplay.axion.spec.converter;

import java.util.ArrayList;
import java.util.List;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

@SuppressWarnings("rawtypes")
public class TagListConverter implements TagConverter<TagList, List> {

  @Override
  public TagList convert(final String name, final List value, final Axion axion) {
    if (value.isEmpty()) {
      throw new IllegalArgumentException("Can't convert an empty list");
    }
    List<Tag> tags = new ArrayList<Tag>();
    for (Object o : value) {
      tags.add(axion.convertToTag("", o));
    }
    return new TagList(tags.get(0).getClass(), name, tags);
  }

  @Override
  public List<?> convert(final TagList tag, final Axion axion) {
    List<Object> list = new ArrayList<Object>();
    List<? extends Tag> tags = tag.getAsList();
    for (Tag child : tags) {
      list.add(axion.convertToValue(child));
    }
    return list;
  }

}
