# Run a server for a Project Ion App

RESOURCE_FILES = glob(["resources/**/*"])


# bazel run com/padsterprogramming/projection/model:tests_hack
java_binary(
  name = "server",
  main_class = "com.padsterprogramming.projection.server.BaseServer",
  srcs = ["BaseServer.java"],
  resources = RESOURCE_FILES
)


# Mostly working. From com/padsterprogramming/projection run:
# java -cp server/gwt-user.jar:server/gwt-dev.jar:/home/pat/code/java/projection/bazel-bin/com/padsterprogramming/projection/server/client_src_deploy-src.jar:/home/pat/code/java/projection/bazel-bin/com/padsterprogramming/projection/server/client_src.jar:/home/pat/code/java/projection/bazel-bin/com/padsterprogramming/projection/model/libmodel.jar
# com.google.gwt.dev.Compiler com.padsterprogramming.projection.server.Module
# Writes out to /war/Client/... so far.
# Need to:
# 1) Automate paths for the input jars
# 2) Make it into a genrule that generates the source jar
# 3) Load it with resources above.
java_library(
  name = "client",
  srcs = ["client/BaseClient.java"],
  deps = [
    ":gwt-dev",
    ":gwt-user",
    "//com/padsterprogramming/projection/model:model",
  ],
)

java_binary(
  name = "client_src",
  # BIG HACK - this is ignored, as GWT doesn't need to read the source.
  main_class = "com.padsterprogramming.projection.server.BaseServer",
  srcs = ["BaseServer.java"],
  deps = [
    ":client",
  ],
  resources = ["Module.gwt.xml"],
)


# Pull in the GWT code so the client entry-point compiles.
java_import(
  name = "gwt-dev",
  jars = ["gwt-dev.jar"],
)
java_import(
  name = "gwt-user",
  jars = ["gwt-user.jar"],
)
