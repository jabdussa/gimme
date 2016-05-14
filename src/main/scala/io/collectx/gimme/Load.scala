package io.collectx.gimme
  
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings
import com.typesafe.config.ConfigFactory

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scala.io.Source
 
import scala.concurrent.Future

case class HtmlMsg(data: String)

class Load extends Actor
  with ActorLogging {
 
  import akka.pattern.pipe
  import context.dispatcher
 
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
 
  val conf = ConfigFactory.load()
  val data = Source.fromFile(conf.getString("gimme.parse.sample_html")).getLines.mkString
  
  override def preStart() = {
    log.info(conf.getString("gimme.hello"))
    log.info("Load Settings")
    log.info("Manufacturers CSS Selector: {}", conf.getString("gimme.parse.manufacturers"))
    log.info("Sample HTML Data: {}", conf.getString("gimme.parse.sample_html"))     
    Future(HtmlMsg(data)).pipeTo(self)
  }
 
  def receive = {
    case h: HtmlMsg if ! h.data.isEmpty =>
      log.info("Received HtmlMsg {} bytes", + h.data.length())      
    case _  =>
      log.info("I got's nada...")
  }
 
}