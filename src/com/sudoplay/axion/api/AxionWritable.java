package com.sudoplay.axion.api;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.tag.Tag;

/**
 * The {@link AxionWritable} provides an interface for creating classes that can
 * be easily read and written by {@link Axion}.
 * 
 * @author Jason Taylor
 * 
 * @param <T>
 * @see Axion#read(java.io.File, AxionWritable)
 * @see Axion#read(java.io.InputStream, AxionWritable)
 * @see Axion#write(AxionWritable, java.io.File)
 * @see Axion#write(AxionWritable, java.io.OutputStream)
 */
public interface AxionWritable<T extends Tag> {

  /**
   * Creates, writes to, and returns a {@link Tag}.
   * 
   * @param axion
   *          the {@link Axion} instance
   * @return a {@link Tag}
   */
  public T write(final Axion axion);

  /**
   * Reads from a {@link Tag}.
   * 
   * @param tag
   *          the {@link Tag} to read from
   * @param axion
   *          the {@link Axion} instance
   */
  public void read(T tag, final Axion axion);

}
