name := "kafka-spark-openshift-scala"

organization := "org.bonesbrigade"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.11"

mainClass in(Compile, run) := Some( "org.bonesbrigade.skeletons.kafkasparkopenshift.Main" )
