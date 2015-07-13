package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.util.Collection;
import java.util.Map;

/**
 * Interface for the AxionWriter object that is passed into the AxionWritable methods.
 * <p>
 * Facilitates the conversion of Objects to Tags.
 * <p>
 * Created by Jason Taylor on 7/12/2015.
 */
@SuppressWarnings("unused")
public interface AxionWriter {

  <S extends Tag> AxionWriter write(String name, S tag);

  AxionWriter write(String name, AxionWritable axionWritable);

  AxionWriter write(String name, Object object);

  <K, V> AxionWriter writeMap(String name, Map<K, V> map);

  AxionWriter writeCollection(String name, Collection<?> collection, Class<? extends Tag> tagClass);

  Axion getAxion();

  TagCompound getTagCompound();

  AxionWriter setTagCompound(TagCompound tagCompound);

  static <K, V> TagList writeMap(
      Axion axion,
      Map<K, V> map
  ) {
    TagList store = new TagList(TagCompound.class);
    map.forEach((key, value) -> {
      TagCompound entry = new TagCompound();
      entry.put(axion.createTagFrom("key", key));
      entry.put(axion.createTagFrom("value", value));
      store.add(entry);
    });
    return store;
  }

  static TagList writeCollection(
      Axion axion,
      Collection<?> collection,
      Class<? extends Tag> tagClass
  ) {
    TagList store = new TagList(tagClass);
    collection.forEach(element -> store.add(axion.createTagFrom(element)));
    return store;
  }

}
