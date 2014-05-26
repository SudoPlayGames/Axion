package com.sudoplay.axion.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;

public class ModifiedUTFCharacterEncoder extends CharacterEncoder {

  protected ModifiedUTFCharacterEncoder() {
    super(null);
  }

  private byte bytearr[] = new byte[80];
  private char chararr[] = new char[80];

  /**
   * Modified of {@link DataOutputStream#writeUTF(String)}
   * 
   * @see DataOutputStream#writeUTF(String)
   */
  @Override
  public void write(final AxionOutputStream out, final String data) throws IOException {
    int strlen = data.length();
    int utflen = 0;
    int c, count = 0;

    /* use charAt instead of copying String to char array */
    for (int i = 0; i < strlen; i++) {
      c = data.charAt(i);
      if ((c >= 0x0001) && (c <= 0x007F)) {
        utflen++;
      } else if (c > 0x07FF) {
        utflen += 3;
      } else {
        utflen += 2;
      }
    }

    if (utflen > 65535)
      throw new UTFDataFormatException("encoded string too long: " + utflen + " bytes");

    byte[] bytearr = null;
    if (this.bytearr == null || (this.bytearr.length < (utflen + 2)))
      this.bytearr = new byte[(utflen * 2) + 2];
    bytearr = this.bytearr;

    bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
    bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);

    int i = 0;
    for (i = 0; i < strlen; i++) {
      c = data.charAt(i);
      if (!((c >= 0x0001) && (c <= 0x007F)))
        break;
      bytearr[count++] = (byte) c;
    }

    for (; i < strlen; i++) {
      c = data.charAt(i);
      if ((c >= 0x0001) && (c <= 0x007F)) {
        bytearr[count++] = (byte) c;

      } else if (c > 0x07FF) {
        bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
        bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
        bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
      } else {
        bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
        bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
      }
    }
    out.write(bytearr, 0, utflen + 2);
  }

  /**
   * Modified version of {@link DataInputStream#readUTF(java.io.DataInput)}
   * 
   * @see DataInputStream#readUTF(java.io.DataInput)
   */
  @Override
  public String read(final AxionInputStream in) throws IOException {
    int utflen = in.readUnsignedShort();
    byte[] bytearr = null;
    char[] chararr = null;
    if (this.bytearr.length < utflen) {
      this.bytearr = new byte[utflen * 2];
      this.chararr = new char[utflen * 2];
    }
    chararr = this.chararr;
    bytearr = this.bytearr;

    int c, char2, char3;
    int count = 0;
    int chararr_count = 0;

    in.readFully(bytearr, 0, utflen);

    while (count < utflen) {
      c = (int) bytearr[count] & 0xff;
      if (c > 127)
        break;
      count++;
      chararr[chararr_count++] = (char) c;
    }

    while (count < utflen) {
      c = (int) bytearr[count] & 0xff;
      switch (c >> 4) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        /* 0xxxxxxx */
        count++;
        chararr[chararr_count++] = (char) c;
        break;
      case 12:
      case 13:
        /* 110x xxxx 10xx xxxx */
        count += 2;
        if (count > utflen)
          throw new UTFDataFormatException("malformed input: partial character at end");
        char2 = (int) bytearr[count - 1];
        if ((char2 & 0xC0) != 0x80)
          throw new UTFDataFormatException("malformed input around byte " + count);
        chararr[chararr_count++] = (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
        break;
      case 14:
        /* 1110 xxxx 10xx xxxx 10xx xxxx */
        count += 3;
        if (count > utflen)
          throw new UTFDataFormatException("malformed input: partial character at end");
        char2 = (int) bytearr[count - 2];
        char3 = (int) bytearr[count - 1];
        if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
          throw new UTFDataFormatException("malformed input around byte " + (count - 1));
        chararr[chararr_count++] = (char) (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
        break;
      default:
        /* 10xx xxxx, 1111 xxxx */
        throw new UTFDataFormatException("malformed input around byte " + count);
      }
    }
    // The number of chars produced may be less than utflen
    return new String(chararr, 0, chararr_count);
  }
}
