package com.sudoplay.axion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sudoplay.axion.adapter.TagAdapter;
import com.sudoplay.axion.adapter.TagConverter;
import com.sudoplay.axion.adapter.TagRegistry;
import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagBooleanArray;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagByteArray;
import com.sudoplay.axion.spec.tag.TagCompound;
import com.sudoplay.axion.spec.tag.TagDouble;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagInt;
import com.sudoplay.axion.spec.tag.TagIntArray;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.spec.tag.TagLong;
import com.sudoplay.axion.spec.tag.TagShort;
import com.sudoplay.axion.spec.tag.TagString;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.tag.ContainerTag;
import com.sudoplay.axion.tag.Tag;

public class TestUtil {

  private TestUtil() {
    //
  }

  public static final double DOUBLE_DELTA = 1e-15;
  public static final float FLOAT_DELTA = 1e-15f;

  public static class AbstractTagTestClass extends Tag {

    public AbstractTagTestClass(String newName) {
      super(newName);
    }

    @Override
    public AbstractTagTestClass clone() {
      return new AbstractTagTestClass(getName());
    }

  }

  public static class AbstractContainerTagTestClass extends ContainerTag {

    private List<Tag> children = new ArrayList<Tag>();

    public AbstractContainerTagTestClass(String newName) {
      super(newName);
    }

    @Override
    protected void onChildNameChange(String oldName, String newName) {
      throw new RuntimeException();
    }

    @Override
    protected void onChildAddition(Tag tag) {
      children.add(tag);
    }

    @Override
    protected void onChildRemoval(Tag tag) {
      children.remove(tag);
    }

    @Override
    public boolean contains(Tag tag) {
      return children.contains(tag);
    }

    public int size() {
      return children.size();
    }

    @Override
    public AbstractContainerTagTestClass clone() {
      return new AbstractContainerTagTestClass(getName());
    }

    @Override
    public Iterator<Tag> iterator() {
      return children.iterator();
    }

    @Override
    public void clear() {
      children.clear();
    }

  }

  public static TagBoolean getTagBoolean() {
    return new TagBoolean("tagBoolean", true);
  }

  public static TagDoubleArray getTagDoubleArray() {
    return new TagDoubleArray("tagDoubleArray", new double[] { 45.345, 7456.34, 5713.0012, 251.32 });
  }

  public static TagFloatArray getTagFloatArray() {
    return new TagFloatArray("tagFloatArray", new float[] { 2.5f, 13.58f, 1.02f, 778.02001f });
  }

  public static TagLongArray getTagLongArray() {
    return new TagLongArray("tagLongArray", new long[] { 163986384486338L, 36868746684636843L, 68798798789468798L, 561657876813368L });
  }

  public static TagShortArray getTagShortArray() {
    return new TagShortArray("tagShortArray", new short[] { 45, 689, 874, 12254 });
  }

  public static TagStringArray getTagStringArray() {
    return new TagStringArray("tagStringArray", new String[] { "hello", "world", "foo", "bar" });
  }

  public static TagBooleanArray getTagBooleanArray() {
    return new TagBooleanArray("tagBooleanArray", new boolean[] { true, false, true, false, true, false, true, false, true, false, true, false, true, false,
        true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true,
        false, true, false });
  }

  public static TagCompound getTagCompound() {
    TagCompound tag = new TagCompound("name");
    TagList listA = new TagList(TagByte.class, "list");
    listA.add(new TagByte("tagA", (byte) 16));
    listA.add(new TagByte("tagB", (byte) 8));
    listA.add(new TagByte("tagC", (byte) 4));
    listA.add(new TagByte("tagD", (byte) 2));
    tag.put(listA);
    tag.put(new TagByte("byte", (byte) 16));
    tag.put(new TagByteArray("byteArray", new byte[] { 0, 1, 2, 3 }));
    tag.put(new TagDouble("double", 67.394857));
    tag.put(new TagFloat("float", 6.453f));
    tag.put(new TagInt("int", 16));
    tag.put(new TagIntArray("intArray", new int[] { 0, 1, 2, 3 }));
    tag.put(new TagLong("long", 79L));
    tag.put(new TagShort("short", (short) 947));
    tag.put(new TagString("string", "somestring"));
    return tag;
  }

