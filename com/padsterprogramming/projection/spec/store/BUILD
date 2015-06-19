# Data storage generation from model spec.
package(
  default_visibility = ["//visibility:public"],
)

# Sample genrule for generating model code from a .spec file.

java_binary(
  name = "store_generator",
  main_class = "com.padsterprogramming.projection.spec.store.StoreGenerator",
  srcs = ["StoreGenerator.java"],
  deps = [
    "//com/padsterprogramming/projection/model:model",
    "//com/padsterprogramming/projection/spec:spec",
    "//com/padsterprogramming/projection/tpl:text",
  ],
  resources = [
    "store.tpl.txt",
  ],
)

