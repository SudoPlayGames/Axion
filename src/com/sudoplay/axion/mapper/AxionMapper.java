package com.sudoplay.axion.mapper;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link AxionMapper} interface allows mapper classes to be created to convert objects into tags. This is
 * convenient when you need to repeatedly convert objects that you don't have access to into a collection of tags, such
 * as a {@link TagList} or {@link TagCompound}.
 * <p>
 * For example, if you had a 3rd party library with a Vector3f class that contained three floats and you were constantly
 * writing code to turn those three floats into a {@link TagList} of {@link TagFloat}s or a single {@link
 * TagFloatArray}, it would make sense to write a mapper to do the work for you.
 * <p>
 * Once the mapper was created, simply call:<br> <code> axion.createObjectFrom("myNewTagName", myVector3fVar); </code>
 *
 * @param <T> the {@link Tag} type
 * @param <V> the object
 * @author Jason Taylor
 */
public interface AxionMapper<T extends Tag, V> {

  /**
   * Creates and returns a new {@link Tag} from the name and object given.
   *
   * @param name   the name of the new {@link Tag}
   * @param object the object to convert
   * @param axion  the {@link Axion} instance
   * @return new {@link Tag} from the name and object given
   */
  T createTagFrom(final String name, final V object, final Axion axion);

  /**
   * Creates and returns a new object from the {@link Tag} given.
   *
   * @param tag   the {@link Tag} to create the object from
   * @param axion the {@link Axion} instance
   * @return a new object from the {@link Tag} given
   */
  V createObjectFrom(final T tag, final Axion axion);

}
