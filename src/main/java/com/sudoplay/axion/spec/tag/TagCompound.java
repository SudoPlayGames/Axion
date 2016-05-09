package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.tag.AxionIllegalTagNameException;
import com.sudoplay.axion.tag.AxionInvalidTagException;
import com.sudoplay.axion.tag.ContainerTag;
import com.sudoplay.axion.tag.Tag;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * tag.type 10<br> tag.name <code>TAG_Compound</code><br> tag.payload * A sequential list of Named Tags. This array
 * keeps going until a <code>TAG_End</code> is found.<br> * <code>TAG_End</code> end<br> tag.note If there's a nested
 * <code>TAG_Compound</code> within this tag, that one will also have a <code>TAG_End</code>, so simply reading until
 * the next <code>TAG_End</code> will not work. The names of the named tags have to be unique within each
 * <code>TAG_Compound</code> The order of the tags is not guaranteed.
 *
 * @author Jason Taylor
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
   * Creates a new {@link TagCompound} with no name and a copy of the map given as the backing map.
   *
   * @param newMap the {@link Map} value
   */
  public TagCompound(
      final Map<String, Tag> newMap
  ) {
    this(null, newMap);
  }

  /**
   * Creates a new {@link TagCompound} with the given name and an empty backing map.
   *
   * @param newName the {@link Tag} name
   */
  public TagCompound(
      final String newName
  ) {
    this(newName, null);
  }

  /**
   * Creates a new {@link TagCompound} with the given name and a copy of the map given as the backing map.
   *
   * @param newName the {@link Tag} name
   * @param newMap  the {@link Map} value
   */
  public TagCompound(
      final String newName,
      final Map<String, Tag> newMap
  ) {
    super(newName);
    if (newMap == null) {
      data = new HashMap<>();
    } else {
      data = new HashMap<>(newMap);
      for (Entry<String, Tag> stringTagEntry : data.entrySet()) {
        assertValid(stringTagEntry.getValue());
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

  public Stream<Entry<String, Tag>> stream() {
    Set<Entry<String, Tag>> set = data.entrySet();
    return set.stream();
  }

  @Override
  public void clear() {
    List<Tag> toRemove = new ArrayList<>(data.values());
    toRemove.forEach(Tag::removeFromParent);
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public boolean contains(
      final Tag tag
  ) {
    return tag != null && data.values().contains(tag);
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
   * @param name the name of the {@link Tag} to look for
   * @return true if the backing map contains the key given
   */
  public boolean containsKey(
      final String name
  ) {
    return data.containsKey(name);
  }

  /**
   * Removes and returns the tag with the name given.
   *
   * @param name the name of the {@link Tag} to remove
   * @return the tag with the name given
   */
  public Tag remove(
      final String name
  ) {
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
   * @param name the name of the {@link Tag} to get
   * @return the {@link Tag} with the name given
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag> T get(
      final String name
  ) {
    return (T) data.get(name);
  }

  /**
   * Adds a {@link Tag} to this {@link TagCompound}. If a tag exists in this compound with the same name as the given
   * tag, the old tag will be replaced by the new tag.
   *
   * @param tag the {@link Tag} to add
   */
  public void put(
      final Tag tag
  ) {
    assertValid(tag);
    remove(tag.getName());
    tag.addTo(this);
  }

  /**
   * Adds a {@link Tag} to this {@link TagCompound} with the name given.
   *
   * @param name name of the {@link Tag}
   * @param tag  the {@link Tag} to add
   */
  public void put(
      final String name,
      final Tag tag
  ) {
    put(tag.setName(name));
  }

  @Override
  protected void onChildAddition(
      Tag tag
  ) {
    data.put(tag.getName(), tag);
  }

  @Override
  protected void onChildRemoval(
      Tag tag
  ) {
    data.remove(tag.getName());
  }

  /**
   * Checks if the {@link Tag} given is not null and has a name.
   *
   * @param tag the {@link Tag} to check
   * @return the {@link Tag} given
   * @throws AxionInvalidTagException
   */
  protected Tag assertValid(
      final Tag tag
  ) throws AxionInvalidTagException {
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
  public boolean equals(
      Object obj
  ) {
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
  protected void onChildNameChange(
      final String oldName,
      final String newName
  ) throws AxionIllegalTagNameException {
    if (newName == null || newName.isEmpty()) {
      throw new AxionIllegalTagNameException("Tag belongs to [" + this.toString() + "] and can not have an empty or " +
          "null name");
    }
    data.put(newName, data.remove(oldName));
  }

  @SuppressWarnings("CloneDoesntCallSuperClone")
  @Override
  public TagCompound clone() {
    if (data.isEmpty()) {
      return new TagCompound(getName());
    } else {
      Map<String, Tag> newMap = new HashMap<>(data.size());
      for (Entry<String, Tag> entry : data.entrySet()) {
        newMap.put(entry.getKey(), entry.getValue().clone());
      }
      return new TagCompound(getName(), newMap);
    }

  }
}
