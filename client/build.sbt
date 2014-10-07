scalaJSSettings

name := Common.baseName + "-client"

organization := Common.organization

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6",
  "com.lihaoyi" %%% "autowire" % "0.2.3"
)

ScalaJSKeys.jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM

skip in ScalaJSKeys.packageJSDependencies := false

// uTest settings
utest.jsrunner.Plugin.utestJsSettings

ScalaJSKeys.persistLauncher in Compile := true

ScalaJSKeys.persistLauncher in Test := false
