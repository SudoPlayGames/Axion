package com.sudoplay.axion.spec.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.mapper.AxionMapperRegistrationException;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.tag.AxionIllegalTagNameException;
import com.sudoplay.axion.tag.AxionInvalidTagException;
import com.sudoplay.axion.tag.ContainerTag;
import com.sudoplay.axion.tag.Tag;

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
public class TagCompound extends ContainerTag {

  private final Map<String, Tag> data;

  /**
   * Creates a new {@link TagCompound} with no name and an empty backing map.
   */
  public TagCompound() {
    this(null, null);
  }

  /**
   * Creates a new {@link TagCompound} with no name and a copy of the map given
   * as the backing map.
   * 
   * @param newMap
   *          the {@link Map} value
   */
  public TagCompound(final Map<String, Tag> newMap) {
    this(null, newMap);
  }

  /**
   * Creates a new {@link TagCompound} with the given name and an empty backing
   * map.
   * 
   * @param newName
   *          the {@link Tag} name
   */
  public TagCompound(final String newName) {
    this(newName, null);
  }

  /**
   * Creates a new {@link TagCompound} with the given name and a copy of the map
   * given as the backing map.
   * 
   * @param newName
   *          the {@link Tag} name
   * @param newMap
   *          the {@link Map} value
   */
  public TagCompound(final String newName, final Map<String, Tag> newMap) {
    super(newName);
    if (newMap == null) {
      data = new HashMap<String, Tag>();
    } else {
      data = new HashMap<String, Tag>(newMap);
      Iterator<Entry<String, Tag>> it = data.entrySet().iterator();
      while (it.hasNext()) {
        assertValid(it.next().getValue());
      }
    }
  }

  /**
   * Returns an unmodifiable {@link Iterator} for the backing map's values.
   * 
   * @return an unmodifiable {@link Iterator} for the backing map's values
   */
  @Override
  public Iterator<Tag> iterator() {
    return Collections.unmodifiableCollection(data.values()).iterator();
  }

  @Override
  public void clear() {
    List<Tag> toRemove = new ArrayList<Tag>(data.values());
    for (Tag child : toRemove) {
      child.removeFromParent();
    }
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public boolean contains(final Tag tag) {
    if (tag == null) {
      return false;
    }
    return data.values().contains(tag);
  }

  /**
   * Returns an unmodifiable view of this {@link TagCompound}'s backing map.
   * 
   * @return an unmodifiable view of this {@link TagCompound}'s backing map
   */
  public Map<String, Tag> getAsMap() {
    return Collections.unmodifiableMap(data);
  }

  /**
   * Returns true if the backing map contains the key given.
   * 
   * @param name
   *          the name of the {@link Tag} to look for
   * @return true if the backing map contains the key given
   */
  public boolean containsKey(final String name) {
    return data.containsKey(name);
  }

  /**
   * Removes and returns the tag with the name given.
   * 
   * @param name
   *          the name of the {@link Tag} to remove
   * @return the tag with the name given
   */
  public Tag remove(final String name) {
    if (name == null || "".equals(name)) {
      return null;
    }
    Tag result = data.get(name);
    if (result == null) {
      return null;
    }
    result.removeFromParent();
    return result;
  }

  /**
   * Returns the {@link Tag} with the name given.
   * 
   * @param name
   *          the name of the {@link Tag} to get
   * @return the {@link Tag} with the name given
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag> T get(final String name) {
    return (T) data.get(name);
  }

  /**
   * Uses the registered {@link TagConverter} for the type of the tag requested
   * and returns the converted value of the {@link Tag} with the name given.
   * 
   * @param name
   *          the name of the {@link Tag} to get
   * @param axion
   *          an {@link Axion} instance
   * @return the converted value of the {@link Tag} with the name given
   * @throws AxionTagRegistrationException
   *           if no {@link TagConverter} is registered for the tag requested
   */
  public <V> V getValue(final String name, final Axion axion) throws AxionTagRegistrationException {
    return axion.convertToValue(data.get(name));
  }

  /**
   * Uses the registered {@link NBTObjectMapper} for the type given to return a
   * new object from the tag requested.
   * 
   * @param name
   *          the name of the {@link Tag} to get
   * @param type
   *          the class of the object to return
   * @param axion
   *          an {@link Axion} instance
   * @return a new object from the tag requested
   * @throws AxionMapperRegistrationException
   *           if no {@link NBTObjectMapper} is registered for the type given
   */
  public <V> V getValue(final String name, final Class<V> type, final Axion axion) throws AxionMapperRegistrationException {
    return axion.createObjectFrom(data.get(name), type);
  }

  /**
   * Adds a {@link Tag} to this {@link TagCompound}. If a tag exists in this
   * compound with the same name as the given tag, the old tag will be replaced
   * by the new tag.
   * 
   * @param tag
   *          the {@link Tag} to add
   */
  public void put(final Tag tag) {
    assertValid(tag);
    remove(tag.getName());
    tag.addTo(this);
  }

  @Override
  protected void onChildAddition(Tag tag) {
    data.put(tag.getName(), tag);
  }

  @Override
  protected void onChildRemoval(Tag tag) {
    data.remove(tag.getName());
  }

  /**
   * Checks if the {@link Tag} given is not null and has a name.
   * 
   * @param tag
   *          the {@link Tag} to check
   * @return the {@link Tag} given
   * @throws AxionInvalidTagException
   */
  protected Tag assertValid(final Tag tag) throws AxionInvalidTagException {
    if (tag == null) {
      throw new AxionInvalidTagException(this.toString() + " does not support null tags");
    } else if ("".equals(tag.getName())) {
      throw new AxionInvalidTagException(this.toString() + " does not support unnamed tags");
    }
    return tag;
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
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    TagCompound other = (TagCompound) obj;
    if (data == null) {
      if (other.data != null) return false;
    } else if (!data.equals(other.data)) return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data.size() + " entries";
  }

  @Override
  protected void onChildNameChange(final String oldName, final String newName) throws AxionIllegalTagNameException {
    if (newName == null || newName.isEmpty()) {
      throw new AxionIllegalTagNameException("Tag belongs to [" + this.toString() + "] and can not have an empty or null name");
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
