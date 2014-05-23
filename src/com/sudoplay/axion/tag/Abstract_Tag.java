package com.sudoplay.axion.tag;

import com.sudoplay.axion.tag.impl.TagCompound;
import com.sudoplay.axion.tag.impl.TagList;

public abstract class Abstract_Tag implements Interface_Tag {

  private String name;
  private Abstract_Tag parent;

  public Abstract_Tag(final String newName) {
    setName(newName);
  }

  public void setName(final String newName) {
    if (parent instanceof TagList && newName != null && !newName.isEmpty()) {
      throw new IllegalStateException(this.getClass().getSimpleName() + " belongs to a " + TagList.TAG_NAME + " and can not be named");
    } else if (parent instanceof TagCompound && (newName == null || newName.isEmpty())) {
      throw new IllegalStateException(this.getClass().getSimpleName() + " belongs to a " + TagCompound.TAG_NAME + " and can not have an empty or null name");
    } else {
      name = (newName == null) ? "" : newName;
    }
  }

  public String getName() {
    return (name == null) ? "" : name;
  }

  public void setParent(final Abstract_Tag newParent) {
    parent = newParent;
  }

  public Abstract_Tag getParent() {
    return parent;
  }
  
  public boolean hasParent() {
    return parent != null;
  }

  public void removeFromParent() {
    if (parent instanceof TagList) {
      ((TagList) parent).remove(this);
      parent = null;
    } else if (parent instanceof TagCompound) {
      ((TagCompound) parent).remove(getName());
      parent = null;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Abstract_Tag other = (Abstract_Tag) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    if (!name.equals("") || name.equals(null)) {
      return "(\"" + name + "\")";
    } else {
      return "";
    }
  }

}
