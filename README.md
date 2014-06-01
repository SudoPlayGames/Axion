Axion is dependent on [SLF4J](http://www.slf4j.org/) for logging.

Looking for the [JavaDoc for v1.0.0](http://codetaylor.github.io/Axion/doc/1.0.0/)?

## Quick Start

### Get an Axion Instance

Axion instances can be set up with different configurations. The two built-in configurations are for the original and extended specification.

```java
// Get the built-in, original specification
Axion axion = Axion.getSpecInstance();

// Get the built-in, extended specification
Axion axion = Axion.getExtInstance();

```

### Read NBT

NBT data can be read from a file or a stream and returned as a `TagCompound`. The data can also be read directly into any class that implements the `AxionWritable<TagCompound>` interface.

```java
// Read a TagCompound from a file
TagCompound tag = axion.read(file);

// Read a TagCompound from an InputStream
TagCompound tag = axion.read(inputStream);

// Read a file into an AxionWritable
AxionWritable<TagCompound> result = axion.read(file, writable);

// Read an InputStream into an AxionWritable
AxionWritable<TagCompound> result = axion.read(inputStream, writable);
```

### Write NBT

NBT data can be written to a file or a stream. The data can be written from a `TagCompound` or directly from any class that implements the `AxionWritable<TagCompound>` interface.

```java
// Write a TagCompound to a file
axion.write(tag, file);

// Write a TagCompound to an OutputStream
axion.write(tag, outputStream);

// Write an AxionWritable to a file
axion.write(writable, file);

// Write an AxionWritable to an OutputStream
axion.write(writable, outputStream);
```

## Customization

### Custom Configurations

Axion configurations specify how an instance reads and writes the NBT data.

Configurations consist of:
* a base tag adapter
* tags
* adapters
* converters
* mappers
* a compression type
* a character encoding type

#### Create

Axion allows custom configurations to be created either from scratch or by duplicating an existing configuration. The built-in configurations can't be changed directly, but they can be duplicated and the duplicates can be altered.

```java
// Create a new, empty configuration
Axion.createInstance("new-instance-name");

// Duplicate an existing configuration
Axion.createInstanceFrom(axionInstance, "new-instance-name");

// Duplicate an existing configuration by name
Axion.createInstanceFrom("existing-instnace-name", "new-instance-name");
```

#### Get

Axion instances are retrieved via the `String` id given during creation.

```java
// Get a custom configuration
Axion.getInstance("instance-name");
```

#### Delete

Instances are deleted also via the `String` id given during creation. Attempting to delete a built-in instance will result in an exception.

```java
// Delete a custom configuration
Axion.deleteInstance("instance-to-delete");
```

---

### Custom Base Tag Adapter

The base tag adapter, registered using a different method than the other adapters, is used by Axion as the entry point for reading all tags. The default base tag adapter is responsible for handling most of the common logic involved in reading and writing tags.

Here is the default base tag adapter used by Axion:

```java
public class BaseTagAdapter extends TagAdapter<Tag> {

  @Override
  public Tag read(Tag parent, AxionInputStream in) throws IOException {
    int id = in.readUnsignedByte();
    return (id == 0) ? null : getAdapterFor(id).read(parent, in);
  }

  @Override
  public void write(Tag tag, AxionOutputStream out) throws IOException {
    int id = getIdFor(tag.getClass());
    out.writeByte(id);
    if (!(tag.getParent() instanceof TagList)) {
      out.writeString(tag.getName());
    }
    getAdapterFor(id).write(tag, out);
  }

}
```

The base tag adapter is registered using:

```java
axion.registerBaseTagAdapter(new BaseTagAdapter());
```

---

### Custom Tags

Custom tags are created by extending the abstract `Tag` class and are registered with an Axion configuration instance along with an id, data type, adapter, and converter.

For example, this is how you might register the `TagByte`:

```java
axion.registerTag(1, TagByte.class, Byte.class, new TagByteAdapter(), new TagByteConverter());
```

#### Adapters

Adapters tell Axion how to read and write the tags. An adapter can be any class that extends the abstract `TagAdapter` class. Due to the way Axion works internally, adapters *should not contain state*. Any adapters that contain state may not behave as expected in multi-threaded situations.

For example, this is the adapter registered for the `TagByte`:

```java
public class TagByteAdapter extends TagAdapter<TagByte> {

  @Override
  public void write(TagByte tag, AxionOutputStream out) throws IOException {
    out.writeByte(tag.get());
  }

  @Override
  public TagByte read(final Tag parent, final AxionInputStream in) throws IOException {
    return convertToTag((parent instanceof TagList) ? null : in.readString(), in.readByte());
  }

}
```

This adapter simply writes the payload of the tag as a single byte. When reading a byte tag it reads a name only if the tag is not a child of a list, then reads a single byte.

#### Converters

Converters tell Axion how to convert data to and from tags. A converter can be any class that extends the abstract `TagConverter` class. Due to the way Axion works internally, converters *should not contain state*. Any converters that contain state may not behave as expected in multi-threaded situations.

For example, this is the converter registered for the `TagByte`:

```java
public class TagByteConverter extends TagConverter<TagByte, Byte> {

  @Override
  public TagByte convert(final String name, final Byte value) {
    return new TagByte(name, value);
  }

  @Override
  public Byte convert(final TagByte tag) {
    return tag.get();
  }

}
```

The `TagByteConverter` class is fairly simple and self-explanatory.

---

### Mappers

Mappers are simply utility classes that you can register with an Axion configuration instance to assist in converting objects to and from NBT tags. Mappers are useful when you don't have access to the objects source or otherwise can't implement the `AxionWritable` interface.

For example, let's say you had a 3rd-party class that looked like this:

```java
public class Vector3f {

  public float x, y, z;

  public Vector3f() {
    //
  }

  public Vector3f(final float x, final float y, final float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

}
```

You could write a mapper to convert a `Vector3f` object into a `TagList` of `TagFloat` like:

```java
public class Vector3fMapper implements NBTObjectMapper<TagList, Vector3f> {

  @Override
  public Vector3f createObjectFrom(TagList tag, Axion axion) {
    Vector3f v = new Vector3f();
    v.x = ((TagFloat) tag.get(0)).get();
    v.y = ((TagFloat) tag.get(1)).get();
    v.z = ((TagFloat) tag.get(2)).get();
    return v;
  }

  @Override
  public TagList createTagFrom(String name, Vector3f value, Axion axion) {
    TagList list = new TagList(TagFloat.class, name);
    list.add(new TagFloat("", value.x));
    list.add(new TagFloat("", value.y));
    list.add(new TagFloat("", value.z));
    return list;
  }

}
```

Then register the mapper like so:

```java
axion.registerNBTObjectMapper(Vector3f.class, new Vector3fMapper());
```

Finally, use the mapper:

```java
Vector3f position = new Vector3f(16f, 2.5f, 65f);

// Convert the Vector3f into a TagList
TagList tagList = axion.createTagFrom("vec1", position);

// Convert the TagList back into a Vector3f
Vector3f result = axion.createObjectFrom(tagList, Vector3f.class);
```

---

### Compression Type

Both built-in configurations use GZip as the default compression type. This is consistent with the original specification of NBT. If you wish to change this, however, Axion configurations can use GZip, Deflater, or no compression.

```java
// GZip compression
axion.setCompressionType(CompressionType.GZip);

// Deflater compression
axion.setCompressionType(CompressionType.Deflater);

// No compression
axion.setCompressionType(CompressionType.None);
```

---

### Character Encoding Type

Both built-in configurations use a modified UTF-8 character encoding for reading and writing all strings. This is consistent with the `DataInputStream#readUTF()` and `DataOutputStream#writeUTF(String string)` provided by Java. If you wish to change this, however, Axion supports the following character encoding types:

```java
public static enum CharacterEncodingType {
  MODIFIED_UTF_8, US_ASCII, ISO_8859_1, UTF_8, UTF_16BE, UTF_16LE, UTF_16
}
```

This can be changed using:

```java
axion.setCharacterEncodingType(CharacterEncodingType.US_ASCII);
```

## AxionWritable Interface

The `AxionWritable` provides an interface for easily creating classes that can be read and written by Axion.

For example, extending the `Vector3f` example used above:

```java
public class Vector3f implements AxionWritable<TagList> {

  public float x, y, z;

  public Vector3f() {
    //
  }

  public Vector3f(final float x, final float y, final float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public TagList write(final Axion axion) {
    TagList out = new TagList(TagFloat.class);
    list.add(new TagFloat("", x));
    list.add(new TagFloat("", y));
    list.add(new TagFloat("", z));
    return out;
  }

  @Override
  public void read(final TagList in, final Axion axion) {
    x = ((TagFloat) in.get(0)).get();
    y = ((TagFloat) in.get(1)).get();
    z = ((TagFloat) in.get(2)).get();
  }

}
```

##License

Copyright (C) 2014 Jason Taylor. Released as open-source under [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).