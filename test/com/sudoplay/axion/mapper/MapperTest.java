package com.sudoplay.axion.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sudoplay.axion.Axion;
import com.sudoplay.axion.mapper.NBTObjectMapper;
import com.sudoplay.axion.spec.tag.TagFloat;
import com.sudoplay.axion.spec.tag.TagList;

public class MapperTest {

  @BeforeClass
  public static void before() {
    Axion.createFrom(Axion.getExt(), "vectorTest");
    Axion.get("vectorTest").getConfiguration().registerNBTObjectMapper(Vector3f.class, new Vector3fMapper());
  }
  
  @AfterClass
  public static void after() {
    Axion.destroy("vectorTest");
  }

  @Test
  public void test() {

    Axion axion = Axion.get("vectorTest");

    Vector3f position = new Vector3f(16f, 2.5f, 65f);
    TagList tagList = axion.createTagFrom("vec1", position);
    Vector3f result = axion.createObjectFrom(tagList, Vector3f.class);

    assertEquals(position, result);
  }

  static class Vector3f {

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
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Float.floatToIntBits(x);
      result = prime * result + Float.floatToIntBits(y);
      result = prime * result + Float.floatToIntBits(z);
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
      Vector3f other = (Vector3f) obj;
      if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
        return false;
      if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
        return false;
      if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
        return false;
      return true;
    }

  }

  static class Vector3fMapper implements NBTObjectMapper<TagList, Vector3f> {

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

}
