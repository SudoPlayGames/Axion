package com.sudoplay.axion.stream;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is basically a {@link DataInputStream} modified to support a custom
 * {@link CharacterEncoder}.
 * 
 * @author Jason Taylor
 */
public class AxionInputStream extends FilterInputStream {

  protected final CharacterEncoder characterEncoder;
  private byte readBuffer[] = new byte[8];

  /**
   * Creates a new {@link AxionInputStream} from the given {@link InputStream}
   * and {@link CharacterEncoder}.
   * 
   * @param newInputStream
   *          the {@link InputStream} wrap
   * @param newCharacterEncoder
   *          the {@link CharacterEncoder} to use
   */
  public AxionInputStream(final InputStream newInputStream, final CharacterEncoder newCharacterEncoder) {
    super(newInputStream);
    characterEncoder = newCharacterEncoder;
  }

  /**
   * Reads the next byte and returns true if non-zero, false otherwise.
   * <p>
   * The returned value is:<br>
   * b != 0
   * 
   * @return true or false
   * @throws IOException
   * @throws EOFException
   */
  public boolean readBoolean() throws IOException, EOFException {
    int b = in.read();
    if (b < 0) {
      throw new EOFException();
    }
    return b != 0;
  }

  /**
   * Reads and returns the next byte.
   * 
   * @return the next byte
   * @throws IOException
   * @throws EOFException
   */
  public byte readByte() throws IOException, EOFException {
    int b = in.read();
    if (b < 0) {
      throw new EOFException();
    }
    return (byte) b;
  }

  /**
   * Reads and returns the next eight bytes interpreted as a double.
   * 
   * @return the next eight bytes interpreted as a double
   * @throws IOException
   * @throws EOFException
   * @see com.sudoplay.axion.stream.AxionInputStream#readLong()
   * @see java.lang.Double#longBitsToDouble(long)
   */
  public double readDouble() throws IOException, EOFException {
    return Double.longBitsToDouble(readLong());
  }

  /**
   * Reads and returns the next four bytes interpreted as a float.
   * 
   * @return the next four bytes interpreted as a float
   * @throws IOException
   * @throws EOFException
   * @see com.sudoplay.axion.stream.AxionInputStream#readInt()
   * @see java.lang.Float#intBitsToFloat(int)
   */
  public float readFloat() throws IOException, EOFException {
    return Float.intBitsToFloat(readInt());
  }

  /**
   * Reads a sequence of bytes equal to the length of the byte array given. The
   * bytes read are stored in the byte array given.
   * 
   * @param b
   *          the byte array to store the read bytes
   * @throws IOException
   * @throws EOFException
   */
  public void readFully(byte[] b) throws IOException, EOFException {
    readFully(b, 0, b.length);
  }

  /**
   * Reads a sequence of bytes and stores them in the byte array given.
   * 
   * @param b
   *          the byte array to store the read bytes
   * @param off
   *          the offset to start reading
   * @param len
   *          the number of bytes to read
   * @throws IOException
   * @throws EOFException
   */
  public void readFully(byte[] b, int off, int len) throws IOException, EOFException {
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

  /**
   * Reads and returns the next four bytes interpreted as an integer.
   * <p>
   * The returned value is:<br>
   * ((b1 << 24) + (b2 << 16) + (b3 << 8) + (b4 << 0))
   * 
   * @return the next four bytes interpreted as an integer
   * @throws IOException
   * @throws EOFException
   */
  public int readInt() throws IOException, EOFException {
    int b1 = in.read();
    int b2 = in.read();
    int b3 = in.read();
    int b4 = in.read();
    if ((b1 | b2 | b3 | b4) < 0) {
      throw new EOFException();
    }
    return ((b1 << 24) + (b2 << 16) + (b3 << 8) + (b4 << 0));
  }

  /**
   * Reads and returns the next eight bytes interpreted as a long.
   * <p>
   * The returned value is:
   * 
   * <pre>
   * <code>
   * (((long)(a &amp; 0xff) &lt;&lt; 56) |
   *  ((long)(b &amp; 0xff) &lt;&lt; 48) |
   *  ((long)(c &amp; 0xff) &lt;&lt; 40) |
   *  ((long)(d &amp; 0xff) &lt;&lt; 32) |
   *  ((long)(e &amp; 0xff) &lt;&lt; 24) |
   *  ((long)(f &amp; 0xff) &lt;&lt; 16) |
   *  ((long)(g &amp; 0xff) &lt;&lt;  8) |
   *  ((long)(h &amp; 0xff)))
   * </code>
   * </pre>
   * <p>
   * 
   * @return the next eight bytes interpreted as a long
   * @throws IOException
   * @throws EOFException
   */
  public long readLong() throws IOException, EOFException {
    readFully(readBuffer, 0, 8);
    return (((long) readBuffer[0] << 56) + ((long) (readBuffer[1] & 0xff) << 48) + ((long) (readBuffer[2] & 0xff) << 40)
        + ((long) (readBuffer[3] & 0xff) << 32) + ((long) (readBuffer[4] & 0xff) << 24) + ((readBuffer[5] & 0xff) << 16) + ((readBuffer[6] & 0xff) << 8) + ((readBuffer[7] & 0xff) << 0));
  }

  /**
   * Reads and returns the next two bytes interpreted as a short.
   * <p>
   * The returned value is:<br>
   * ((b1 << 8) + (b2 << 0))
   * 
   * @return the next two bytes interpreted as a short
   * @throws IOException
   * @throws EOFException
   */
  public short readShort() throws IOException, EOFException {
    int b1 = in.read();
    int b2 = in.read();
    if ((b1 | b2) < 0) {
      throw new EOFException();
    }
    return (short) ((b1 << 8) + (b2 << 0));
  }

  /**
   * Reads and returns the next series of bytes interpreted as a string by the
   * {@link CharacterEncoder} set in the constructor,
   * {@link #AxionInputStream(InputStream, CharacterEncoder)}.
   * 
   * @return the next series of bytes interpreted as a string
   * @throws IOException
   * @throws EOFException
   */
  public String readString() throws IOException, EOFException {
    return characterEncoder.read(this);
  }

  /**
   * Reads and returns the next byte interpreted as an unsigned byte.
   * 
   * @return the next byte interpreted as an unsigned byte
   * @throws IOException
   * @throws EOFException
   */
  public int readUnsignedByte() throws IOException, EOFException {
    int i = in.read();
    if (i < 0) {
      throw new EOFException();
    }
    return i;
  }

  /**
   * Reads and returns the next two bytes interpreted as an unsigned short.
   * <p>
   * The returned value is:<br>
   * ((b1 << 8) + (b2 << 0))
   * 
   * @return the next two bytes interpreted as an unsigned short
   * @throws IOException
   * @throws EOFException
   */
  public int readUnsignedShort() throws IOException, EOFException {
    int b1 = in.read();
    int b2 = in.read();
    if ((b1 | b2) < 0) {
      throw new EOFException();
    }
    return ((b1 << 8) + (b2 << 0));
  }

}
