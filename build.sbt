val sparkVersion = "2.3.0"

name := "kafka-spark-scala"

organization := "org.bonesbrigade"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.11"

mainClass in assembly := Some( "org.bonesbrigade.skeletons.kafkasparkopenshift.Main" )

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-sql_2.11" % sparkVersion % "provided",
  "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % sparkVersion % "provided"
)
