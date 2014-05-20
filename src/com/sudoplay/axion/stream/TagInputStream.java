package com.sudoplay.axion.stream;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;

import com.sudoplay.axion.serializer.Interface_TagSerializer;
import com.sudoplay.axion.serializer.TagSerializer;
import com.sudoplay.axion.tag.Abstract_Tag;
import com.sudoplay.axion.tag.impl.TagCompound;

public class TagInputStream implements Closeable {

  private final DataInputStream dataInputStream;
  private Interface_TagSerializer nbtSerializer = TagSerializer.INSTANCE;

  public TagInputStream(final DataInputStream newDataInputStream) {
    dataInputStream = newDataInputStream;
  }

  public TagInputStream(final DataInputStream newDataInputStream, final Interface_TagSerializer newNBTSerializer) {
    dataInputStream = newDataInputStream;
    nbtSerializer = newNBTSerializer;
  }

  public TagCompound read() throws IOException {
    Abstract_Tag nbt = nbtSerializer.read(dataInputStream);
    if (nbt instanceof TagCompound) {
      return (TagCompound) nbt;
    } else {
      throw new IOException("Root NBT must be a compound tag");
    }
  }

  @Override
  public void close() throws IOException {
    dataInputStream.close();
  }

}
