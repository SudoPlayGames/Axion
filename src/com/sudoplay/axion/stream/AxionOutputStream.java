package com.sudoplay.axion.stream;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is basically a {@link DataOutputStream} modified to support a custom
 * {@link CharacterEncoder}.
 * 
 * @author Jason Taylor
 */
public class AxionOutputStream extends FilterOutputStream {

  private final CharacterEncoder characterEncoder;
  private byte writeBuffer[] = new byte[8];

  /**
   * Creates a new {@link AxionOutputStream} from the given {@link InputStream}
   * and {@link CharacterEncoder}.
   * 
   * @param newOutputStream
   *          the {@link OutputStream} to wrap
   * @param newCharacterEncoder
   *          the {@link CharacterEncoder} to use
   */
  public AxionOutputStream(final OutputStream newOutputStream, final CharacterEncoder newCharacterEncoder) {
    super(newOutputStream);
    characterEncoder = newCharacterEncoder;
  }

  /**
   * Writes a single byte; 0 for false, 1 for true.
   * 
   * @param v
   *          the boolean value to write
   * @throws IOException
   */
  public final void writeBoolean(boolean v) throws IOException {
    out.write(v ? 1 : 0);
  }

  /**
   * Writes a single byte.
   * 
   * @param v
   *          the byte value to write
   * @throws IOException
   */
  public final void writeByte(int v) throws IOException {
    out.write(v);
  }

  /**
   * Writes a two-byte short.
   * 
   * @param v
   *          the short value to write
   * @throws IOException
   */
  public final void writeShort(int v) throws IOException {
    out.write((v >>> 8) & 0xFF);
    out.write((v >>> 0) & 0xFF);
  }

  /**
   * Writes a four-byte integer.
   * 
   * @param v
   *          the integer value to write
   * @throws IOException
   */
  public final void writeInt(int v) throws IOException {
    out.write((v >>> 24) & 0xFF);
    out.write((v >>> 16) & 0xFF);
    out.write((v >>> 8) & 0xFF);
    out.write((v >>> 0) & 0xFF);
  }

  /**
   * Writes an eight-byte long.
   * 
   * @param v
   *          the long value to write
   * @throws IOException
   */
  public final void writeLong(long v) throws IOException {
    writeBuffer[0] = (byte) (v >>> 56);
    writeBuffer[1] = (byte) (v >>> 48);
    writeBuffer[2] = (byte) (v >>> 40);
    writeBuffer[3] = (byte) (v >>> 32);
    writeBuffer[4] = (byte) (v >>> 24);
    writeBuffer[5] = (byte) (v >>> 16);
    writeBuffer[6] = (byte) (v >>> 8);
    writeBuffer[7] = (byte) (v >>> 0);
    out.write(writeBuffer, 0, 8);
  }

  /**
   * Writes a four-byte float.
   * 
   * @param v
   *          the float to write
   * @throws IOException
   */
  public final void writeFloat(float v) throws IOException {
    writeInt(Float.floatToIntBits(v));
  }

  /**
   * Writes an eight-byte double.
   * 
   * @param v
   *          the double to write
   * @throws IOException
   */
  public final void writeDouble(double v) throws IOException {
    writeLong(Double.doubleToLongBits(v));
  }

  /**
   * Writes a string using the {@link CharacterEncoder} set in the constructor,
   * {@link #AxionOutputStream(InputStream, CharacterEncoder)}.
   * 
   * @param v
   * @throws IOException
   */
  public final void writeString(String v) throws IOException {
    characterEncoder.write(this, v);
  }

}
