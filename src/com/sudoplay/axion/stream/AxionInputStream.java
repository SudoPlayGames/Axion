package com.sudoplay.axion.stream;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Modified from {@link DataInputStream}
 */
public class AxionInputStream {

  protected final CharacterEncoder characterEncoder;
  protected final InputStream in;
  private byte readBuffer[] = new byte[8];

  public AxionInputStream(final InputStream newInputStream, final CharacterEncoder newCharacterEncoder) {
    in = newInputStream;
    characterEncoder = newCharacterEncoder;
  }

  public boolean readBoolean() throws IOException {
    int i = in.read();
    if (i < 0) {
      throw new EOFException();
    }
    return i != 0;
  }

  public byte readByte() throws IOException {
    int i = in.read();
    if (i < 0) {
      throw new EOFException();
    }
    return (byte) i;
  }

  public double readDouble() throws IOException {
    return Double.longBitsToDouble(readLong());
  }

  public float readFloat() throws IOException {
    return Float.intBitsToFloat(readInt());
  }

  public void readFully(byte[] b) throws IOException {
    readFully(b, 0, b.length);
  }

  public void readFully(byte[] b, int off, int len) throws IOException {
    if (len < 0) {
      throw new IndexOutOfBoundsException();
    }
    int n = 0;
    while (n < len) {
      int count = in.read(b, off + n, len - n);
      if (count < 0) {
        throw new EOFException();
      }
      n += count;
    }
  }

  public int readInt() throws IOException {
    int i1 = in.read();
    int i2 = in.read();
    int i3 = in.read();
    int i4 = in.read();
    if ((i1 | i2 | i3 | i4) < 0) {
      throw new EOFException();
    }
    return ((i1 << 24) + (i2 << 16) + (i3 << 8) + (i4 << 0));
  }

  public long readLong() throws IOException {
    readFully(readBuffer, 0, 8);
    return (((long) readBuffer[0] << 56) + ((long) (readBuffer[1] & 255) << 48) + ((long) (readBuffer[2] & 255) << 40) + ((long) (readBuffer[3] & 255) << 32)
        + ((long) (readBuffer[4] & 255) << 24) + ((readBuffer[5] & 255) << 16) + ((readBuffer[6] & 255) << 8) + ((readBuffer[7] & 255) << 0));
  }

  public short readShort() throws IOException {
    int i1 = in.read();
    int i2 = in.read();
    if ((i1 | i2) < 0) {
      throw new EOFException();
    }
    return (short) ((i1 << 8) + (i2 << 0));
  }

  public String readString() throws IOException {
    return characterEncoder.read(this);
  }

  public int readUnsignedByte() throws IOException {
    int i = in.read();
    if (i < 0) {
      throw new EOFException();
    }
    return i;
  }

  public int readUnsignedShort() throws IOException {
    int i1 = in.read();
    int i2 = in.read();
    if ((i1 | i2) < 0) {
      throw new EOFException();
    }
    return (i1 << 8) + (i2 << 0);
  }

}
