package com.sudoplay.axion.stream;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;

import com.sudoplay.axion.serializer.Interface_NBTSerializer;
import com.sudoplay.axion.serializer.NBTSerializer;
import com.sudoplay.axion.tag.Abstract_NBT;
import com.sudoplay.axion.tag.definition.NBTCompound;

public class NBTInputStream implements Closeable {

  private final DataInputStream dataInputStream;
  private Interface_NBTSerializer nbtSerializer = NBTSerializer.INSTANCE;

  public NBTInputStream(final DataInputStream newDataInputStream) {
    dataInputStream = newDataInputStream;
  }

  public NBTInputStream(final DataInputStream newDataInputStream, final Interface_NBTSerializer newNBTSerializer) {
    dataInputStream = newDataInputStream;
    nbtSerializer = newNBTSerializer;
  }

  public NBTCompound read() throws IOException {
    Abstract_NBT nbt = nbtSerializer.read(dataInputStream);
    if (nbt instanceof NBTCompound) {
      return (NBTCompound) nbt;
    } else {
      throw new IOException("Root NBT must be a compound tag");
    }
  }

  @Override
  public void close() throws IOException {
    dataInputStream.close();
  }

}
