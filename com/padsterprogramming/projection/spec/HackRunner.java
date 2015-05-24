package com.padsterprogramming.projection.spec;

import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;
import com.padsterprogramming.projection.model.impl.StringMapImpl;

import java.io.IOException;
import java.io.InputStream;

/** Java entry class that can be run, utility to help test code. */
public class HackRunner {
  public static void main(String[] args) throws IOException {
    String path = "com/padsterprogramming/projection/spec/testmodel.spec";
    InputStream input = HackRunner.class.getClassLoader().getResourceAsStream(path);
    StringMapImpl<Type> loaded = (StringMapImpl<Type>) new SpecReader().parseStringMap(input);
    System.out.println("Loaded! " + loaded);
  }
}
