package com.sudoplay.axion.spec.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

  public TagCompound() {
    this(null, null);
  }

  public TagCompound(final String newName) {
    this(newName, null);
  }

  public TagCompound(final String newName, final Map<String, Tag> newMap) {
    super(newName);
    if (newMap == null) {
      data = new HashMap<String, Tag>();
    } else {
      data = newMap;
      Iterator<Entry<String, Tag>> it = data.entrySet().iterator();
      while (it.hasNext()) {
        assertValid(it.next().getValue());
      }
    }
  }

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

  public Map<String, Tag> getAsMap() {
    return Collections.unmodifiableMap(data);
  }

  public boolean containsKey(final String name) {
    return data.containsKey(name);
  }

  public Tag remove(final String name) {
    Tag result = data.get(name);
    if (result == null) {
      return null;
    }
    result.removeFromParent();
    return result;
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> T get(final String name) {
    return (T) data.get(name);
  }

  public void put(final Tag tag) {
    assertValid(tag).addTo(this);
  }

  @Override
  protected void onChildAddition(Tag tag) {
    data.put(tag.getName(), tag);
  }

  @Override
  protected void onChildRemoval(Tag tag) {
    data.remove(tag.getName());
  }

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
