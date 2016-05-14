name := "gimme"
 
version := "1.0"
 
scalaVersion := "2.11.7"

libraryDependencies ++= 
Seq(	
	 "com.typesafe.akka" %% "akka-actor" % "2.4.4"
	,"com.typesafe.akka" %% "akka-http-core" % "2.4.4"
	,"com.typesafe.akka" %% "akka-slf4j" % "2.4.4"
	,"ch.qos.logback" % "logback-classic" % "1.1.7"
)
