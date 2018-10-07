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
    val brokers = getEnv("KAFKA_BROKERS", "localhost:9092")
    val intopic = getEnv("KAFKA_IN_TOPIC", "topic1")
    val outtopic = getEnv("KAFKA_OUT_TOPIC", "topic2")
    println("kafka-spark-openshift-scala starting")
    println(" - brokers: " + brokers)
    println(" - in topic: " + intopic)
    println(" - out topic: " + outtopic)
  }

}
