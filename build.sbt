import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import Revolver._

name := Common.baseName

organization := Common.organization

lazy val root = project.in(file(".")).aggregate(client, server)

lazy val client = project.in(file("client"))

lazy val server = project.in(file("server")).settings(
  (resources in Compile) ++= {
    (fastOptJS in(client, Compile)).value
    Seq(
      (artifactPath in(client, Compile, fastOptJS)).value,
      (artifactPath in(client, Compile, packageJSDependencies)).value,
      (artifactPath in(client, Compile, packageScalaJSLauncher)).value
    )
  }
)
