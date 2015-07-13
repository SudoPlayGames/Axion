package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link AxionWritable} provides an interface for creating classes that can be easily read and written by {@link
 * Axion}. This interface assumes all classes are {@link com.sudoplay.axion.spec.tag.TagCompound}
 *
 * @author Jason Taylor
 */
public interface AxionWritable {

  /**
   * Creates, writes to, and returns a {@link Tag}.
   *
   * @param out the {@link AxionWriter} instance
   */
  void write(final AxionWriter out);

  /**
   * Reads from a {@link Tag}.
   *
   * @param in the {@link AxionReader} instance
   */
  void read(final AxionReader in);

}
