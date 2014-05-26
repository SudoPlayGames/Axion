package com.sudoplay.axion.spec.tag;

public abstract class Tag implements Cloneable {

  private String name;
  private Tag parent;

  public Tag(final String newName) {
    setName(newName);
  }

  public void setName(final String newName) {
    if (parent != null) {
      parent.onChildNameChange(name, newName);
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
    if (parent != null) {
      parent.onChildRemoval(this);
    }
    parent = null;
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
      return this.getClass().getSimpleName() + "(\"" + name + "\")";
    } else {
      return this.getClass().getSimpleName();
    }
  }

  protected void onChildNameChange(final String oldName, final String newName) {
    // override
  }
  
  protected void onChildRemoval(final Tag tag) {
    // override
  }

  @Override
  public abstract Tag clone();

}
