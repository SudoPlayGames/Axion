package com.sudoplay.axion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.extended.TagBooleanAdapter;
import com.sudoplay.axion.adapter.spec.TagByteAdapter;
import com.sudoplay.axion.adapter.spec.TagByteArrayAdapter;
import com.sudoplay.axion.adapter.spec.TagCompoundAdapter;
import com.sudoplay.axion.adapter.spec.TagDoubleAdapter;
import com.sudoplay.axion.adapter.spec.TagFloatAdapter;
import com.sudoplay.axion.adapter.spec.TagIntAdapter;
import com.sudoplay.axion.adapter.spec.TagIntArrayAdapter;
import com.sudoplay.axion.adapter.spec.TagListAdapter;
import com.sudoplay.axion.adapter.spec.TagLongAdapter;
import com.sudoplay.axion.adapter.spec.TagShortAdapter;
import com.sudoplay.axion.adapter.spec.TagStringAdapter;
import com.sudoplay.axion.converter.TagConverter;
import com.sudoplay.axion.converter.extended.TagBooleanConverter;
import com.sudoplay.axion.converter.spec.TagByteArrayConverter;
import com.sudoplay.axion.converter.spec.TagByteConverter;
import com.sudoplay.axion.converter.spec.TagCompoundConverter;
import com.sudoplay.axion.converter.spec.TagDoubleConverter;
import com.sudoplay.axion.converter.spec.TagFloatConverter;
import com.sudoplay.axion.converter.spec.TagIntArrayConverter;
import com.sudoplay.axion.converter.spec.TagIntConverter;
import com.sudoplay.axion.converter.spec.TagListConverter;
import com.sudoplay.axion.converter.spec.TagLongConverter;
import com.sudoplay.axion.converter.spec.TagShortConverter;
import com.sudoplay.axion.converter.spec.TagStringConverter;
import com.sudoplay.axion.tag.extended.TagBoolean;
import com.sudoplay.axion.tag.spec.Tag;
import com.sudoplay.axion.tag.spec.TagByte;
import com.sudoplay.axion.tag.spec.TagByteArray;
import com.sudoplay.axion.tag.spec.TagCompound;
import com.sudoplay.axion.tag.spec.TagDouble;
import com.sudoplay.axion.tag.spec.TagFloat;
import com.sudoplay.axion.tag.spec.TagInt;
import com.sudoplay.axion.tag.spec.TagIntArray;
import com.sudoplay.axion.tag.spec.TagList;
import com.sudoplay.axion.tag.spec.TagLong;
import com.sudoplay.axion.tag.spec.TagShort;
import com.sudoplay.axion.tag.spec.TagString;
import com.sudoplay.axion.util.TypeUtil;

public class Axion {

  private static final Logger LOG = LoggerFactory.getLogger(Axion.class);

  private static final Map<Class<? extends Tag>, Integer> classToId = new HashMap<Class<? extends Tag>, Integer>();
  private static final Map<Integer, Class<? extends Tag>> idToClass = new HashMap<Integer, Class<? extends Tag>>();
  private static final Map<Class<? extends Tag>, TagAdapter> classToAdapter = new HashMap<Class<? extends Tag>, TagAdapter>();
  private static final Map<Integer, TagAdapter> idToAdapter = new HashMap<Integer, TagAdapter>();
  private static final Map<Class<? extends Tag>, TagConverter<? extends Tag, ?>> classToConverter = new HashMap<Class<? extends Tag>, TagConverter<? extends Tag, ?>>();
  private static final Map<Class<?>, TagConverter<? extends Tag, ?>> typeToConverter = new HashMap<Class<?>, TagConverter<? extends Tag, ?>>();

  static {

    /*
     * Standard Tag Adapters
     */
    register(1, TagByte.class, new TagByteAdapter());
    register(2, TagShort.class, new TagShortAdapter());
    register(3, TagInt.class, new TagIntAdapter());
    register(4, TagLong.class, new TagLongAdapter());
    register(5, TagFloat.class, new TagFloatAdapter());
    register(6, TagDouble.class, new TagDoubleAdapter());
    register(7, TagByteArray.class, new TagByteArrayAdapter());
    register(8, TagString.class, new TagStringAdapter());
    register(9, TagList.class, new TagListAdapter());
    register(10, TagCompound.class, new TagCompoundAdapter());
    register(11, TagIntArray.class, new TagIntArrayAdapter());

    /*
     * Extended Tag Adapters
     */
    register(80, TagBoolean.class, new TagBooleanAdapter());

    /*
     * Standard Tag Converters
     */
    registerTagConverter(TagByte.class, Byte.class, new TagByteConverter());
    registerTagConverter(TagShort.class, Short.class, new TagShortConverter());
    registerTagConverter(TagInt.class, Integer.class, new TagIntConverter());
    registerTagConverter(TagLong.class, Long.class, new TagLongConverter());
    registerTagConverter(TagFloat.class, Float.class, new TagFloatConverter());
    registerTagConverter(TagDouble.class, Double.class, new TagDoubleConverter());
    registerTagConverter(TagByteArray.class, byte[].class, new TagByteArrayConverter());
    registerTagConverter(TagString.class, String.class, new TagStringConverter());
    registerTagConverter(TagList.class, List.class, new TagListConverter());
    registerTagConverter(TagCompound.class, Map.class, new TagCompoundConverter());
    registerTagConverter(TagIntArray.class, int[].class, new TagIntArrayConverter());

    /*
     * Extended Tag Converters
     */
    registerTagConverter(TagBoolean.class, Boolean.class, new TagBooleanConverter());

  }

