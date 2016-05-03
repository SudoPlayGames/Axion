package com.sudoplay.axion.ext.adapter;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.ext.tag.*;
import com.sudoplay.axion.spec.tag.TagList;
import com.sudoplay.axion.stream.AxionInputStream;
import com.sudoplay.axion.stream.AxionOutputStream;
import com.sudoplay.axion.stream.CharacterEncoderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class TagAdapterTest {
  private static Axion ext;

  @BeforeClass
  public static void before() {
    ext = Axion.getExtInstance();
  }

  private static final AxionOutputStream getOutputStream(ByteArrayOutputStream baos) {
    return new AxionOutputStream(baos, CharacterEncoderFactory.create(CharacterEncodingType.MODIFIED_UTF_8));
  }

  private static final AxionInputStream getInputStream(ByteArrayInputStream bais) {
    return new AxionInputStream(bais, CharacterEncoderFactory.create(CharacterEncodingType.MODIFIED_UTF_8));
  }

  @Test
  public void test_TagBooleanAdapter() throws IOException {

    // write - should write a single byte 0 for false, 1 for true
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagBoolean tagTrue = new TagBoolean("tagName", true);
      TagBoolean tagFalse = new TagBoolean("tagName", false);
      ext.getAdapterFor(TagBoolean.class).write(tagTrue, out);
      ext.getAdapterFor(TagBoolean.class).write(tagFalse, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals((byte) 1, in.readByte());
      Assert.assertEquals((byte) 0, in.readByte());
    }

    // read - should read a name and a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeByte(1);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagBoolean tag = ext.getAdapterFor(TagBoolean.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals(true, tag.get());
    }

    // read - should read only a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeByte(1);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagBoolean tag = ext.getAdapterFor(TagBoolean.class).read(new TagList(TagBoolean.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals(true, tag.get());
    }

  }

  @Test
  public void test_TagBooleanArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of bytes; eight
    // booleans are encoded in a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagBooleanArray tag = new TagBooleanArray("tagName", new boolean[]{true, false, false, true});
      ext.getAdapterFor(TagBooleanArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(4, in.readInt());
      Assert.assertEquals((byte) 9, in.readByte());
    }

    // read - should read a name, an int, and a series of bytes; eight
    // booleans are encoded in a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(4);
      out.writeByte(9);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagBooleanArray tag = ext.getAdapterFor(TagBooleanArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertTrue(Arrays.equals(new boolean[]{true, false, false, true}, tag.get()));
    }

    // read - should read NO name, an int, and a series of bytes; eight
    // booleans are encoded in a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(4);
      out.writeByte(9);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagBooleanArray tag = ext.getAdapterFor(TagBooleanArray.class).read(new TagList(TagBooleanArray.class),
          getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertTrue(Arrays.equals(new boolean[]{true, false, false, true}, tag.get()));
    }

  }

  @Test
  public void test_TagDoubleArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of doubles
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagDoubleArray tag = new TagDoubleArray("tagName", new double[]{5.2348, 0.2155, 2.3489, 1.2345});
      ext.getAdapterFor(TagDoubleArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(4, in.readInt());
      Assert.assertEquals(5.2348, in.readDouble(), TestUtil.DOUBLE_DELTA);
      Assert.assertEquals(0.2155, in.readDouble(), TestUtil.DOUBLE_DELTA);
      Assert.assertEquals(2.3489, in.readDouble(), TestUtil.DOUBLE_DELTA);
      Assert.assertEquals(1.2345, in.readDouble(), TestUtil.DOUBLE_DELTA);
    }

    // read - should read a name, an int, and a series of doubles
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(4);
      out.writeDouble(5.2348);
      out.writeDouble(0.2155);
      out.writeDouble(2.3489);
      out.writeDouble(1.2345);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagDoubleArray tag = ext.getAdapterFor(TagDoubleArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertArrayEquals(new double[]{5.2348, 0.2155, 2.3489, 1.2345}, tag.get(), TestUtil.DOUBLE_DELTA);
    }

    // read - should read NO name, an int, and a series of doubles
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(4);
      out.writeDouble(5.2348);
      out.writeDouble(0.2155);
      out.writeDouble(2.3489);
      out.writeDouble(1.2345);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagDoubleArray tag = ext.getAdapterFor(TagDoubleArray.class).read(new TagList(TagDoubleArray.class),
          getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertArrayEquals(new double[]{5.2348, 0.2155, 2.3489, 1.2345}, tag.get(), TestUtil.DOUBLE_DELTA);
    }

  }

  @Test
  public void test_TagFloatArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of floats
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagFloatArray tag = new TagFloatArray("tagName", new float[]{5.2348f, 0.2155f, 2.3489f, 1.2345f});
      ext.getAdapterFor(TagFloatArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(4, in.readInt());
      Assert.assertEquals(5.2348f, in.readFloat(), TestUtil.DOUBLE_DELTA);
      Assert.assertEquals(0.2155f, in.readFloat(), TestUtil.DOUBLE_DELTA);
      Assert.assertEquals(2.3489f, in.readFloat(), TestUtil.DOUBLE_DELTA);
      Assert.assertEquals(1.2345f, in.readFloat(), TestUtil.DOUBLE_DELTA);
    }

    // read - should read a name, an int, and a series of floats
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(4);
      out.writeFloat(5.2348f);
      out.writeFloat(0.2155f);
      out.writeFloat(2.3489f);
      out.writeFloat(1.2345f);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagFloatArray tag = ext.getAdapterFor(TagFloatArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertArrayEquals(new float[]{5.2348f, 0.2155f, 2.3489f, 1.2345f}, tag.get(), TestUtil.FLOAT_DELTA);
    }

    // read - should read NO name, an int, and a series of floats
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(4);
      out.writeFloat(5.2348f);
      out.writeFloat(0.2155f);
      out.writeFloat(2.3489f);
      out.writeFloat(1.2345f);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagFloatArray tag = ext.getAdapterFor(TagFloatArray.class).read(new TagList(TagFloatArray.class),
          getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertArrayEquals(new float[]{5.2348f, 0.2155f, 2.3489f, 1.2345f}, tag.get(), TestUtil.FLOAT_DELTA);
    }

  }

  @Test
  public void test_TagLongArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of longs
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagLongArray tag = new TagLongArray("tagName", new long[]{25876468343L, 68638465486L, 13898436544L,
          88989835355L});
      ext.getAdapterFor(TagLongArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(4, in.readInt());
      Assert.assertEquals(25876468343L, in.readLong());
      Assert.assertEquals(68638465486L, in.readLong());
      Assert.assertEquals(13898436544L, in.readLong());
      Assert.assertEquals(88989835355L, in.readLong());
    }

    // read - should read a name, an int, and a series of longs
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(4);
      out.writeLong(25876468343L);
      out.writeLong(68638465486L);
      out.writeLong(13898436544L);
      out.writeLong(88989835355L);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagLongArray tag = ext.getAdapterFor(TagLongArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertArrayEquals(new long[]{25876468343L, 68638465486L, 13898436544L, 88989835355L}, tag.get());
    }

    // read - should read NO name, an int, and a series of longs
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(4);
      out.writeLong(25876468343L);
      out.writeLong(68638465486L);
      out.writeLong(13898436544L);
      out.writeLong(88989835355L);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagLongArray tag = ext.getAdapterFor(TagLongArray.class).read(new TagList(TagLongArray.class), getInputStream
          (bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertArrayEquals(new long[]{25876468343L, 68638465486L, 13898436544L, 88989835355L}, tag.get());
    }

  }

  @Test
  public void test_TagShortArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of shorts
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagShortArray tag = new TagShortArray("tagName", new short[]{5215, 485, -9856, 4587});
      ext.getAdapterFor(TagShortArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(4, in.readInt());
      Assert.assertEquals(5215, in.readShort());
      Assert.assertEquals(485, in.readShort());
      Assert.assertEquals(-9856, in.readShort());
      Assert.assertEquals(4587, in.readShort());
    }

    // read - should read a name, an int, and a series of shorts
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(4);
      out.writeShort(5215);
      out.writeShort(485);
      out.writeShort(-9856);
      out.writeShort(4587);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagShortArray tag = ext.getAdapterFor(TagShortArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertArrayEquals(new short[]{5215, 485, -9856, 4587}, tag.get());
    }

    // read - should read NO name, an int, and a series of shorts
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(4);
      out.writeShort(5215);
      out.writeShort(485);
      out.writeShort(-9856);
      out.writeShort(4587);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagShortArray tag = ext.getAdapterFor(TagShortArray.class).read(new TagList(TagShortArray.class),
          getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertArrayEquals(new short[]{5215, 485, -9856, 4587}, tag.get());
    }

  }

  @Test
  public void test_TagStringArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of shorts
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagStringArray tag = new TagStringArray("tagName", new String[]{"Hello", "World!"});
      ext.getAdapterFor(TagStringArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(2, in.readInt());
      Assert.assertEquals("Hello", in.readString());
      Assert.assertEquals("World!", in.readString());
    }

    // read - should read a name, an int, and a series of shorts
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(2);
      out.writeString("Hello");
      out.writeString("World!");

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagStringArray tag = ext.getAdapterFor(TagStringArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertArrayEquals(new String[]{"Hello", "World!"}, tag.get());
    }

    // read - should read NO name, an int, and a series of shorts
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(2);
      out.writeString("Hello");
      out.writeString("World!");

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagStringArray tag = ext.getAdapterFor(TagStringArray.class).read(new TagList(TagStringArray.class),
          getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertArrayEquals(new String[]{"Hello", "World!"}, tag.get());
    }

  }

}
