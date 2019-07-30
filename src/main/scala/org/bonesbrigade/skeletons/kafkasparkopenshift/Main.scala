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
    println("kafka-spark-scala starting")
    println(" - brokers: " + brokers)
    println(" - in topic: " + intopic)
    println(" - out topic: " + outtopic)

    /* acquire a SparkSession object */
    val spark = SparkSession
      .builder
      .appName("KafkaSparkOpenShiftScala")
      .getOrCreate()

    /* configure the operations to read the input topic */
    val records = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("subscribe", intopic)
      .load()
      .select(functions.column("value").cast(DataTypes.StringType).alias("value"))
      /*
      * add your data operations here, the raw message is passed along as
      * the alias `value`.
      *
      * for example, to process the message as json and create the
      * corresponding objects you could do the following:
      *
      * .select(functions.from_json(functions.column('value'), msg_struct).alias('json'));
      *
      * the following operations would then access the object and its
      * properties using the name `json`.
      */

    /* configure the output stream */
    val writer = records
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", outtopic)
      .option("checkpointLocation", "/tmp")
      .start()

    /* begin processing the input and output topics */
    writer.awaitTermination()
  }

}
