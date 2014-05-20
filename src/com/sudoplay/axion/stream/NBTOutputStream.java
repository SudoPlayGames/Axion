package com.sudoplay.axion.stream;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sudoplay.axion.serializer.Interface_NBTSerializer;
import com.sudoplay.axion.serializer.NBTSerializer;
import com.sudoplay.axion.tag.definition.NBTCompound;

/**
 * This class provides a wrapper around a DataOutputStream to facilitate writing
 * a compound tag to the stream.
 * 
 * @author Jason Taylor
 * 
 */
public class NBTOutputStream implements Closeable {

  private final DataOutputStream dataOutputStream;
  private Interface_NBTSerializer nbtSerializer = NBTSerializer.INSTANCE;

  public NBTOutputStream(final DataOutputStream newDataOutputStream) {
    dataOutputStream = newDataOutputStream;
  }

  public NBTOutputStream(final DataOutputStream newDataOutputStream, final Interface_NBTSerializer newNBTSerializer) {
    dataOutputStream = newDataOutputStream;
    nbtSerializer = newNBTSerializer;
  }

  public void write(NBTCompound nbt) throws IOException {
    nbtSerializer.write(nbt, dataOutputStream);
  }

  @Override
  public void close() throws IOException {
    dataOutputStream.close();
  }

}
