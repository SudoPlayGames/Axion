package com.sudoplay.axion.tag.definition;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sudoplay.axion.tag.Abstract_NBT;

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
public class NBTCompound extends Abstract_NBT {

  public static final byte TAG_ID = 10;
  public static final String TAG_NAME = "TAG_Compound";

  private Map<String, Abstract_NBT> data;

  public NBTCompound() {
    super(null);
    data = new HashMap<String, Abstract_NBT>();
  }

  public NBTCompound(final String newName) {
    super(newName);
    data = new HashMap<String, Abstract_NBT>();
  }

  public Map<String, Abstract_NBT> getAsMap() {
    return data;
  }

  public Map<String, Abstract_NBT> getAsUnmodifiableMap() {
    return Collections.unmodifiableMap(data);
  }

  public int size() {
    return data.size();
  }

  public boolean containsKey(final String name) {
    return data.containsKey(name);
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
      return ((NBTByte) data.get(name)).get() == 0x01 ? true : false;
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
      return ((NBTByte) data.get(name)).get() == 0x01 ? true : false;
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
      return ((NBTByte) data.get(name)).get();
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
      return ((NBTByte) data.get(name)).get();
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
      return ((NBTByteArray) data.get(name)).get();
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
      return ((NBTByteArray) data.get(name)).get();
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
  public NBTCompound getCompoundOrNull(final String name) {
    return (NBTCompound) data.get(name);
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
      return ((NBTDouble) data.get(name)).get();
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
      return ((NBTDouble) data.get(name)).get();
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
      return ((NBTFloat) data.get(name)).get();
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
      return ((NBTFloat) data.get(name)).get();
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
      return ((NBTInt) data.get(name)).get();
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
      return ((NBTInt) data.get(name)).get();
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
      return ((NBTIntArray) data.get(name)).get();
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
      return ((NBTIntArray) data.get(name)).get();
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
  public NBTList getListOrNull(final String name) {
    return (NBTList) data.get(name);
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
      return ((NBTLong) data.get(name)).get();
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
      return ((NBTLong) data.get(name)).get();
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
      return ((NBTShort) data.get(name)).get();
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
      return ((NBTShort) data.get(name)).get();
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
      return ((NBTString) data.get(name)).get();
    } else {
      return defaultValue;
    }
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
  public void read(DataInput dataInput) {
    // TODO Auto-generated method stub

  }

  @Override
  public void write(DataOutput dataOutput) {
    // TODO Auto-generated method stub

  }

}
