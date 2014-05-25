package com.sudoplay.axion.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class CharacterEncoder {

  private final Charset charset;

  protected CharacterEncoder(final Charset newCharset) {
    charset = newCharset;
  }

  public void write(final DataOutputStream out, final String data) throws IOException {
    byte[] bytes = data.getBytes(charset);
    out.writeShort(bytes.length);
    out.write(bytes);
  }

  public String read(final DataInputStream in) throws IOException {
    byte[] bytes = new byte[in.readShort()];
    in.readFully(bytes);
    return new String(bytes, charset);
  }

  public static final CharacterEncoder MODIFIED_UTF_8 = new CharacterEncoder(null) {
    @Override
    public void write(final DataOutputStream out, final String data) throws IOException {
      out.writeUTF(data);
    }

    @Override
    public String read(final DataInputStream in) throws IOException {
      return in.readUTF();
    }
  };

  public static final CharacterEncoder ISO_8859_1 = new CharacterEncoder(Charset.forName("ISO-8859-1")) {
    //
  };

  public static final CharacterEncoder US_ASCII = new CharacterEncoder(Charset.forName("US-ASCII")) {
    //
  };

  public static final CharacterEncoder UTF_16 = new CharacterEncoder(Charset.forName("UTF-16")) {
    //
  };

  public static final CharacterEncoder UTF_16BE = new CharacterEncoder(Charset.forName("UTF-16BE")) {
    //
  };

  public static final CharacterEncoder UTF_16LE = new CharacterEncoder(Charset.forName("UTF-16LE")) {
    //
  };

  public static final CharacterEncoder UTF_8 = new CharacterEncoder(Charset.forName("UTF-8")) {
    //
  };

}
