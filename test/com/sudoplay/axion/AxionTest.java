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
    return "TAG_Compound(\"Level\"): 11 entries\n{\n  TAG_Short(\"shortTest\"): 32767\n  TAG_Long(\"longTest\"): 9223372036854775807\n  TAG_Float(\"floatTest\"): 0.49823147\n  TAG_String(\"stringTest\"): HELLO WORLD THIS IS A TEST STRING ÅÄÖ!\n  TAG_Int(\"intTest\"): 2147483647\n  TAG_Compound(\"nested compound test\"): 2 entries\n  {\n    TAG_Compound(\"ham\"): 2 entries\n    {\n      TAG_String(\"name\"): Hampus\n      TAG_Float(\"value\"): 0.75\n    }\n    TAG_Compound(\"egg\"): 2 entries\n    {\n      TAG_String(\"name\"): Eggbert\n      TAG_Float(\"value\"): 0.5\n    }\n  }\n  TAG_List(\"listTest (long)\"): 5 entries of type 4\n  {\n    TAG_Long: 11\n    TAG_Long: 12\n    TAG_Long: 13\n    TAG_Long: 14\n    TAG_Long: 15\n  }\n  TAG_Byte(\"byteTest\"): 127\n  TAG_List(\"listTest (compound)\"): 2 entries of type 10\n  {\n    TAG_Compound: 2 entries\n    {\n      TAG_String(\"name\"): Compound tag #0\n      TAG_Long(\"created-on\"): 1264099775885\n    }\n    TAG_Compound: 2 entries\n    {\n      TAG_String(\"name\"): Compound tag #1\n      TAG_Long(\"created-on\"): 1264099775885\n    }\n  }\n  TAG_Byte_Array(\"byteArrayTest (the first 1000 values of (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))\"): [1000 bytes]\n  TAG_Double(\"doubleTest\"): 0.4931287132182315\n}\n";
  }
}
