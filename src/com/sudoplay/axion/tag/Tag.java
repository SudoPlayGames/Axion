package com.sudoplay.axion.tag;


public abstract class Tag {

  private String name;
  private Tag parent;

  public Tag(final String newName) {
    setName(newName);
  }

  public void setName(final String newName) {
    if (parent != null) {
      parent.onNameChange(name, newName);
    }
    name = (newName == null) ? "" : newName;
  }

  public String getName() {
    return (name == null) ? "" : name;
  }

  protected void setParent(final Tag newParent) {
    parent = newParent;
  }

  public Tag getParent() {
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
    Tag other = (Tag) obj;
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

  protected void onNameChange(final String oldName, final String newName) {
    // override
  }

  public abstract byte getTagId();

  public abstract String getTagName();

}
