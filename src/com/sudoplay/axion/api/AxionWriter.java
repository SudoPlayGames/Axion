package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.util.AxionTypeToken;

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

  /**
   * Writes the tag with the name.
   * <p>
   * Neither name or tag parameter can be null.
   *
   * @param name name
   * @param tag  tag
   * @param <T>  tag type
   * @return this {@link AxionWriter} for chaining
   */
  <T extends Tag> AxionWriter write(String name, T tag);

  /**
   * Writes the {@link AxionWritable} implementation with the name.
   * <p>
   * Neither the name or axionWritable parameter can be null.
   *
   * @param name          name
   * @param axionWritable AxionWritable instance
   * @return this {@link AxionWriter} for chaining
   */
  AxionWriter write(String name, AxionWritable axionWritable);

  /**
   * TODO
   *
   * @param name
   * @param collection
   * @param <V>
   * @return
   */
  <V> AxionWriter write(String name, Collection<V> collection);

  /**
   * TODO
   *
   * @param name
   * @param map
   * @param <K>
   * @param <V>
   * @return
   */
  <K, V> AxionWriter write(String name, Map<K, V> map, AxionTypeToken<Map<K, V>> typeToken);

  /**
   * Writes an object that has a {@link TagConverter} registered with {@link Axion#registerTag(int, Class, Class,
   * TagAdapter, TagConverter)} or {@link Axion#registerConverter(Class, TagConverter)}.
   * <p>
   * Note: This method will not write {@link AxionWritable} implementations, instead see {@link
   * AxionWriter#write(String, AxionWritable)}.
   * <p>
   * Neither the name or object parameter can be null.
   *
   * @param name   name
   * @param object object
   * @return this {@link AxionWriter} for chaining
   */
  AxionWriter write(String name, Object object);

  // --------------------------------------------------------------------------
  // Predicate
  // --------------------------------------------------------------------------

  /**
   * Writes the {@link Tag} only if the given predicate returns true.
   * <p>
   * The name parameter can't be null, however, the tag parameter can be null if the predicate returns false.
   *
   * @param name      name
   * @param tag       tag
   * @param predicate predicate
   * @param <T>       tag type
   * @return this {@link AxionWriter} for chaining
   */
  <T extends Tag> AxionWriter writeIf(String name, T tag, Predicate<T> predicate);

  /**
   * Writes the {@link AxionWritable} only if the given predicate returns true.
   * <p>
   * The name parameter can't be null, however, the axionWritable parameter can be null if the predicate returns false.
   *
   * @param name          name
   * @param axionWritable {@link AxionWritable} implementation
   * @param predicate     predicate
   * @return this {@link AxionWriter} for chaining
   */
  AxionWriter writeIf(String name, AxionWritable axionWritable, Predicate<AxionWritable> predicate);

  /**
   * TODO
   *
   * @param name
   * @param collection
   * @param predicate
   * @param <V>
   * @return
   */
  <V> AxionWriter writeIf(String name, Collection<V> collection, Predicate<Collection<V>> predicate);

  /**
   * TODO
   *
   * @param name
   * @param map
   * @param predicate
   * @param <K>
   * @param <V>
   * @return
   */
  <K, V> AxionWriter writeIf(String name, Map<K, V> map, Predicate<Map<K, V>> predicate);

  /**
   * If the given predicate returns true, writes an object that  has a {@link TagConverter} registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)} or {@link Axion#registerConverter(Class,
   * TagConverter)}.
   * <p>
   * Note: This method will not write {@link AxionWritable} implementations, instead see {@link
   * AxionWriter#writeIf(String, AxionWritable, Predicate)}.
   * <p>
   * The name parameter can't be null, however, the object parameter can be null if the predicate returns false.
   *
   * @param name      name
   * @param object    object
   * @param predicate predicate
   * @param <O>       object type
   * @return this {@link AxionWriter} for chaining
   */
  <O> AxionWriter writeIf(String name, O object, Predicate<O> predicate);

  // --------------------------------------------------------------------------
  // Not Null
  // --------------------------------------------------------------------------

  /**
   * Writes the {@link Tag} if it isn't null.
   * <p>
   * The name parameter can't be null.
   *
   * @param name name
   * @param tag  tag
   * @param <T>  tag type
   * @return this {@link AxionWriter} for chaining
   */
  <T extends Tag> AxionWriter writeIfNotNull(String name, T tag);

  /**
   * Writes the {@link AxionWritable} implementation if it isn't null.
   *
   * @param name          name
   * @param axionWritable {@link AxionWritable} implementation
   * @return this {@link AxionWriter} for chaining
   */
  AxionWriter writeIfNotNull(String name, AxionWritable axionWritable);

  /**
   * TODO
   *
   * @param name
   * @param collection
   * @param <V>
   * @return
   */
  <V> AxionWriter writeIfNotNull(String name, Collection<V> collection);

  /**
   * TODO
   *
   * @param name
   * @param map
   * @param <K>
   * @param <V>
   * @return
   */
  <K, V> AxionWriter writeIfNotNull(String name, Map<K, V> map);

  /**
   * If the given object parameter isn't null, writes an object that has a {@link TagConverter} registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TagConverter)} or {@link Axion#registerConverter(Class,
   * TagConverter)}.
   * <p>
   * Note: This method will not write {@link AxionWritable} implementations, instead see {@link
   * AxionWriter#writeIf(String, AxionWritable, Predicate)}.
   * <p>
   * The name parameter can't be null.
   *
   * @param name   name
   * @param object object
   * @param <O>    object type
   * @return this {@link AxionWriter} for chaining
   */
  <O> AxionWriter writeIfNotNull(String name, O object);

  // --------------------------------------------------------------------------
  // Miscellaneous
  // --------------------------------------------------------------------------

  Axion getAxion();

  TagCompound getTagCompound();

  AxionWriter setTagCompound(TagCompound tagCompound);

}
