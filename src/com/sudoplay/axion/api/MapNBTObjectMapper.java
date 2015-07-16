package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.spec.tag.TagList;

import java.util.Map;

/**
 * Created by Jason Taylor on 7/15/2015.
 */
public class MapNBTObjectMapper implements NBTObjectMapper<TagList, Map> {
  @Override
  public TagList createTagFrom(String name, Map object, Axion axion) {
    return null;
  }

  @Override
  public Map createObjectFrom(TagList tag, Axion axion) {
    return null;
  }
}