  private Axion() {
    //
  }

  public static void register(final int id, final Class<? extends Tag> tagClass, final TagAdapter adapter) {
    if (classToId.containsKey(tagClass) || classToAdapter.containsKey(tagClass)) {
      throw new IllegalArgumentException("Tag class already registered: " + tagClass.getSimpleName());
    } else if (idToClass.containsKey(id) || idToAdapter.containsKey(id)) {
      throw new IllegalArgumentException("Tag id already registered: " + id);
    }
    classToId.put(tagClass, id);
    idToClass.put(id, tagClass);
    classToAdapter.put(tagClass, adapter);
    idToAdapter.put(id, adapter);
  }

  public static String getNameFor(final int id) {
    return getClassFor(id).getSimpleName();
  }

  public static String getNameFor(final Tag tag) {
    return tag.getClass().getSimpleName();
  }

  public static int getIdFor(final Class<? extends Tag> tagClass) {
    Integer result;
    if ((result = classToId.get(tagClass)) == null) {
      throw new IllegalArgumentException("No id registered for tag class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public static Class<? extends Tag> getClassFor(final int id) {
    Class<? extends Tag> result;
    if ((result = idToClass.get(id)) == null) {
      throw new IllegalArgumentException("No class registered for tag id: " + id);
    }
    return result;
  }

  public static TagAdapter getAdapterFor(final int id) {
    TagAdapter result;
    if ((result = idToAdapter.get(id)) == null) {
      throw new IllegalArgumentException("No adapter registered for id: " + id);
    }
    return result;
  }

  public static TagAdapter getAdapterFor(final Class<? extends Tag> tagClass) {
    TagAdapter result;
    if ((result = classToAdapter.get(tagClass)) == null) {
      throw new IllegalArgumentException("No adapter registered for class: " + tagClass.getSimpleName());
    }
    return result;
  }

  public static <T extends Tag, V> void registerTagConverter(final Class<T> tagClass, final Class<V> type, final TagConverter<T, V> converter) {
    if (classToConverter.containsKey(tagClass)) {
      throw new IllegalArgumentException("Converter already registered for class: " + tagClass.getSimpleName());
    } else if (typeToConverter.containsKey(type)) {
      throw new IllegalArgumentException("Converter already registered for type: " + type.getSimpleName());
    }
    classToConverter.put(tagClass, converter);
    typeToConverter.put(type, converter);
  }

  @SuppressWarnings("unchecked")
  public static <T extends Tag, V> V convertToValue(final T tag) {
    if (tag == null) {
      return null;
    } else if (!classToConverter.containsKey(tag.getClass())) {
      throw new IllegalArgumentException("No converter registered for tag class: " + tag.getClass());
    }
    TagConverter<T, ?> converter = (TagConverter<T, ?>) classToConverter.get(tag.getClass());
    return (V) converter.convert(tag);
  }

  @SuppressWarnings("unchecked")
  public static <V, T extends Tag> T convertToTag(final String name, final V value) {
    if (value == null) {
      return null;
    }
    TagConverter<T, V> converter = (TagConverter<T, V>) typeToConverter.get(value.getClass());
    if (converter == null) {
      for (Class<?> c : TypeUtil.getAllClasses(value.getClass())) {
        if (typeToConverter.containsKey(c)) {
          try {
            converter = (TagConverter<T, V>) typeToConverter.get(c);
            break;
          } catch (ClassCastException e) {
            //
          }
        }
      }
    }
    if (converter == null) {
      throw new IllegalArgumentException("No converter registered for type: " + value.getClass().getSimpleName());
    }
    return converter.convert(name, value);
  }

  public static Tag readGZip(final InputStream inputStream) throws IOException {
    return read(new GZIPInputStream(inputStream));
  }

  public void writeGZip(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    write(tagCompound, new GZIPOutputStream(outputStream));
  }

  public static Tag readDeflater(final InputStream inputStream) throws IOException {
    return read(new InflaterInputStream(inputStream));
  }

  public void writeDeflater(final TagCompound tagCompound, final OutputStream outputStream) throws IOException {
    write(tagCompound, new DeflaterOutputStream(outputStream));
  }

  public static Tag read(final InputStream in) throws IOException {
    return readTag(null, new DataInputStream(in));
  }

  public static void write(final Tag tag, final OutputStream out) throws IOException {
    writeTag(tag, new DataOutputStream(out));
  }

  public static Tag readTag(final Tag parent, final DataInputStream in) throws IOException {
    int id = in.readUnsignedByte();
    if (id == 0) {
      return null;
    } else {
      LOG.trace("reading [{}]", getClassFor(id).getSimpleName());
      Tag tag = getAdapterFor(id).read(parent, in);
      LOG.trace("finished reading [{}]", tag);
      return tag;
    }
  }

  public static void writeTag(final Tag tag, final DataOutputStream out) throws IOException {
    LOG.trace("writing [{}]", tag);
    int id = getIdFor(tag.getClass());
    out.writeByte(id);
    if (id != 0) {
      if (!(tag.getParent() instanceof TagList)) {
        out.writeUTF(tag.getName());
      }
      getAdapterFor(id).write(tag, out);
    }
    LOG.trace("finished writing [{}]", tag);
  }

}
