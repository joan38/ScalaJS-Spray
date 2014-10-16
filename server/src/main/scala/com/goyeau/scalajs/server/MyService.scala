package com.goyeau.scalajs.server

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService with DevPageGenerator {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(route)
}

trait PageGenerator {
  def genPage(resource: String, entryPoint: String): Route
}

trait DevPageGenerator extends PageGenerator {
  self: HttpService =>

  def genPage(resource: String, jsEntryPoint: String) = path(resource) {
    get {
      respondWithMediaType(`text/html`) {
        complete {
          <html>
            <head>
              <meta charset="UTF-8"/>
              <title>The Scala.js Tutorial</title>
              <!-- Include JavaScript dependencies -->
              <script type="text/javascript" src="/scalajs-client-jsdeps.js"></script>
              <!-- Include Scala.js compiled code -->
              <script type="text/javascript" src="/scalajs-client-fastopt.js"></script>
            </head>
            <body onload={jsEntryPoint}>
            </body>
          </html>
        }
      }
    }
  } ~
    path("scalajs-client-jsdeps.js")(getFromResource("scalajs-client-jsdeps.js")) ~
    path("scalajs-client-fastopt.js")(getFromResource("scalajs-client-fastopt.js")) ~
    path("scalajs-client-fastopt.js.map")(getFromResource("scalajs-client-fastopt.js.map"))
}

trait ProdPageGenerator extends PageGenerator {
  self: HttpService =>

  def genPage(resource: String, jsEntryPoint: String) = path(resource) {
    get {
      respondWithMediaType(`text/html`) {
        complete {
          <html>
            <head>
              <meta charset="UTF-8"/>
              <title>The Scala.js Tutorial</title>
              <!-- Include JavaScript dependencies -->
              <script type="text/javascript" src="/scalajs-client-jsdeps.js"></script>
              <!-- Include Scala.js compiled code -->
              <script type="text/javascript" src="/scalajs-client-opt.js"></script>
            </head>
            <body onload={jsEntryPoint}>
            </body>
          </html>
        }
      }
    }
  } ~
    path("scalajs-client-jsdeps.js")(getFromResource("scalajs-client-jsdeps.js")) ~
    path("scalajs-client-opt.js")(getFromResource("scalajs-client-opt.js"))
}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {
  self: PageGenerator =>

  val route =
    genPage("", "com.goyeau.scalajs.client.TutorialApp().main()")
}