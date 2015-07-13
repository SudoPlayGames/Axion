package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

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

  <S extends Tag> AxionWriter write(String name, S tag, Predicate<S> predicate);

  AxionWriter write(String name, AxionWritable axionWritable);

  AxionWriter write(String name, AxionWritable axionWritable, Predicate<AxionWritable> predicate);

  AxionWriter write(String name, Object object);

  <O> AxionWriter write(String name, O object, Predicate<O> predicate);

  <K, V> AxionWriter writeMap(String name, Map<K, V> map);

  AxionWriter writeCollection(String name, Collection<?> collection, Class<? extends Tag> tagClass);

  Axion getAxion();

  TagCompound getTagCompound();

  AxionWriter setTagCompound(TagCompound tagCompound);

  /**
   * Writes a Map to a TagList.
   * <p>
   * A TagList was chosen over a TagCompound to provide support for sorted maps and non-string keys.
   *
   * @param axion
   * @param map
   * @param <K>
   * @param <V>
   * @return
   */
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
