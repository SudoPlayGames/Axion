package com.sudoplay.axion.stream;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;

import java.nio.charset.Charset;

/**
 * The {@link CharacterEncoderFactory} is responsible for providing new instances of the {@link CharacterEncoder}.
 *
 * @author Jason Taylor
 */
public class CharacterEncoderFactory {

  private CharacterEncoderFactory() {
    //
  }

  /**
   * Creates and returns a new {@link CharacterEncoder} instance from the given {@link CharacterEncodingType}.
   *
   * @param type the type of converter to create
   * @return a new {@link CharacterEncoder} instance
   */
  public static CharacterEncoder create(final CharacterEncodingType type) {
    switch (type) {
      case ISO_8859_1:
        return new CharacterEncoder(Charset.forName("ISO-8859-1"));
      case US_ASCII:
        return new CharacterEncoder(Charset.forName("US-ASCII"));
      case UTF_16:
        return new CharacterEncoder(Charset.forName("UTF-16"));
      case UTF_16BE:
        return new CharacterEncoder(Charset.forName("UTF-16BE"));
      case UTF_16LE:
        return new CharacterEncoder(Charset.forName("UTF-16LE"));
      case UTF_8:
        return new CharacterEncoder(Charset.forName("UTF-8"));
      default:
      case MODIFIED_UTF_8:
        return new ModifiedUTFCharacterEncoder();
    }
  }
}
