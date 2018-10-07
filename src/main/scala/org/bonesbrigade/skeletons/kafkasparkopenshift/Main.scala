package org.bonesbrigade.skeletons.kafkasparkopenshift

import scala.sys

object Main {

  def getEnv(env: String, default: String) : String = {
    sys.env get env match {
      case Some(s) => s
      case None => default
    }
  }

  def main(args: Array[String]) : Unit = {
    val intopic = getEnv("KAFKA_IN_TOPIC")
    println("kafka in topic " + intopic)
  }

}
