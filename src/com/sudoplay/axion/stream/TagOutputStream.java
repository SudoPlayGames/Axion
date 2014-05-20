package com.sudoplay.axion.stream;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.serializer.Interface_TagSerializer;
import com.sudoplay.axion.serializer.TagSerializer;
import com.sudoplay.axion.tag.impl.TagCompound;

/**
 * This class provides a wrapper around a DataOutputStream to facilitate writing
 * a compound tag to the stream.
 * 
 * @author Jason Taylor
 * 
 */
public class TagOutputStream implements Closeable {

  private final DataOutputStream dataOutputStream;
  private Interface_TagSerializer nbtSerializer = TagSerializer.INSTANCE;

  public TagOutputStream(final DataOutputStream newDataOutputStream) {
    dataOutputStream = newDataOutputStream;
  }

  public TagOutputStream(final DataOutputStream newDataOutputStream, final Interface_TagSerializer newNBTSerializer) {
    dataOutputStream = newDataOutputStream;
    nbtSerializer = newNBTSerializer;
  }

  public void write(TagCompound nbt) throws IOException {
    nbtSerializer.write(nbt, dataOutputStream);
  }

  @Override
  public void close() throws IOException {
    dataOutputStream.close();
  }

}
