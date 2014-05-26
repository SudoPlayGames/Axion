package com.sudoplay.axion.converter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.ext.converter.TagBooleanArrayConverter;
import com.sudoplay.axion.ext.converter.TagBooleanConverter;
import com.sudoplay.axion.ext.converter.TagDoubleArrayConverter;
import com.sudoplay.axion.ext.converter.TagFloatArrayConverter;
import com.sudoplay.axion.ext.converter.TagLongArrayConverter;
import com.sudoplay.axion.ext.converter.TagShortArrayConverter;
import com.sudoplay.axion.ext.converter.TagStringArrayConverter;
import com.sudoplay.axion.spec.converter.TagByteArrayConverter;
import com.sudoplay.axion.spec.converter.TagByteConverter;
import com.sudoplay.axion.spec.converter.TagCompoundConverter;
import com.sudoplay.axion.spec.converter.TagDoubleConverter;
import com.sudoplay.axion.spec.converter.TagFloatConverter;
import com.sudoplay.axion.spec.converter.TagIntArrayConverter;
import com.sudoplay.axion.spec.converter.TagIntConverter;
import com.sudoplay.axion.spec.converter.TagListConverter;
import com.sudoplay.axion.spec.converter.TagLongConverter;
import com.sudoplay.axion.spec.converter.TagShortConverter;
import com.sudoplay.axion.spec.converter.TagStringConverter;
import com.sudoplay.axion.spec.tag.Tag;

public interface TagConverter<T extends Tag, V> {

  public V convert(final T tag, final Axion axion);

  public T convert(final String name, final V value, final Axion axion);

  public static final TagByteConverter BYTE = new TagByteConverter();
  public static final TagByteArrayConverter BYTE_ARRAY = new TagByteArrayConverter();
  public static final TagCompoundConverter COMPOUND = new TagCompoundConverter();
  public static final TagDoubleConverter DOUBLE = new TagDoubleConverter();
  public static final TagFloatConverter FLOAT = new TagFloatConverter();
  public static final TagIntConverter INT = new TagIntConverter();
  public static final TagIntArrayConverter INT_ARRAY = new TagIntArrayConverter();
  public static final TagListConverter LIST = new TagListConverter();
  public static final TagLongConverter LONG = new TagLongConverter();
  public static final TagShortConverter SHORT = new TagShortConverter();
  public static final TagStringConverter STRING = new TagStringConverter();

  public static final TagBooleanConverter BOOLEAN = new TagBooleanConverter();
  public static final TagDoubleArrayConverter DOUBLE_ARRAY = new TagDoubleArrayConverter();
  public static final TagFloatArrayConverter FLOAT_ARRAY = new TagFloatArrayConverter();
  public static final TagLongArrayConverter LONG_ARRAY = new TagLongArrayConverter();
  public static final TagShortArrayConverter SHORT_ARRAY = new TagShortArrayConverter();
  public static final TagStringArrayConverter STRING_ARRAY = new TagStringArrayConverter();
  public static final TagBooleanArrayConverter BOOLEAN_ARRAY = new TagBooleanArrayConverter();

}
