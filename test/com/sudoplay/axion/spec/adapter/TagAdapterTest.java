package com.sudoplay.axion.spec.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
import com.sudoplay.axion.TestUtil;
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
import com.sudoplay.axion.stream.CharacterEncoderFactory;
import com.sudoplay.axion.tag.Tag;

public class TagAdapterTest {

  private static final AxionOutputStream getOutputStream(ByteArrayOutputStream baos) {
    return new AxionOutputStream(baos, CharacterEncoderFactory.create(CharacterEncodingType.MODIFIED_UTF_8));
  }

  private static final AxionInputStream getInputStream(ByteArrayInputStream bais) {
    return new AxionInputStream(bais, CharacterEncoderFactory.create(CharacterEncodingType.MODIFIED_UTF_8));
  }

  @Test
  public void test_BaseTagAdapter() throws IOException {

    // read - should return null if byte zero read as id
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeByte(0);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      Tag tag = TestUtil.SPEC_REGISTRY.getBaseTagAdapter().read(null, getInputStream(bais));

      Assert.assertEquals(null, tag);
    }

    // read - should call the read method of the adapter mapped to the id read
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeByte(12);
      out.writeString("tagNull");

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      Tag tag = TestUtil.SPEC_REGISTRY.getBaseTagAdapter().read(null, getInputStream(bais));

