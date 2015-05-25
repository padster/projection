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

# Sample genrule for generating model code from a .spec file.
# TODO - put in somewhere common.

java_binary(
  name = "spec_generator",
  main_class = "com.padsterprogramming.projection.spec.SpecGenerator",
  srcs = ["SpecGenerator.java"],
  deps = [
    ":spec",
    "//com/padsterprogramming/projection/model:model",
    "//com/padsterprogramming/projection/tpl:text",
  ],
  resources = [
    "basetype.tpl.txt",
  ],
)


genrule(
  name = "modelspec",
  srcs = ["model.spec"],
  outs = ["Model.java"],
  tools = [ ":spec_generator" ],
  heuristic_label_expansion = 0, # GRRRR
  cmd = "$(location :spec_generator) \"$(SRCS)\" \"$@\" > \"$@\"",
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
