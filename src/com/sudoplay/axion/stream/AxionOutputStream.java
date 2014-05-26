package com.sudoplay.axion.stream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Modified from {@link DataOutputStream}
 */
public class AxionOutputStream {

  private final OutputStream out;
  private final CharacterEncoder characterEncoder;
  private byte writeBuffer[] = new byte[8];

  public AxionOutputStream(final OutputStream outputStream, final CharacterEncoder newCharacterEncoder) {
    out = outputStream;
    characterEncoder = newCharacterEncoder;
  }

  public synchronized void write(int b) throws IOException {
    out.write(b);
  }

  public synchronized void write(byte b[]) throws IOException {
    out.write(b, 0, b.length);
  }

  public synchronized void write(byte b[], int off, int len) throws IOException {
    out.write(b, off, len);
  }

  public void flush() throws IOException {
    out.flush();
  }

  public final void writeBoolean(boolean v) throws IOException {
    out.write(v ? 1 : 0);
  }

  public final void writeByte(int v) throws IOException {
    out.write(v);
  }

  public final void writeShort(int v) throws IOException {
    out.write((v >>> 8) & 0xFF);
    out.write((v >>> 0) & 0xFF);
  }

  public final void writeInt(int v) throws IOException {
    out.write((v >>> 24) & 0xFF);
    out.write((v >>> 16) & 0xFF);
    out.write((v >>> 8) & 0xFF);
    out.write((v >>> 0) & 0xFF);
  }

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

  public final void writeFloat(float v) throws IOException {
    writeInt(Float.floatToIntBits(v));
  }

  public final void writeDouble(double v) throws IOException {
    writeLong(Double.doubleToLongBits(v));
  }

  public final void writeString(String v) throws IOException {
    characterEncoder.write(this, v);
  }

}
