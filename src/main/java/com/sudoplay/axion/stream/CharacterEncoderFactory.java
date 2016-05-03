package com.sudoplay.axion.stream;

import com.sudoplay.axion.AxionConfiguration.CharacterEncodingType;

import java.nio.charset.StandardCharsets;

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
        return new CharacterEncoder(StandardCharsets.ISO_8859_1);
      case US_ASCII:
        return new CharacterEncoder(StandardCharsets.US_ASCII);
      case UTF_16:
        return new CharacterEncoder(StandardCharsets.UTF_16);
      case UTF_16BE:
        return new CharacterEncoder(StandardCharsets.UTF_16BE);
      case UTF_16LE:
        return new CharacterEncoder(StandardCharsets.UTF_16LE);
      case UTF_8:
        return new CharacterEncoder(StandardCharsets.UTF_8);
      default:
      case MODIFIED_UTF_8:
        return new ModifiedUTFCharacterEncoder();
    }
  }
}
