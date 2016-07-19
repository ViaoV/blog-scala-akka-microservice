import NativePackagerKeys._

name := """simple-rest-scala"""

maintainer:= "Joe Bellus"

version := "1.0-SNAPSHOT"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

dockerBaseImage in Docker := "java"

fork in run := true


libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-remote" % "2.3.10")
