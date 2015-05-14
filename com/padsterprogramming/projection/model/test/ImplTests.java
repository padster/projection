package com.padsterprogramming.projection.model.test;

import com.padsterprogramming.projection.model.Primitive;
import com.padsterprogramming.projection.model.impl.ListImpl;
import com.padsterprogramming.projection.model.impl.PrimitiveImpl;
import com.padsterprogramming.projection.model.impl.StringMapImpl;
import com.padsterprogramming.projection.model.util.Primitives;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/** Simple test suite for model implementation classes. */
@RunWith(JUnit4.class)
public class ImplTests {

  @Test
  public void intPrimitiveImpl() {
    // PICK: Have special unboxed primitives?
    PrimitiveImpl<Integer> value = new PrimitiveImpl<>(4);
    assertEquals(4, (int)value.get());
    assertEquals(4, (int)value.getOrDefault(-1));

    value.set(-10000);
    assertEquals(-10000, (int)value.get());

    value.set(null);
    assertNull(value.get());
    assertEquals(10, (int)value.getOrDefault(10));
  }

  @Test
  public void stringPrimitiveImpl() {
    PrimitiveImpl<String> value = new PrimitiveImpl<String>();
    assertNull(value.get());
    assertEquals("X", value.getOrDefault("X"));

    value.set(null);
    assertNull(value.get());
    assertEquals(null, value.getOrDefault(null));

    value.set("ProjectIon");
    assertEquals("ProjectIon", value.get());
    assertEquals("ProjectIon", value.getOrDefault("X"));
  }

  @Test
  public void listImpl() {
    ListImpl<Primitive<Boolean>> value = new ListImpl<>();
    assertEquals(0, value.size());

    value.append(Primitives.of(true));
    assertEquals(1, value.size());
    assertTrue(value.get(0).get());

    value.insert(0, Primitives.of(false));
    assertEquals(2, value.size());
    assertFalse(value.get(0).get());
    assertTrue(value.get(1).get());
    assertNotNull(value.getOrDefault(0, null));

    value.set(1, null);
    assertEquals(2, value.size());
    assertNull(value.get(1));
    assertTrue(value.getOrDefault(1, Primitives.of(true)).get());

    value.remove(0);
    assertEquals(1, value.size());
    assertNull(value.get(0));
  }

  @Test
  public void stringMapImpl() {
    StringMapImpl<Primitive<String>> value = new StringMapImpl<>();
    assertEquals(0, value.size());
    assertEquals("oops", value.getOrDefault("A", Primitives.of("oops")).get());

    value.set("P", Primitives.of(""));
    assertEquals(1, value.size());
    assertEquals("", value.get("P").get());
    assertEquals("", value.getOrDefault("P", Primitives.of("Empty is not null")).get());

    value.set("Q", Primitives.of("LONGER"));
    assertEquals(2, value.size());
    assertEquals("LONGER", value.get("Q").get());
    assertEquals("", value.get("P").get());

    value.remove("P");
    assertEquals(1, value.size());
    assertNull(value.get("P"));
    assertEquals("Yay", value.getOrDefault("P", Primitives.of("Yay")).get());
  }
}
