package controllers

import play.api.Play.current
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import akka.actor._
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import scala.concurrent.Future
import shared.services.messages.TestPrimality

object Application extends Controller {

  val remote = Akka.system.actorSelection("akka.tcp://PrimeService@127.0.0.1:5150/user/PrimeActor")
  implicit val timeout:Timeout  = Timeout(5 seconds)

  def primeTest = Action.async { request =>
    request.body.asJson.map { json =>
      val n = (json \ "data" \ "number").as[Int]
      val future  = (remote ? TestPrimality(n)).mapTo[Boolean]
      future map { result =>
        Ok(Json.obj("result" -> result))
      }
    }.getOrElse(Future(Ok("Bad Request")))
  }
}
