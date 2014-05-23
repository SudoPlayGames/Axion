package com.sudoplay.axion.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.sudoplay.axion.adapter.StringOutputAdapter;
import com.sudoplay.axion.tag.Tag;
import com.sudoplay.axion.tag.TagCompound;

public class StringOutputAdapterTest {

  @Test
  public void testRead() throws IOException {
    try {
      StringOutputAdapter adapter = new StringOutputAdapter();
      adapter.read(null, null);
      fail(StringOutputAdapter.class.getSimpleName() + " should throw UnsupportedOperationException on read()");
    } catch (UnsupportedOperationException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testWrite() throws IOException {
    StringOutputAdapter adapter = new StringOutputAdapter(StandardCharsets.UTF_8);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    adapter.write(getTestTag(), baos);
    baos.close();
    assertEquals(getTestTagString(), baos.toString("UTF-8"));
  }

  private Tag getTestTag() {
    return new TagCompound("root") {
      {
        putBoolean("boolean", true);
        putInt("int", 16);
      }
    };
  }

  private String getTestTagString() {
    return "TAG_Compound(\"root\"): 2 entries\n{\n  TAG_Int(\"int\"): 16\n  TAG_Byte(\"boolean\"): 1\n}\n";
  }

}
