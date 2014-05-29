package com.sudoplay.axion.tag;

import java.util.Iterator;

/**
 * A {@link ContainerTag} can hold other {@link Tag}s.
 * 
 * @author Jason Taylor
 */
public abstract class ContainerTag extends Tag implements Iterable<Tag> {

  /**
   * Creates a new {@link ContainerTag} with the name given.
   * 
   * @param newName
   *          the name of the new {@link Tag}
   */
  public ContainerTag(String newName) {
    super(newName);
  }

  /**
   * Returns the number of {@link Tag}s that belong to this container.
   * 
   * @return the number of {@link Tag}s that belong to this container
   */
  public abstract int size();

  /**
   * Returns an {@link Iterator} for this container.
   */
  public abstract Iterator<Tag> iterator();

  /**
   * Returns <code>true</code> if this container contains the {@link Tag} given.
   * 
   * @param tag
   *          the tag to look for
   * @return <code>true</code> if this container contains the {@link Tag} given
   */
  public abstract boolean contains(final Tag tag);

  /**
   * Removes all {@link Tag}s from this container.
   */
  public abstract void clear();

  /**
   * Called when a child tag's name changes.
   * 
   * @param oldName
   *          the name before the change
   * @param newName
   *          the name after the change
   */
  protected abstract void onChildNameChange(final String oldName, final String newName);

  /**
   * Called when a child is added to this container tag.
   * 
   * @param tag
   *          tag to add
   */
  protected abstract void onChildAddition(final Tag tag);

  /**
   * Called when a child tag is removed from this container tag.
   * 
   * @param tag
   *          tag to remove
   */
  protected abstract void onChildRemoval(final Tag tag);

}
