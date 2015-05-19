# Underlying data model used throughout Project Icon
package(
  default_visibility = ["//visibility:public"],
)


TEST_SRCS = glob(["test/**/*.java"])

java_library(
  name = "model",
  srcs = glob(["**/*.java"], exclude = TEST_SRCS),
)

java_test(
  name = "tests",
  srcs = TEST_SRCS,
  size = "small",
  deps = [
    ":model",
    "//third_party:junit4"
  ],
)

# HACK - Run this to execute Junit4 tests until bazel test works.
# bazel run com/padsterprogramming/projection/model:tests_hack
java_binary(
  name = "tests_hack",
  main_class = "com.padsterprogramming.projection.model.test.HackRunner",
  srcs = TEST_SRCS,
  deps = [
    ":model",
    "//third_party:junit4",
  ],
)