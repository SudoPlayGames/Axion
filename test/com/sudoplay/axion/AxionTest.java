package com.sudoplay.axion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.sudoplay.axion.Axion.CompressionType;
import com.sudoplay.axion.adapter.StringOutputAdapter;
import com.sudoplay.axion.tag.TagCompound;

public class AxionTest {

  private Axion axion = new Axion();

  @Test
  public void test() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream("bigtest.nbt");
    TagCompound tagCompound = (TagCompound) axion.read(inputStream);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    axion.setAdapter(new StringOutputAdapter());
    axion.setCompressionType(CompressionType.None);
    axion.write(tagCompound, ps);
    System.out.println(baos.toString("UTF8"));
  }

}
