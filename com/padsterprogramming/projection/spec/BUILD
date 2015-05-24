# Code for managing the data model spec within Project Ion.
package(
  default_visibility = ["//visibility:public"],
)


java_library(
  name = "spec",
  srcs = glob(["**/*.java"], exclude = ["SpecGenerator.java"]),
  deps = [
    "//com/padsterprogramming/projection/model:model",
    "//com/padsterprogramming/projection:jackson",
  ],
)

# HACK - Run this to execute Junit4 tests until bazel test works.
java_binary(
  name = "gen",
  main_class = "com.padsterprogramming.projection.spec.SpecGenerator",
  srcs = ["SpecGenerator.java"],
  deps = [
    ":spec",
    "//com/padsterprogramming/projection/model:model",
    "//com/padsterprogramming/projection/tpl:text",
  ],
  resources = [
    "basetype.tpl.txt",
    "model.spec",
  ],
)
