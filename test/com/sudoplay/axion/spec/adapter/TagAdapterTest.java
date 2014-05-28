package com.sudoplay.axion.spec.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;
import com.sudoplay.axion.TestUtil;
import com.sudoplay.axion.spec.tag.TagList;
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

}
