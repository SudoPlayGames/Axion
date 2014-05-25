package com.sudoplay.axion.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sudoplay.axion.ext.tag.TagBoolean;
import com.sudoplay.axion.ext.tag.TagDoubleArray;
import com.sudoplay.axion.ext.tag.TagFloatArray;
import com.sudoplay.axion.ext.tag.TagLongArray;
import com.sudoplay.axion.ext.tag.TagShortArray;
import com.sudoplay.axion.ext.tag.TagStringArray;
import com.sudoplay.axion.spec.tag.Tag;
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

public class NameTest {

  @Test
  public void testSpec() {
    check(new TagByte("name"));
    check(new TagShort("name"));
    check(new TagInt("name"));
    check(new TagLong("name"));
    check(new TagFloat("name"));
    check(new TagDouble("name"));
    check(new TagByteArray("name"));
    check(new TagString("name"));
    check(new TagList(TagByte.class, "name"));
    check(new TagCompound("name"));
    check(new TagIntArray("name"));
  }

  @Test
  public void testExt() {
    check(new TagBoolean("name"));
    check(new TagDoubleArray("name"));
    check(new TagFloatArray("name"));
    check(new TagLongArray("name"));
    check(new TagShortArray("name"));
    check(new TagStringArray("name"));
  }

  private void check(Tag tag) {
    assertEquals(tag.getClass().getSimpleName(), "name", tag.getName());
  }

}
