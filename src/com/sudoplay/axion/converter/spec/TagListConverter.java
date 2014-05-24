package com.sudoplay.axion.converter.spec;

import java.util.ArrayList;
import java.util.List;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagList;

@SuppressWarnings("rawtypes")
public class TagListConverter implements TagConverter<TagList, List> {

  @Override
  public TagList convert(String name, List value) {
    if (value.isEmpty()) {
      throw new IllegalArgumentException("Can't convert an empty list");
    }
    List<Tag> tags = new ArrayList<Tag>();
    for (Object o : value) {
      tags.add(Axion.convertToTag("", o));
    }
    return new TagList(Axion.getIdFor(tags.get(0).getClass()), name, tags);
  }

  @Override
  public List<?> convert(TagList tag) {
    List<Object> list = new ArrayList<Object>();
    List<? extends Tag> tags = tag.getAsList();
    for (Tag child : tags) {
      list.add(Axion.convertToValue(child));
    }
    return list;
  }

}
