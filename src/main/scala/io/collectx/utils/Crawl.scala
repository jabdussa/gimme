package io.collectx.utils

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings
import akka.util.ByteString


class Crawl extends Actor
  with ActorLogging {
 
  import akka.pattern.pipe
  import context.dispatcher
  
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
 
  val http = Http(context.system)
 
  override def preStart() = {
    http.singleRequest(HttpRequest(uri = "http://akka.io"))
      .pipeTo(self)
  }
 
  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      log.info("Got response, body: " + entity.dataBytes.runFold(ByteString(""))(_ ++ _))
      
    case HttpResponse(code, _, _, _) =>
      log.info("Request failed, response code: " + code)
      
    case _  =>
      log.info("Nada...")
      
  }
 
}