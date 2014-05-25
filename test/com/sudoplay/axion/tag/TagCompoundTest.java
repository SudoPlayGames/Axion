package com.sudoplay.axion.tag;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.axion.spec.tag.TagByte;
import com.sudoplay.axion.spec.tag.TagCompound;

public class TagCompoundTest {

  @Test
  public void testGetValue() {
    TagCompound tag = new TagCompound("name");
    tag.put(new TagByte("tagByte", (byte) 45));

    assertEquals((byte) 45, ((TagByte) tag.get("tagByte")).get());

  }

}
