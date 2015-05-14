package com.padsterprogramming.projection.model.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

/** HACK: use this until bazel test is working :( */
public class HackRunner {
  public static void main(String[] args) {
    for (Failure failure : JUnitCore.runClasses(ImplTests.class).getFailures()) {
      System.out.println(failure.toString());
    }
  }
}
