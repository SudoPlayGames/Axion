package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TagConverter;
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
   * Locates a convertible Tag by name and returns its value or null if the tag doesn't exist.
   * <p>
   * The name parameter can't be null.
   * <p>
   * Note: This method will not read implementations of {@link AxionWritable} or objects that have been registered with
   * {@link Axion#registerNBTObjectMapper(Class, NBTObjectMapper)}. It will only read tags registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)}. To read a writable or mappable object, {@link
   * AxionReader#read(String, Class)};
   *
   * @param name tag name
   * @param <V>  return value type
   * @return tag's value or null if the tag doesn't exist
   */
  <V> V read(String name);

  /**
   * Locates a convertible Tag by name, and returns its value or the default value if the tag doesn't exist.
   * <p>
   * The name parameter can't be null.
   * <p>
   * Note: This method will not read implementations of {@link AxionWritable} or objects that have been registered with
   * {@link Axion#registerNBTObjectMapper(Class, NBTObjectMapper)}. It will only read tags registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)}. To read a writable or mappable object, {@link
   * AxionReader#read(String, Class, V)};
   *
   * @param name         tag name
   * @param defaultValue default return value
   * @param <V>          return value type
   * @return tag's value or default value if tag doesn't exist
   */
  <V> V read(String name, V defaultValue);

  /**
   * Locates a convertible Tag by name, converts the Tag to its value, and applies the given function to the value
   * before returning it. Null is returned if the tag doesn't exist.
   * <p>
   * The name parameter can not be null.
   * <p>
   * Note: This method will not read implementations of {@link AxionWritable} or objects that have been registered with
   * {@link Axion#registerNBTObjectMapper(Class, NBTObjectMapper)}. It will only read tags registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)}. To read a writable or mappable object, {@link
   * AxionReader#read(String, Class, Function)};
   *
   * @param name     tag name
   * @param function function
   * @param <V>      return value type
   * @return tag's value after function or null if the tag doesn't exist
   */
  <V> V read(String name, Function<V, V> function);

  /**
   * Converts a tag to its value and returns it.
   * <p>
   * The name parameter can not be null.
   * <p>
   * Note: This method will not read implementations of {@link AxionWritable} or objects that have been registered with
   * {@link Axion#registerNBTObjectMapper(Class, NBTObjectMapper)}. It will only read tags registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)}. To read a writable or mappable object, {@link
   * AxionReader#read(Tag, Class)};
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
   * <p>
   * Note: This method will not read implementations of {@link AxionWritable} or objects that have been registered with
   * {@link Axion#registerNBTObjectMapper(Class, NBTObjectMapper)}. It will only read tags registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)}. To read a writable or mappable object, {@link
   * AxionReader#read(Tag, V)};
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
   * <p>
   * Note: This method will not read implementations of {@link AxionWritable} or objects that have been registered with
   * {@link Axion#registerNBTObjectMapper(Class, NBTObjectMapper)}. It will only read tags registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)}. To read a writable or mappable object, {@link
   * AxionReader#read(Tag, Class, Function)};
   *
   * @param tag      tag
   * @param function function
   * @param <V>      value type
   * @param <T>      tag type
   * @return tag's value after function
   */
  <V, T extends Tag> V read(T tag, Function<V, V> function);

  /**
   * Locates the tag by name and converts it to its value if the given class is an AxionWritable instance or a mappable
   * class. Returns null if the tag doesn't exist.
   * <p>
   * Neither the name or class parameter can be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TagConverter)}. To read registered tags see the {@link AxionReader#read(String)} method.
   *
   * @param name   tag name
   * @param vClass value class
   * @param <V>    value type
   * @return tag's value or null if the tag doesn't exist
   */
  <V> V read(String name, Class<V> vClass);

  /**
   * Locates the tag by name and converts it to its value if the given class is an AxionWritable instance or a mappable
   * class. Returns the given default value if the tag doesn't exist.
   * <p>
   * Neither the name or class parameter can be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TagConverter)}. To read registered tags see the {@link AxionReader#read(String, V)} method.
   *
   * @param name         tag name
   * @param vClass       value class
   * @param defaultValue default value
   * @param <V>          value type
   * @return tag's value or the default value if the tag doesn't exist
   */
  <V> V read(String name, Class<V> vClass, V defaultValue);

  /**
   * Locates the tag by name and converts it to its value if the given class is an AxionWritable instance or a mappable
   * class. The given function is applied to the value before returning it. Returns null if the tag doesn't exist.
   * <p>
   * Neither the name, class, or function parameter can be null.
   * <p>
   * Note: This method will not read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TagConverter)}. It will only read implementations of the {@link AxionWritable} interface and objects that have
   * registered a {@link com.sudoplay.axion.mapper.NBTObjectMapper} using {@link Axion#registerNBTObjectMapper(Class,
   * NBTObjectMapper)}. To read registered tags see the {@link AxionReader#read(String, Function)} method.
   *
   * @param name     tag name
   * @param vClass   value class
   * @param function function
   * @param <V>      value type
   * @return tag's value after function
   */
  <V> V read(String name, Class<V> vClass, Function<V, V> function);

  /**
   * Converts the given tag to its value.
   * <p>
   * Neither the tag, or class parameter can be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TagConverter)}. To read registered tags see the {@link AxionReader#read(Tag)} method.
   *
   * @param tag    tag
   * @param vClass value class
   * @param <V>    value type
   * @param <T>    tag type
   * @return tag's value
   */
  <V, T extends Tag> V read(T tag, Class<V> vClass);

  /**
   * Converts the given tag to its value. If the tag parameter is null, the given defaultValue is returned instead.
   * <p>
   * The class parameter can't be null.
   * <p>
   * Note: This method won't read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TagConverter)}. To read registered tags see the {@link AxionReader#read(Tag, V)} method.
   *
   * @param tag          tag
   * @param vClass       value class
   * @param defaultValue default value
   * @param <V>          value type
   * @param <T>          tag type
   * @return tag's value or the default value if the tag parameter is null
   */
  <V, T extends Tag> V read(T tag, Class<V> vClass, V defaultValue);

  /**
   * Converts the given tag to its value, then applies the given function before returning the value.
   * <p>
   * Neither the tag, class or function parameters can be null.
   * <p>
   * Note: This method will not read tags registered with {@link Axion#registerTag(int, Class, Class, TagAdapter,
   * TagConverter)}. It will only read implementations of the {@link AxionWritable} interface and objects that have
   * registered a {@link com.sudoplay.axion.mapper.NBTObjectMapper} using {@link Axion#registerNBTObjectMapper(Class,
   * NBTObjectMapper)}. To read registered tags see the {@link AxionReader#read(Tag, Function)} method.
   *
   * @param tag      tag
   * @param vClass   value class
   * @param function function
   * @param <V>      value type
   * @param <T>      tag type
   * @return tag's value after function
   */
  <V, T extends Tag> V read(T tag, Class<V> vClass, Function<V, V> function);

  /**
   * Returns the tag with the given name or null if the tag doesn't exist.
   * <p>
   * The name parameter can't be null.
   *
   * @param name tag name
   * @param <T>  tag value
   * @return tag or null if the tag doesn't exist
   */
  <T extends Tag> T getTag(String name);

  /**
   * Returns the tag with the given name or, if the tag doesn't exist, it returns the given default tag.
   * <p>
   * The name parameter can't be null.
   *
   * @param name       tag name
   * @param defaultTag default tag
   * @param <T>        tag type
   * @return tag or default tag if tag doesn't exist
   */
  <T extends Tag> T getTag(String name, T defaultTag);

  /**
   * Finds the tag with the given name, applies the given function to the tag, and returns the tag. Null is returned if
   * the tag doesn't exist.
   * <p>
   * Neither the name or function parameter can be null.
   *
   * @param name     tag name
   * @param function function
   * @param <T>      tag type
   * @return tag or null if tag doesn't exist
   */
  <T extends Tag> T getTag(String name, Function<T, T> function);

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
