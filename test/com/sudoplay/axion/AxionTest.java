package com.sudoplay.axion;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.sudoplay.axion.tag.impl.TagCompound;

public class AxionTest {

  private Axion axion = new Axion();

  @Test
  public void test() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream("bigtest.nbt");
    TagCompound tagCompound = (TagCompound) axion.read(inputStream);

    StringOutputFormatter formatter = new StringOutputFormatter();
    System.out.println(formatter.format(tagCompound));
  }

}
