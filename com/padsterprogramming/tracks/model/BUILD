# Model generation tools for Tracks.

package(
  default_visibility = ["//com/padsterprogramming/tracks:__subpackages__"],
)

genrule(
  name = "modelspec",
  srcs = ["model.spec"],
  outs = ["Model.java"],
  tools = [ "//com/padsterprogramming/projection/spec:spec_generator" ],
  heuristic_label_expansion = 0, # GRRRR
  cmd = "$(location //com/padsterprogramming/projection/spec:spec_generator) \"$(SRCS)\" \"$@\" > \"$@\"",
)

genrule(
  name = "artiststore",
  srcs = ["model.spec"],
  outs = ["ArtistStore.java"],
  tools = [ "//com/padsterprogramming/projection/spec/store:store_generator" ],
  heuristic_label_expansion = 0, # GRRRR
  cmd = "$(location //com/padsterprogramming/projection/spec/store:store_generator) \"$(SRCS)\" \"$@\" \"Artist\" > \"$@\"",
)