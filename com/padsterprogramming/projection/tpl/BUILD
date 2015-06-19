# Code for managing the data model spec within Project Ion.
package(
  default_visibility = ["//visibility:public"],
)

TEXT_SRCS = [
  "TextTemplate.java",
  "TextTemplateParser.java",
  "TextTemplateWriter.java",
]

java_library(
  name = "text",
  srcs = TEXT_SRCS,
  deps = [
    "//com/padsterprogramming/projection/model:model",
  ],
)

# TODO - get bazel test working :'(
# bazel run com/padsterprogramming/projection/tpl:test
java_binary(
  name = "test",
  main_class = "com.padsterprogramming.projection.tpl.Test",
  srcs = ["Test.java"],
  deps = [
    ":text",
    "//com/padsterprogramming/projection/model:model",
  ],
  resources = ["test.tpl.txt"],
)
