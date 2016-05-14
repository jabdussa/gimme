package io.collectx.utils

object Main {

  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[Crawl].getName))
  }

}