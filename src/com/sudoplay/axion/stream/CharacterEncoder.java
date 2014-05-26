package com.sudoplay.axion.stream;

import java.io.IOException;
import java.nio.charset.Charset;

public class CharacterEncoder {

  private final Charset charset;

  protected CharacterEncoder(final Charset newCharset) {
    charset = newCharset;
  }

  public void write(final AxionOutputStream out, final String data) throws IOException {
    byte[] bytes = data.getBytes(charset);
    if (bytes.length > 65535) {
      throw new AxionCharacterEncodingException("encoded string too long: " + bytes.length + " bytes");
    }
    out.writeShort(bytes.length);
    out.write(bytes);
  }

  public String read(final AxionInputStream in) throws IOException {
    byte[] bytes = new byte[in.readUnsignedShort()];
    in.readFully(bytes);
    return new String(bytes, charset);
  }

}
