package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.registry.TagAdapter;
import com.sudoplay.axion.registry.TypeConverter;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;

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
   * Writes an object that has a {@link TypeConverter} registered with {@link Axion#registerTag(int, Class, Class,
   * TagAdapter, TypeConverter)} or {@link Axion#registerConverter(Class, TypeConverter)}.
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
   * If the given predicate returns true, writes an object that  has a {@link TypeConverter} registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TypeConverter)} or {@link Axion#registerConverter(Class,
   * TypeConverter)}.
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
   * If the given object parameter isn't null, writes an object that has a {@link TypeConverter} registered with {@link
   * Axion#registerTag(int, Class, Class, TagAdapter, TypeConverter)} or {@link Axion#registerConverter(Class,
   * TypeConverter)}.
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
