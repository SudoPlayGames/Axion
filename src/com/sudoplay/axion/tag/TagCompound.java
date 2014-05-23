package com.sudoplay.axion.tag;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @tag.type 10
 * 
 * @tag.name <code>TAG_Compound</code>
 * 
 * @tag.payload * A sequential list of Named Tags. This array keeps going until
 *              a <code>TAG_End</code> is found.<br>
 *              * <code>TAG_End</code> end
 * 
 * @tag.note If there's a nested <code>TAG_Compound</code> within this tag, that
 *           one will also have a <code>TAG_End</code>, so simply reading until
 *           the next <code>TAG_End</code> will not work. The names of the named
 *           tags have to be unique within each <code>TAG_Compound</code> The
 *           order of the tags is not guaranteed.
 * 
 * @author Jason Taylor
 * 
 */
public class TagCompound extends Tag implements Iterable<Tag> {

  public static final byte TAG_ID = 10;
  public static final String TAG_NAME = "TAG_Compound";

  private final Map<String, Tag> data;

  public TagCompound() {
    this(null, null);
  }

  public TagCompound(final String newName) {
    this(newName, null);
  }

  private TagCompound(final String newName, final Map<String, Tag> newMap) {
    super(newName);
    if (newMap == null) {
      data = new HashMap<String, Tag>();
    } else {
      data = new HashMap<String, Tag>(newMap);
      Iterator<Entry<String, Tag>> it = data.entrySet().iterator();
      while (it.hasNext()) {
        Entry<String, Tag> entry = it.next();
        assertValidTag(entry.getKey(), entry.getValue());
      }
    }
  }

  public Map<String, Tag> getAsMap() {
    return Collections.unmodifiableMap(data);
  }

  public Iterator<Tag> iterator() {
    return Collections.unmodifiableCollection(data.values()).iterator();
  }

  public void clear() {
    data.clear();
  }

  public int size() {
    return data.size();
  }

  public boolean containsKey(final String name) {
    return data.containsKey(name);
  }

  public Tag remove(final String name) {
    Tag result = data.remove(name);
    if (result != null) {
      result.setParent(null);
    }
    return result;
  }

  public Tag get(final String name) {
    return data.get(name);
  }

