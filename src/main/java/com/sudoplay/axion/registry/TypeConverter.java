package com.sudoplay.axion.registry;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionInstanceException;
import com.sudoplay.axion.ext.converter.*;
import com.sudoplay.axion.spec.converter.*;
import com.sudoplay.axion.tag.Tag;

/**
 * Classes that extend the abstract {@link TypeConverter} class define how {@link Tag}s are converted to and from their
 * respective values.
 *
 * @param <T> {@link Tag} type
 * @param <V> value type
 * @author Jason Taylor
 */
public abstract class TypeConverter<T extends Tag, V> {

  protected Axion axion;

  /**
   * Group of {@link TypeConverter}s that conform to the original NBT specification.
   */
  public static class Spec {
    public static final TagByteConverter BYTE = new TagByteConverter();
    public static final TagShortConverter SHORT = new TagShortConverter();
    public static final TagIntConverter INT = new TagIntConverter();
    public static final TagLongConverter LONG = new TagLongConverter();
    public static final TagFloatConverter FLOAT = new TagFloatConverter();
    public static final TagDoubleConverter DOUBLE = new TagDoubleConverter();
    public static final TagByteArrayConverter BYTE_ARRAY = new TagByteArrayConverter();
    public static final TagStringConverter STRING = new TagStringConverter();
    public static final TagListConverter LIST = new TagListConverter();
    public static final TagCompoundConverter COMPOUND = new TagCompoundConverter();
    public static final TagIntArrayConverter INT_ARRAY = new TagIntArrayConverter();
  }

  /**
   * Group of {@link TypeConverter}s that conform to Axion's custom, extended specification.
   */
  public static class Ext {
    public static final TagBooleanConverter BOOLEAN = new TagBooleanConverter();
    public static final TagDoubleArrayConverter DOUBLE_ARRAY = new TagDoubleArrayConverter();
    public static final TagFloatArrayConverter FLOAT_ARRAY = new TagFloatArrayConverter();
    public static final TagLongArrayConverter LONG_ARRAY = new TagLongArrayConverter();
    public static final TagShortArrayConverter SHORT_ARRAY = new TagShortArrayConverter();
    public static final TagStringArrayConverter STRING_ARRAY = new TagStringArrayConverter();
    public static final TagBooleanArrayConverter BOOLEAN_ARRAY = new TagBooleanArrayConverter();
  }

  /**
   * Converts a {@link Tag} into a value.
   *
   * @param tag the {@link Tag} to convert
   * @return the converted value
   */
  public abstract V convert(final T tag);

  /**
   * Converts a value into a {@link Tag}.
   *
   * @param name  the name for the created {@link Tag}
   * @param value the value to create the tag from
   * @return a new {@link Tag} from the value
   */
  public abstract T convert(final String name, final V value);

  /**
   * Creates a new instance of this {@link TypeConverter}.
   *
   * @return a new instance of this {@link TypeConverter}
   */
  @SuppressWarnings("unchecked")
  protected TypeConverter<T, V> newInstance(Axion axion) {
    try {
      TypeConverter<T, V> converter = this.getClass().newInstance();
      converter.setAxion(axion);
      return converter;
    } catch (Exception e) {
      throw new AxionInstanceException("Unable to instantiate new converter of type "
          + this.getClass().getSimpleName(), e);
    }
  }

  public void setAxion(Axion axion) {
    this.axion = axion;
  }

  @Override
  public String toString() {
    return this.getClass().toString();
  }

}
