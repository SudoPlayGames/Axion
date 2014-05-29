package com.sudoplay.axion.spec.converter;

import java.util.ArrayList;
import java.util.List;

import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link TagConverter} used to convert to and from a {@link TagList}.
 * <p>
 * Part of the original specification.
 * 
 * @author Jason Taylor
 */
@SuppressWarnings("rawtypes")
public class TagListConverter extends TagConverter<TagList, List> {

  @Override
  public TagList convert(final String name, final List value) {
    if (value.isEmpty()) {
      throw new IllegalArgumentException("Can't convert an empty list");
    }
    List<Tag> tags = new ArrayList<Tag>();
    for (Object o : value) {
      tags.add(convertToTag("", o));
    }
    return new TagList(tags.get(0).getClass(), name, tags);
  }

  @Override
  public List<?> convert(final TagList tag) {
    List<Object> list = new ArrayList<Object>();
    List<? extends Tag> tags = tag.getAsList();
    for (Tag child : tags) {
      list.add(convertToValue(child));
    }
    return list;
  }

}
