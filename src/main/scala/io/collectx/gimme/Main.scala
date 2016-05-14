package io.collectx.gimme

object Main {

  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[Get].getName))
  }

}