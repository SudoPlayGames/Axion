package com.sudoplay.axion.stream;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * The {@link CharacterEncoder} provides read and write methods to decode and encode strings using the {@link Charset}
 * given in its constructor.
 *
 * @author Jason Taylor
 */
public class CharacterEncoder {

  /**
   * The {@link Charset} to use.
   */
  private final Charset charset;

  /**
   * Creates a new {@link CharacterEncoder} with the {@link Charset} given.
   *
   * @param newCharset the {@link Charset} to use
   */
  protected CharacterEncoder(final Charset newCharset) {
    charset = newCharset;
  }

  /**
   * Writes the given string to the given {@link AxionOutputStream} using the {@link Charset} set with the constructor.
   *
   * @param out  the {@link AxionOutputStream} to write to
   * @param data the string to write
   * @throws IOException
   */
  protected void write(final AxionOutputStream out, final String data) throws IOException {
    byte[] bytes = data.getBytes(charset);
    if (bytes.length > 65535) {
      throw new AxionCharacterEncodingException("encoded string too long: " + bytes.length + " bytes");
    }
    out.writeShort(bytes.length);
    out.write(bytes);
  }

  /**
   * Reads and returns a string from the given {@link AxionInputStream} using the {@link Charset} set with the
   * constructor.
   *
   * @param in the {@link AxionInputStream} to read from
   * @return the string read
   * @throws IOException
   */
  protected String read(final AxionInputStream in) throws IOException {
    byte[] bytes = new byte[in.readUnsignedShort()];
    in.readFully(bytes);
    return new String(bytes, charset);
  }

}
