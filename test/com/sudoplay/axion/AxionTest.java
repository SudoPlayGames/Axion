package com.sudoplay.axion;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.sudoplay.axion.tag.standard.TagCompound;
import com.sudoplay.axion.util.TagToStringUtil;

public class AxionTest {

  @Test
  public void test() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream("bigtest.nbt");
    TagCompound tagCompound = (TagCompound) Axion.readGZip(inputStream);
    assertEquals(getTestString(), TagToStringUtil.getString(tagCompound));
  }

  private String getTestString() {
    return "TagCompound(\"Level\"): 11 entries\n{\n  TagShort(\"shortTest\"): 32767\n  TagLong(\"longTest\"): 9223372036854775807\n  TagFloat(\"floatTest\"): 0.49823147\n  TagString(\"stringTest\"): HELLO WORLD THIS IS A TEST STRING ÅÄÖ!\n  TagInt(\"intTest\"): 2147483647\n  TagCompound(\"nested compound test\"): 2 entries\n  {\n    TagCompound(\"ham\"): 2 entries\n    {\n      TagString(\"name\"): Hampus\n      TagFloat(\"value\"): 0.75\n    }\n    TagCompound(\"egg\"): 2 entries\n    {\n      TagString(\"name\"): Eggbert\n      TagFloat(\"value\"): 0.5\n    }\n  }\n  TagList(\"listTest (long)\"): 5 entries of type 4\n  {\n    TagLong: 11\n    TagLong: 12\n    TagLong: 13\n    TagLong: 14\n    TagLong: 15\n  }\n  TagByte(\"byteTest\"): 127\n  TagList(\"listTest (compound)\"): 2 entries of type 10\n  {\n    TagCompound: 2 entries\n    {\n      TagString(\"name\"): Compound tag #0\n      TagLong(\"created-on\"): 1264099775885\n    }\n    TagCompound: 2 entries\n    {\n      TagString(\"name\"): Compound tag #1\n      TagLong(\"created-on\"): 1264099775885\n    }\n  }\n  TagByteArray(\"byteArrayTest (the first 1000 values of (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))\"): [1000 bytes]\n  TagDouble(\"doubleTest\"): 0.4931287132182315\n}\n";
  }
}
