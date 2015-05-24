# Code for managing the data model spec within Project Ion.
package(
  default_visibility = ["//visibility:public"],
)


TEST_SRCS = glob(["test/**/*.java"])

java_library(
  name = "spec",
  srcs = glob(["**/*.java"], exclude = TEST_SRCS),
  deps = [
    "//com/padsterprogramming/projection/model:model",
    "//com/padsterprogramming/projection:jackson",
  ],
)

# HACK - Run this to execute Junit4 tests until bazel test works.
java_binary(
  name = "tests_hack",
  main_class = "com.padsterprogramming.projection.spec.HackRunner",
  srcs = ["HackRunner.java"],
  deps = [
    ":spec",
    "//com/padsterprogramming/projection/model:model",
  ],
  resources = ["testmodel.spec"],
)
