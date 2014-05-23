package com.sudoplay.axion.tag;


/**
 * @tag.type 0
 * 
 * @tag.name <code>TAG_End</code>
 * 
 * @tag.payload * None.
 * 
 * @tag.note This tag is used to mark the end of a list. Cannot be named! If
 *           type 0 appears where a Named Tag is expected, the name is assumed
 *           to be "". (In other words, this Tag is always just a single 0 byte
 *           when named, and nothing in all other cases).
 * 
 * @author Jason Taylor
 * 
 */
public class TagEnd extends Tag {

  public static final byte TAG_ID = (byte) 0;
  public static final String TAG_NAME = "TAG_End";
  public static final TagEnd INSTANCE = new TagEnd();

  public TagEnd() {
    super(null);
  }

  @Override
  public byte getTagId() {
    return TAG_ID;
  }

  @Override
  public String getTagName() {
    return TAG_NAME;
  }

  @Override
  public void setName(final String newName) {
    super.setName(null);
  }

  @Override
  public String toString() {
    return TAG_NAME;
  }

}
