import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import Revolver._
import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

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

//val moveFullOptResources = Def.task {
//  (fullOptJS in(client, Compile)).value
//  IO.copy(Seq(
//    (artifactPath in(client, Compile, fullOptJS)).value,
//    (artifactPath in(client, Compile, packageJSDependencies)).value,
//    (artifactPath in(client, Compile, packageScalaJSLauncher)).value
//  ) map { file =>
//    (file, new File((resourceDirectory in (server, Compile)).value, file.getName))
//  })
//}
//
//run := (run in(server, Compile) dependsOn moveFullOptResources).evaluated


//lazy val `re-start-dev` = inputKey[Unit]("Run in development")
//
//`re-start-dev` := (reStart in server).evaluated
//
//`re-start-dev` <<= `re-start-dev` dependsOn moveFastOptResources

////reStart <<= (reStart in(server, Compile, reStart)) dependsOn (fastOptJS in(client, Compile))
//
//
//
//lazy val `re-start-prod` = inputKey[Unit]("Run in production")
//
//`re-start-prod` := (reStart in server dependsOn (fullOptJS in(client, Compile))).evaluated
//
//(resources in(server, Compile, `re-start-prod`)) ++= Seq(
//  (artifactPath in(client, Compile, fullOptJS)).value,
//  (artifactPath in(client, Compile, packageJSDependencies)).value,
//  (artifactPath in(client, Compile, packageScalaJSLauncher)).value
//)

/////////////////////////////////////////////////
//(run in server) <<= (run in(server, Compile)) dependsOn (fullOptJS in(client, Compile))

//run := (run in(server, Compile)).evaluated
//
//run in server <<= (run in(server, Compile)) dependsOn (fastOptJS in(client, Compile))
//
//(resources in(server, Compile, run)) ++= Seq(
//  (artifactPath in(client, Compile, fastOptJS)).value,
//  (artifactPath in(client, Compile, packageJSDependencies)).value,
//  (artifactPath in(client, Compile, packageScalaJSLauncher)).value
//)
//
//lazy val runProd = inputKey[Unit]("Run in production")
//
//runProd := (run in(server, Compile)).evaluated
//
//runProd <<= runProd dependsOn (fullOptJS in(client, Compile))
//
//(resources in(server, Compile, runProd)) ++= Seq(
//  (artifactPath in(client, Compile, fullOptJS)).value,
//  (artifactPath in(client, Compile, packageJSDependencies)).value,
//  (artifactPath in(client, Compile, packageScalaJSLauncher)).value
//)