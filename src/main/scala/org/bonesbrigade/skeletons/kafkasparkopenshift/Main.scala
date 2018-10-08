package org.bonesbrigade.skeletons.kafkasparkopenshift

import scala.sys
import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes

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

    val spark = SparkSession
      .builder
      .appName("KafkaSparkOpenShiftScala")
      .getOrCreate()

    val records = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("subscribe", intopic)
      .load()
      .select(functions.column("value").cast(DataTypes.StringType).alias("value"))

    val writer = records
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", outtopic)
      .option("checkpointLocation", "/tmp")
      .start()

    writer.awaitTermination()
  }

}
