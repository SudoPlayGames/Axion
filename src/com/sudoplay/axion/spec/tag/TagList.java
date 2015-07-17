package com.sudoplay.axion.spec.tag;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.mapper.AxionMapper;
import com.sudoplay.axion.mapper.AxionMapperRegistrationException;
import com.sudoplay.axion.registry.AxionTagRegistrationException;
import com.sudoplay.axion.registry.TagConverter;
import com.sudoplay.axion.tag.AxionIllegalTagNameException;
import com.sudoplay.axion.tag.AxionInvalidTagException;
import com.sudoplay.axion.tag.ContainerTag;
import com.sudoplay.axion.tag.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jason Taylor
 * @tag.type 9
 * @tag.name <code>TAG_List</code>
 * @tag.payload * <code>TAG_Byte</code> tagId<br> * <code>TAG_Int</code> length<br> * A sequential list of Tags (not
 * Named Tags), of type <code>typeId</code>. The length of this array is <code>length</code> Tags.
 * @tag.note All tags share the same type.
 */
public class TagList extends ContainerTag {

  private final List<Tag> data;

  /**
   * Stores the type for tags in this list; all tags must be of the same type.
   */
  private final Class<? extends Tag> type;

  /**
   * Creates a new {@link TagList} of the given type with no name and an empty backing list.
   *
   * @param tagClass the list type
   */
  public TagList(final Class<? extends Tag> tagClass) {
    this(tagClass, null, new ArrayList<>());
  }

  /**
   * Creates a new {@link TagList} of the given type with no name and a copy of the given list as the backing list.
   *
   * @param tagClass the list type
   * @param newList  the list
   */
  public TagList(final Class<? extends Tag> tagClass, final List<Tag> newList) {
    this(tagClass, null, newList);
  }

  /**
   * Creates a new {@link TagList} of the given type with the given name and an empty backing list.
   *
   * @param tagClass the list type
   * @param newName  the {@link Tag} name
   */
  public TagList(final Class<? extends Tag> tagClass, final String newName) {
    this(tagClass, newName, new ArrayList<>());
  }

  /**
   * Creates a new {@link TagList} of the given type with the given name and a copy of the given list as the backing
   * list.
   *
   * @param tagClass the list type
   * @param newName  the {@link Tag} name
   * @param newList  the list
   */
  public TagList(final Class<? extends Tag> tagClass, final String newName, final List<Tag> newList) {
    super(newName);
    type = tagClass;
    if (newList == null || newList.isEmpty()) {
      data = new ArrayList<>();
    } else {
      data = new ArrayList<>(newList);
      for (Tag tag : data) {
        assertValid(tag);
        tag.setName("");
      }
    }
  }

  /**
   * Adds tag to the end of the list. If the tag to be added does not match this list's type, an exception is thrown.
   *
   * @param tag the {@link Tag} to add
   */
  public void add(final Tag tag) {
    assertValid(tag).addTo(this);
  }

  /**
   * Converts the value given into a tag using the {@link TagConverter} registered for the value's type and adds the new
   * tag to this {@link TagList}.
   *
   * @param value the value to convert
   * @param axion an {@link Axion} instance
   * @throws AxionTagRegistrationException if no {@link TagConverter} is registered for the value's type
   */
  public <V> void addValue(final V value, final Axion axion) throws AxionTagRegistrationException {
    add(axion.createTagWithConverter(null, value));
  }

  /**
   * Creates a tag from the mappable value given using the {@link AxionMapper} registered for the value's type and adds
   * the new tag to this {@link TagList}.
   *
   * @param value the value to map
   * @param axion an {@link Axion} instance
   * @throws AxionMapperRegistrationException if no {@link AxionMapper} is registered for the value's type
   */
  public <V> void addMappableValue(final V value, final Axion axion) throws AxionMapperRegistrationException {
    add(axion.createTagWithMapper(null, value));
  }

  /**
   * Removes a {@link Tag} from this list.
   *
   * @param tag the {@link Tag} to remove
   * @return true if the given {@link Tag} was found and removed
   */
  public boolean remove(final Tag tag) {
    if (contains(tag)) {
      tag.removeFromParent();
      return true;
    }
    return false;
  }

  /**
   * Removes a {@link Tag} from this list via its index.
   *
   * @param index the index of the {@link Tag} to remove
   * @return the {@link Tag} removed
   */
  public Tag remove(final int index) {
    Tag removed = data.get(index);
    removed.removeFromParent();
    return removed;
  }

  /**
   * Checks if this {@link TagList} contains the {@link Tag} passed in.
   * <p>
   * Since names are stripped of tags in lists, this method compares values only; tags with different names and
   * identical values will match.
   *
   * @param tag tag whose presence in this list is to be tested
   * @return true if this list contains a tag with a matching value
   */
  @Override
  public boolean contains(final Tag tag) {
    if (tag == null || !tag.getClass().isAssignableFrom(type)) {
      return false;
    }
    /*
     * If we don't strip off its name here, it will not satisfy equals(). We
     * also clone it because we don't want to alter the original.
     */
    return data.contains(tag.clone().setName(""));
  }

