# A sample app built with Project Ion.
# Tracks reads publication information for artists/authors,
# and lets users subscribe to receive updates when a new album/book is released.

# bazel run com/padsterprogramming/tracks:server
java_binary(
  name = "server",
  main_class = "com.padsterprogramming.tracks.server.BaseServer",
  srcs = [
    "server/BaseServer.java",
    "//com/padsterprogramming/tracks/model:ArtistStore.java",
    "//com/padsterprogramming/tracks/model:Model.java",
  ],
  deps = [
    "//com/padsterprogramming/projection/model",
    "//com/padsterprogramming/projection/server:server",
  ]
)