  public static Map<String, Object> getMap() {
    List<Byte> list = new ArrayList<Byte>();
    list.add((byte) 16);
    list.add((byte) 8);
    list.add((byte) 4);
    list.add((byte) 2);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("list", list);
    map.put("byte", (byte) 16);
    map.put("byteArray", new byte[] { 0, 1, 2, 3 });
    map.put("double", 67.394857);
    map.put("float", 6.453f);
    map.put("int", 16);
    map.put("intArray", new int[] { 0, 1, 2, 3 });
    map.put("long", 79L);
    map.put("short", (short) 947);
    map.put("string", "somestring");

    return map;
  }

  public static TagList getTagList() {
    TagList tag = new TagList(TagInt.class, "newList");
    tag.add(new TagInt("newName1", 42));
    tag.add(new TagInt("newName2", 54));
    tag.add(new TagInt("newName3", 12));
    tag.add(new TagInt("newName4", 83));
    return tag;
  }

  public static class Null {
    public static final Null INSTANCE = new Null();
  }

  public static class TagNull extends Tag {

    public TagNull(final String newName) {
      super(newName);
    }

    @Override
    public TagNull clone() {
      return new TagNull(getName());
    }

  }

  public static class TagNullConverter extends TagConverter<TagNull, Null> {

    @Override
    public Null convert(TagNull tag) {
      return Null.INSTANCE;
    }

    @Override
    public TagNull convert(String name, Null value) {
      return new TagNull(name);
    }

  }

  public static class TagNullAdapter extends TagAdapter<TagNull> {

    @Override
    public TagNull read(Tag parent, AxionInputStream in) throws IOException {
      return new TagNull(parent instanceof TagList ? null : in.readString());
    }

    @Override
    public void write(TagNull tag, AxionOutputStream out) throws IOException {
      // no payload
    }

  }

  public static final TagRegistry SPEC_REGISTRY = new TagRegistry() {
    {
      registerBaseTagAdapter(TagAdapter.Spec.BASE);
      register(1, TagByte.class, Byte.class, TagAdapter.Spec.BYTE, TagConverter.Spec.BYTE);
      register(2, TagShort.class, Short.class, TagAdapter.Spec.SHORT, TagConverter.Spec.SHORT);
      register(3, TagInt.class, Integer.class, TagAdapter.Spec.INT, TagConverter.Spec.INT);
      register(4, TagLong.class, Long.class, TagAdapter.Spec.LONG, TagConverter.Spec.LONG);
      register(5, TagFloat.class, Float.class, TagAdapter.Spec.FLOAT, TagConverter.Spec.FLOAT);
      register(6, TagDouble.class, Double.class, TagAdapter.Spec.DOUBLE, TagConverter.Spec.DOUBLE);
      register(7, TagByteArray.class, byte[].class, TagAdapter.Spec.BYTE_ARRAY, TagConverter.Spec.BYTE_ARRAY);
      register(8, TagString.class, String.class, TagAdapter.Spec.STRING, TagConverter.Spec.STRING);
      register(9, TagList.class, List.class, TagAdapter.Spec.LIST, TagConverter.Spec.LIST);
      register(10, TagCompound.class, Map.class, TagAdapter.Spec.COMPOUND, TagConverter.Spec.COMPOUND);
      register(11, TagIntArray.class, int[].class, TagAdapter.Spec.INT_ARRAY, TagConverter.Spec.INT_ARRAY);

      register(12, TagNull.class, Null.class, new TagNullAdapter(), new TagNullConverter());
    }
  };

}