      Assert.assertEquals(new TestUtil.TagNull("tagNull"), tag);
    }

    // write - should write byte id and name
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      TestUtil.SPEC_REGISTRY.getBaseTagAdapter().write(new TestUtil.TagNull("tagNull"), out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(12, in.readUnsignedByte());
      Assert.assertEquals("tagNull", in.readString());
    }

    // write - should write byte id and NO name because of parent type
    {
      TestUtil.TagNull tag = new TestUtil.TagNull("tagNull");
      tag.addTo(new TagList(TestUtil.TagNull.class));

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      TestUtil.SPEC_REGISTRY.getBaseTagAdapter().write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(12, in.readUnsignedByte());

      // should throw due to lack of name
      try {
        in.readString();
        Assert.fail("Expected EOFException");
      } catch (EOFException e) {
        // expected
      }
    }

  }

  @Test
  public void test_TagByteAdapter() throws IOException {

    // write - should write a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagByte tag = new TagByte("tagName", (byte) 16);
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagByte.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals((byte) 16, in.readByte());
    }

    // read - should read a name and a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeByte(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagByte tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagByte.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals((byte) 16, tag.get());
    }

    // read - should read only a single byte
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeByte(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagByte tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagByte.class).read(new TagList(TagByte.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals((byte) 16, tag.get());
    }

  }

  @Test
  public void test_TagByteArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of bytes
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagByteArray tag = new TagByteArray("tagName", new byte[] { 0, 1, 2, 3 });
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagByteArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(4, in.readInt());
      Assert.assertEquals((byte) 0, in.readByte());
      Assert.assertEquals((byte) 1, in.readByte());
      Assert.assertEquals((byte) 2, in.readByte());
      Assert.assertEquals((byte) 3, in.readByte());
    }

    // read - should read a name, an int, and a series of bytes
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(4);
      out.writeByte(0);
      out.writeByte(1);
      out.writeByte(2);
      out.writeByte(3);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagByteArray tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagByteArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertArrayEquals(new byte[] { 0, 1, 2, 3 }, tag.get());
    }

    // read - should read NO name, an int, and a series of bytes
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(4);
      out.writeByte(0);
      out.writeByte(1);
      out.writeByte(2);
      out.writeByte(3);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagByteArray tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagByteArray.class).read(new TagList(TagByteArray.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertArrayEquals(new byte[] { 0, 1, 2, 3 }, tag.get());
    }

  }

  @Test
  public void test_TagCompoundAdapter() throws IOException {

    // write - should write for each child: byte id, string name, payload;
    // finally a zero byte as id to denote the end of the container
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagCompound compound = new TagCompound();
      TagByte tag = new TagByte("tagName", (byte) 16);
      tag.addTo(compound);

      TestUtil.SPEC_REGISTRY.getAdapterFor(TagCompound.class).write(compound, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals((byte) 1, in.readByte()); // byte id
      Assert.assertEquals("tagName", in.readString()); // tag name
      Assert.assertEquals((byte) 16, in.readByte()); // payload
      Assert.assertEquals((byte) 0, in.readByte()); // end
    }

    // read - should read a string name, then, for each child: byte id, string
    // name, payload; finally a zero byte as id to denote the end of the
    // container
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("containerName");
      out.writeByte(1);
      out.writeString("tagName");
      out.writeByte(16);
      out.writeByte(0);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagCompound compound = TestUtil.SPEC_REGISTRY.getAdapterFor(TagCompound.class).read(null, getInputStream(bais));

      Assert.assertEquals("containerName", compound.getName());

      TagByte tag = compound.get("tagName");

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals((byte) 16, tag.get());
    }

    // read - should read NO string name, then, for each child: byte id, string
    // name, payload; finally a zero byte as id to denote the end of the
    // container
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeByte(1);
      out.writeString("tagName");
      out.writeByte(16);
      out.writeByte(0);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagCompound compound = TestUtil.SPEC_REGISTRY.getAdapterFor(TagCompound.class).read(new TagList(TagCompound.class), getInputStream(bais));

      Assert.assertEquals("", compound.getName());

      TagByte tag = compound.get("tagName");

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals((byte) 16, tag.get());
    }

  }

  @Test
  public void test_TagDoubleAdapter() throws IOException {

    // write - should write a single double
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagDouble tag = new TagDouble("tagName", 16.0235689);
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagDouble.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(16.0235689, in.readDouble(), TestUtil.DOUBLE_DELTA);
    }

    // read - should read a name and a single double
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeDouble(16.0235689);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagDouble tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagDouble.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals(16.0235689, tag.get(), TestUtil.DOUBLE_DELTA);
    }

    // read - should read only a single double
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeDouble(16.0235689);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagDouble tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagDouble.class).read(new TagList(TagDouble.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals(16.0235689, tag.get(), TestUtil.DOUBLE_DELTA);
    }

  }

  @Test
  public void test_TagFloatAdapter() throws IOException {

    // write - should write a single float
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagFloat tag = new TagFloat("tagName", 16.5f);
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagFloat.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(16.5f, in.readFloat(), TestUtil.DOUBLE_DELTA);
    }

    // read - should read a name and a single float
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeFloat(16.5f);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagFloat tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagFloat.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals(16.5f, tag.get(), TestUtil.DOUBLE_DELTA);
    }

    // read - should read only a single float
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeFloat(16.5f);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagFloat tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagFloat.class).read(new TagList(TagFloat.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals(16.5f, tag.get(), TestUtil.DOUBLE_DELTA);
    }

  }

  @Test
  public void test_TagIntAdapter() throws IOException {

    // write - should write a single int
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagInt tag = new TagInt("tagName", 16);
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagInt.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(16, in.readInt());
    }

    // read - should read a name and a single int
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagInt tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagInt.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals(16, tag.get());
    }

    // read - should read only a single int
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagInt tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagInt.class).read(new TagList(TagInt.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals(16, tag.get());
    }

  }

  @Test
  public void test_TagIntArrayAdapter() throws IOException {

    // write - should write a single int for length and a series of ints
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagIntArray tag = new TagIntArray("tagName", new int[] { 0, 1, 2, 3 });
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagIntArray.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(4, in.readInt());
      Assert.assertEquals(0, in.readInt());
      Assert.assertEquals(1, in.readInt());
      Assert.assertEquals(2, in.readInt());
      Assert.assertEquals(3, in.readInt());
    }

    // read - should read a name, an int, and a series of ints
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeInt(4);
      out.writeInt(0);
      out.writeInt(1);
      out.writeInt(2);
      out.writeInt(3);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagIntArray tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagIntArray.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertArrayEquals(new int[] { 0, 1, 2, 3 }, tag.get());
    }

    // read - should read NO name, an int, and a series of ints
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeInt(4);
      out.writeInt(0);
      out.writeInt(1);
      out.writeInt(2);
      out.writeInt(3);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagIntArray tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagIntArray.class).read(new TagList(TagIntArray.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertArrayEquals(new int[] { 0, 1, 2, 3 }, tag.get());
    }

  }

  @Test
  public void test_TagListAdapter() throws IOException {

    // write - should write byte type, int size, then for each child: payload
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagList list = new TagList(TagByte.class);
      TagByte tag = new TagByte("tagName", (byte) 16);
      tag.addTo(list);

      TestUtil.SPEC_REGISTRY.getAdapterFor(TagList.class).write(list, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals((byte) 1, in.readByte()); // byte id
      Assert.assertEquals(1, in.readInt()); // size
      Assert.assertEquals((byte) 16, in.readByte()); // child payload
    }

    // read - should read a string name, byte id, then, for each child: payload
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("containerName");
      out.writeByte(1); // id
      out.writeInt(1); // size
      out.writeByte(16); // child payload

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagList list = TestUtil.SPEC_REGISTRY.getAdapterFor(TagList.class).read(null, getInputStream(bais));

      Assert.assertEquals("containerName", list.getName());

      TagByte tag = list.get(0);

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals(16, tag.get());
    }

    // read - should read byte id, then, for each child: byte id, string
    // name, payload; finally a zero byte as id to denote the end of the
    // container
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeByte(1); // id
      out.writeInt(1); // size
      out.writeByte(16); // child payload

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagList list = TestUtil.SPEC_REGISTRY.getAdapterFor(TagList.class).read(new TagList(TagList.class), getInputStream(bais));

      Assert.assertEquals("", list.getName());

      TagByte tag = list.get(0);

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals((byte) 16, tag.get());
    }

  }

  @Test
  public void test_TagLongAdapter() throws IOException {

    // write - should write a single long
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagLong tag = new TagLong("tagName", 16);
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagLong.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(16, in.readLong());
    }

    // read - should read a name and a single long
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeLong(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagLong tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagLong.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals(16, tag.get());
    }

    // read - should read only a single long
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeLong(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagLong tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagLong.class).read(new TagList(TagLong.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals(16, tag.get());
    }

  }

  @Test
  public void test_TagShortAdapter() throws IOException {

    // write - should write a single short
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagShort tag = new TagShort("tagName", (short) 16);
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagShort.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals(16, in.readUnsignedShort());
    }

    // read - should read a name and a single short
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeShort(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagShort tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagShort.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals((short) 16, tag.get());
    }

    // read - should read only a single short
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeShort(16);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagShort tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagShort.class).read(new TagList(TagShort.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals((short) 16, tag.get());
    }

  }

  @Test
  public void test_TagStringAdapter() throws IOException {

    // write - should write a single string
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);

      TagString tag = new TagString("tagName", "testString");
      TestUtil.SPEC_REGISTRY.getAdapterFor(TagString.class).write(tag, out);

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      AxionInputStream in = getInputStream(bais);

      Assert.assertEquals("testString", in.readString());
    }

    // read - should read a name and a single string
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("tagName");
      out.writeString("testString");

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagString tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagString.class).read(null, getInputStream(bais));

      Assert.assertEquals("tagName", tag.getName());
      Assert.assertEquals("testString", tag.get());
    }

    // read - should read only a single string
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      AxionOutputStream out = getOutputStream(baos);
      out.writeString("testString");

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      TagString tag = TestUtil.SPEC_REGISTRY.getAdapterFor(TagString.class).read(new TagList(TagString.class), getInputStream(bais));

      Assert.assertEquals("", tag.getName());
      Assert.assertEquals("testString", tag.get());
    }

  }

}
