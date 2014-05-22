package com.sudoplay.axion;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.sudoplay.axion.adapter.impl.StringOutputAdapter;
import com.sudoplay.axion.tag.impl.TagCompound;

public class AxionTest {

  private Axion axion = new Axion();

  @Test
  public void test() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream("bigtest.nbt");
    TagCompound tagCompound = (TagCompound) axion.read(inputStream);

    axion.setAdapter(new StringOutputAdapter());
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    axion.write(tagCompound, ps);
    System.out.println(baos.toString("UTF8"));
    
    
  }

}
