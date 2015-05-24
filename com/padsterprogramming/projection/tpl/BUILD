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
