package io.collectx.gimme

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings
import akka.util.ByteString
import com.typesafe.config.ConfigFactory

class Get extends Actor
  with ActorLogging {
 
  import akka.pattern.pipe
  import context.dispatcher
  
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
 
  val conf = ConfigFactory.load()
  val http = Http(context.system)
  
  override def preStart() = {
    http.singleRequest(HttpRequest(uri = conf.getString("gimme.url") ))
      .pipeTo(self)
  }
 
  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      log.info("Got response, body: " + entity.dataBytes.runFold(ByteString(""))(_ ++ _))
      
    case HttpResponse(code, _, _, _) =>
      log.info("Request failed, response code: " + code)
      
    case _  =>
      log.info("I got's nada...")
      
  }
 
}