  /**
   * Get the data from a boolean byte tag; returns default value if the tag does
   * not exist.
   * 
   * @param name
   * @return boolean from named tag or default value if no tag
   */
  public boolean getBoolean(final String name, final boolean defaultValue) {
    if (data.containsKey(name)) {
      return ((TagByte) data.get(name)).get() == 0x01 ? true : false;
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from a boolean byte tag; returns null if the tag does not
   * exist.
   * 
   * @param name
   * @return boolean from named tag or null if no tag
   */
  public Boolean getBooleanOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagByte) data.get(name)).get() == 0x01 ? true : false;
    } else {
      return null;
    }
  }

  /**
   * Get the data from a byte tag; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return byte from named tag or default value if no tag
   */
  public byte getByte(final String name, final byte defaultValue) {
    if (data.containsKey(name)) {
      return ((TagByte) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from a byte tag; returns null if the tag does not exist.
   * 
   * @param name
   * @return byte from named tag or null if no tag
   */
  public Byte getByteOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagByte) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get the data from a byte array tag; returns default value if the tag does
   * not exist.
   * 
   * @param name
   * @return byte array from named tag or default value if no tag
   */
  public byte[] getByteArray(final String name, final byte[] defaultValue) {
    if (data.containsKey(name)) {
      return ((TagByteArray) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from a byte array tag; returns null if the tag does not exist.
   * 
   * @param name
   * @return byte array from named tag or null if no tag
   */
  public byte[] getByteArrayOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagByteArray) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get a compound tag labeled with name; returns null if the tag does not
   * exist.
   * 
   * @param name
   * @return NBTCompound tag with name
   */
  public TagCompound getCompoundOrNull(final String name) {
    return (TagCompound) data.get(name);
  }

  /**
   * Get the data from a double tag; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return double from named tag or default value if no tag
   */
  public double getDouble(final String name, final double defaultValue) {
    if (data.containsKey(name)) {
      return ((TagDouble) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from a double tag; returns null if the tag does not exist.
   * 
   * @param name
   * @return double from named tag or null if no tag
   */
  public Double getDoubleOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagDouble) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get the data from a float tag; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return float from named tag or default value if no tag
   */
  public float getFloat(final String name, final float defaultValue) {
    if (data.containsKey(name)) {
      return ((TagFloat) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from a float tag; returns null if the tag does not exist.
   * 
   * @param name
   * @return float from named tag or null if no tag
   */
  public Float getFloatOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagFloat) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get the data from an int tag; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return int from named tag or default value if no tag
   */
  public int getInt(final String name, final int defaultValue) {
    if (data.containsKey(name)) {
      return ((TagInt) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from an int tag; returns null if the tag does not exist.
   * 
   * @param name
   * @return int from named tag or null if no tag
   */
  public Integer getIntOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagInt) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get the int array tag with name; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return int array from named tag or default value if no tag
   */
  public int[] getIntArray(final String name, final int[] defaultValue) {
    if (data.containsKey(name)) {
      return ((TagIntArray) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the int array tag with name; returns null if the tag does not exist.
   * 
   * @param name
   * @return int array from named tag or null if no tag
   */
  public int[] getIntArrayOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagIntArray) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get the list tag with name; returns null if the tag does not exist.
   * 
   * @param name
   * @return NBTList named tag or null if no tag
   */
  public TagList getListOrNull(final String name) {
    return (TagList) data.get(name);
  }

  /**
   * Get the data from a long tag; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return long from named tag or default value if no tag
   */
  public long getLong(final String name, final long defaultValue) {
    if (data.containsKey(name)) {
      return ((TagLong) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from a long tag; returns null if the tag does not exist.
   * 
   * @param name
   * @return long from named tag or null if no tag
   */
  public Long getLongOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagLong) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get the data from a short tag; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return short from named tag or default value if no tag
   */
  public short getShort(final String name, final short defaultValue) {
    if (data.containsKey(name)) {
      return ((TagShort) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  /**
   * Get the data from a short tag; returns null if the tag does not exist.
   * 
   * @param name
   * @return short from named tag or null if no tag
   */
  public Short getShortOrNull(final String name) {
    if (data.containsKey(name)) {
      return ((TagShort) data.get(name)).get();
    } else {
      return null;
    }
  }

  /**
   * Get the data from a string tag; returns default value if the tag does not
   * exist.
   * 
   * @param name
   * @return String from named tag or default value if no tag
   */
  public String getString(final String name, final String defaultValue) {
    if (data.containsKey(name)) {
      return ((TagString) data.get(name)).get();
    } else {
      return defaultValue;
    }
  }

  public void put(final String name, final Tag tag) {
    assertValidTag(name, tag);
    tag.setParent(this);
    data.put(name, tag);
  }

  private void assertValidTag(final String name, final Tag tag) {
    if (tag == null) {
      throw new NullPointerException(TagCompound.TAG_NAME + " does not support null tags");
    } else if (tag.hasParent()) {
      throw new IllegalStateException("Tag can not be added to more than one collection tag");
    } else if (name == null || name.equals("")) {
      throw new IllegalArgumentException(TagCompound.TAG_NAME + " does not support unnamed tags");
    }
  }

  /**
   * Convert a boolean value to a byte and add to this compound tag.
   * 
   * @param name
   * @param newBoolean
   */
  public void putBoolean(String name, boolean newBoolean) {
    putByte(name, (byte) (newBoolean ? 0x01 : 0x00));
  }

  /**
   * Add a byte tag with name and data to this compound tag.
   * 
   * @param name
   * @param newByte
   */
  public void putByte(String name, byte newByte) {
    put(name, new TagByte(name, newByte));
  }

  /**
   * Add a byte array with name and data to this compound tag.
   * 
   * @param name
   * @param newByteArray
   */
  public void putByteArray(String name, byte[] newByteArray) {
    put(name, new TagByteArray(name, newByteArray));
  }

  /**
   * Add a compound tag to this compound tag. Sets the name of the compound tag
   * to name.
   * 
   * @param name
   * @param newCompound
   */
  public void putCompound(String name, TagCompound newCompound) {
    newCompound.setName(name);
    put(name, newCompound);
  }

  /**
   * Add a double tag with name and data to this compound tag.
   * 
   * @param name
   * @param newDouble
   */
  public void putDouble(String name, double newDouble) {
    put(name, new TagDouble(name, newDouble));
  }

  /**
   * Add a float tag with name and data to this compound tag.
   * 
   * @param name
   * @param newFloat
   */
  public void putFloat(String name, float newFloat) {
    put(name, new TagFloat(name, newFloat));
  }

  /**
   * Add an int tag with name and data to this compound tag.
   * 
   * @param name
   * @param newInt
   */
  public void putInt(String name, int newInt) {
    put(name, new TagInt(name, newInt));
  }

  /**
   * Add an int array tag with name and data to this compound tag.
   * 
   * @param name
   * @param newIntArray
   */
  public void putIntArray(String name, int[] newIntArray) {
    put(name, new TagIntArray(name, newIntArray));
  }

  /**
   * Add a list tag to this compound tag. Also set the name of the list to name.
   * 
   * @param name
   * @param newList
   */
  public void putList(String name, TagList newList) {
    newList.setName(name);
    put(name, newList);
  }

  /**
   * Add a long tag with name and data to this compound tag.
   * 
   * @param name
   * @param newLong
   */
  public void putLong(String name, long newLong) {
    put(name, new TagLong(name, newLong));
  }

  /**
   * Add a short tag with name and data to this compound tag.
   * 
   * @param name
   * @param newShort
   */
  public void putShort(String name, short newShort) {
    put(name, new TagShort(name, newShort));
  }

  /**
   * Add a string tag with name and data to this compound tag.
   * 
   * @param name
   * @param newString
   */
  public void putString(String name, String newString) {
    put(name, new TagString(name, newString));
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
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    TagCompound other = (TagCompound) obj;
    if (data == null) {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return TAG_NAME + super.toString() + ": " + data.size() + " entries";
  }

  @Override
  protected void onNameChange(final String oldName, final String newName) {
    if (newName == null || newName.isEmpty()) {
      throw new IllegalStateException("Tag belongs to a " + TagCompound.TAG_NAME + " and can not have an empty or null name");
    }
    data.put(newName, data.remove(oldName));
  }

  @Override
  public TagCompound clone() {
    if (data.isEmpty()) {
      return new TagCompound(getName());
    } else {
      Map<String, Tag> newMap = new HashMap<String, Tag>(data.size());
      for (Entry<String, Tag> entry : data.entrySet()) {
        newMap.put(entry.getKey(), entry.getValue().clone());
      }
      return new TagCompound(getName(), newMap);
    }

  }
}
