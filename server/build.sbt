Revolver.settings

name := Common.baseName + "-server"

organization := Common.organization

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalatest" %%  "scalatest" % "2.2.1" % "test",
  "com.lihaoyi" %% "autowire" % "0.2.3",
  "io.spray" %% "spray-can" % "1.3.1",
  "io.spray" %% "spray-routing" % "1.3.1",
  "com.typesafe.akka" %% "akka-actor" % "2.3.2",
  "com.scalatags" %% "scalatags" % "0.4.1"
)
