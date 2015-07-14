package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Interface for the AxionReader object that is passed into the AxionWritable methods.
 * <p>
 * Facilitates the conversion of Tags to Objects.
 * <p>
 * Created by Jason Taylor on 7/12/2015.
 */
@SuppressWarnings("unused")
public interface AxionReader {

  /**
   * Returns true if the reader contains a tag with the name given.
   *
   * @param name tag name
   * @return true if the reader contains a tag with the name given
   */
  boolean has(String name);

  /**
   * Locates a convertible Tag by name, like TagInt, TagString and TagFloat, and returns its value. If the backing
   * TagCompound does not contain the Tag, null is returned.
   * <p>
   * The name parameter can not be null.
   *
   * @param name tag name
   * @param <V>  return value type
   * @return tag's value or null if the tag does not exist
   */
  <V> V read(String name);

  /**
   * Locates a convertible Tag by name, like TagInt, TagString and TagFloat, and returns its value. If the backing
   * TagCompound does not contain the Tag, the default value is returned.
   * <p>
   * This method will never return null.
   * <p>
   * The name parameter can not be null.
   *
   * @param name         tag name
   * @param defaultValue default return value
   * @param <V>          return value type
   * @return tag's value or default value if no tag with provided name
   */
  <V> V read(String name, V defaultValue);

  /**
   * Locates a convertible Tag by name, like TagInt, TagString and TagFloat, converts the Tag to its value, and applies
   * the supplied function to the value before returning it.
   * <p>
   * The name parameter can not be null.
   *
   * @param name     tag name
   * @param function function
   * @param <V>      return value type
   * @return tag's value after applying the function
   */
  <V> V read(String name, Function<V, V> function);

  /**
   * Converts a tag to its value and returns it.
   * <p>
   * The name parameter can not be null.
   *
   * @param tag tag
   * @param <V> value type
   * @param <T> tag type
   * @return tag's value
   */
  <V, T extends Tag> V read(T tag);

  /**
   * Returns the tag's value or the default value if the tag parameter is null.
   * <p>
   * The tag parameter can not be null.
   *
   * @param tag          tag
   * @param defaultValue default value
   * @param <V>          value type
   * @param <T>          tag type
   * @return tag's value or the default value if the tag parameter is null
   */
  <V, T extends Tag> V read(T tag, V defaultValue);

  /**
   * Converts the given tag to its value, applies the given function, and returns the result.
   * <p>
   * Neither the tag or function parameter can be null.
   *
   * @param tag      tag
   * @param function function
   * @param <V>      value type
   * @param <T>      tag type
   * @return tag's value after applying the function
   */
  <V, T extends Tag> V read(T tag, Function<V, V> function);

  /**
   * Locates the tag by name and converts it to its value if the given class is an AxionWritable instance or a mappable
   * class. If the tag doesn't exist
   * <p>
   * This method won't read registered tags. To read registered tags see the {@link #read(String)} and {@link
   * #read(Tag)} methods.
   *
   * @param name tag name
   * @param vClass value class
   * @param <V> value type
   * @return
   */
  <V> V read(String name, Class<V> vClass);

  <V> V read(String name, Class<V> vClass, V defaultValue);

  <V> V read(String name, Class<V> vClass, Function<V, V> function);

  <V, T extends Tag> V read(T tag, Class<V> vClass);

  <V, T extends Tag> V read(T tag, Class<V> vClass, V defaultValue);

  <V, T extends Tag> V read(T tag, Class<V> vClass, Function<V, V> function);

  <T extends Tag> T readAsTag(String name);

  <T extends Tag> T readAsTag(String name, T defaultTag);

  <T extends Tag> T readAsTag(String name, Function<T, T> function);

  <K, V> void readMap(String name, Class<K> kClass, Class<V> vClass, BiConsumer<K, V> consumer);

  <V> void readCollection(String name, Class<V> vClass, Consumer<V> consumer);

  Axion getAxion();

  TagCompound getTagCompound();

  AxionReader setTagCompound(TagCompound tagCompound);

  static <V> void readCollection(
      Axion axion,
      TagList in,
      Class<V> classOfO,
      Consumer<V> consumer
  ) {
    in.forEach(tag -> {
      consumer.accept(axion.createFromTag(tag, classOfO));
    });
  }

  static <K, V> void readMap(
      Axion axion,
      TagList in,
      Class<K> kClass,
      Class<V> vClass,
      BiConsumer<K, V> consumer
  ) {
    in.forEach(tag -> {
      TagCompound entry = (TagCompound) tag;
      K key = axion.createFromTag(entry.get("key"), kClass);
      V value = axion.createFromTag(entry.get("value"), vClass);
      consumer.accept(key, value);
    });
  }

}
