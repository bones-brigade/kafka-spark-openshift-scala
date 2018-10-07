package org.bonesbrigade.skeletons.kafkasparkopenshift

import scala.sys

object Main {

  def main(args: Array[String]) : Unit = {
    val intopic = sys.env["KAFKA_IN_TOPIC"]
    println("kafka in topic " + intopic)
  }

}