  @Override
  public Iterator<Tag> iterator() {
    return Collections.unmodifiableList(data).iterator();
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> Stream<T> stream(@SuppressWarnings("UnusedParameters") Class<T> tagClass) {
    /*
    TODO:
    How can we ensure that stream lambda parameters are the proper type without having to provide the tag class in
    the method call?
     */
    return (Stream<T>) data.stream();
  }

  public <V, T extends Tag> Stream<V> valueStream(
      Class<T> tagClass,
      Class<V> valueClass,
      Axion axion
  ) {
    return this.valueStream(
        tagClass,
        tag -> true,
        tag -> axion.createValueFromTag(tag, valueClass),
        ArrayList::new
    );
  }

  public <V, T extends Tag> Stream<V> valueStream(
      Class<T> tagClass,
      Predicate<T> filter,
      Function<T, V> map,
      Supplier<List<V>> listSupplier
  ) {
    return this
        .stream(tagClass)
        .filter(filter)
        .map(map)
        .collect(Collectors.toCollection((Supplier<List<V>>) listSupplier))
        .stream();
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public void clear() {
    new ArrayList<>(data).forEach(Tag::removeFromParent);
  }

  /**
   * Returns the type of this {@link TagList}.
   *
   * @return the type of this {@link TagList}
   */
  public Class<? extends Tag> getType() {
    return type;
  }

  /**
   * Returns an unmodifiable view of the backing list.
   *
   * @return an unmodifiable view of the backing list
   */
  public List<Tag> getAsList() {
    return Collections.unmodifiableList(data);
  }

  /**
   * Returns a {@link Tag} from the backing list at the index given.
   *
   * @param index the index of the {@link Tag} to get
   * @return a {@link Tag} from the backing list at the index given
   */
  @SuppressWarnings("unchecked")
  public <T extends Tag> T get(final int index) {
    return (T) data.get(index);
  }

  /**
   * Uses the registered {@link TagConverter} for the type of the tag requested and returns the converted value of the
   * {@link Tag} with the index given.
   *
   * @param index the index of the {@link Tag} to get
   * @param axion an {@link Axion} instance
   * @return the converted value of the {@link Tag} with the index given
   * @throws AxionTagRegistrationException if no {@link TagConverter} is registered for the tag requested
   */
  public <V> V getValue(final int index, final Axion axion) throws AxionTagRegistrationException {
    return axion.createValueFromTag(data.get(index));
  }

  /**
   * Uses the registered {@link AxionMapper} for the type given to return a new object from the tag requested.
   *
   * @param index the index of the {@link Tag} to get
   * @param type  the class of the object to return
   * @param axion an {@link Axion} instance
   * @return a new object from the tag requested
   * @throws AxionMapperRegistrationException if no {@link AxionMapper} is registered for the type given
   */
  public <V> V getValue(final int index, final Class<V> type, final Axion axion) throws
      AxionMapperRegistrationException {
    return axion.createValueFromTag(data.get(index), type);
  }

  @Override
  protected void onChildAddition(Tag tag) {
    tag.setName(null);
    data.add(tag);
  }

  @Override
  protected void onChildRemoval(Tag tag) {
    data.remove(tag);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    TagList other = (TagList) obj;
    if (data == null) {
      if (other.data != null) return false;
    } else if (!data.equals(other.data)) return false;
    if (type == null) {
      if (other.type != null) return false;
    } else if (!type.equals(other.type)) return false;
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + ": " + data.size() + " entries of type " + type.getSimpleName();
  }

  @Override
  protected void onChildNameChange(final String oldName, final String newName) throws AxionIllegalTagNameException {
    if (newName != null && !newName.isEmpty()) {
      throw new AxionIllegalTagNameException("Tag belongs to a " + TagList.class.getSimpleName() + " and can't be " +
          "named");
    }
  }

  @SuppressWarnings("CloneDoesntCallSuperClone")
  @Override
  public TagList clone() {
    if (data.isEmpty()) {
      return new TagList(type, getName(), new ArrayList<>());
    } else {
      List<Tag> newList = new ArrayList<>(data.size());
      newList.addAll(data.stream().map(Tag::clone).collect(Collectors.toList()));
      return new TagList(type, getName(), newList);
    }
  }

  /**
   * Checks if the {@link Tag} given is not null and of this list's type.
   *
   * @param tag the {@link Tag} to check
   * @return the {@link Tag} given
   * @throws AxionInvalidTagException
   */
  protected Tag assertValid(final Tag tag) throws AxionInvalidTagException {
    if (tag == null) {
      throw new AxionInvalidTagException(this.toString() + " can't contain null tags");
    } else if (type != tag.getClass()) {
      throw new AxionInvalidTagException("Can't add tag of type [" + tag.getClass().getSimpleName() + "] to " + this
          .toString());
    }
    return tag;
  }

}
