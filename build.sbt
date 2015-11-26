name := "DiceChat"

version := "1.0"

lazy val `dicechat` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

// required because specs2 seems to rely on scalaz-stream_2.11;0.7a which is not available elsewhere
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(cache , specs2 % Test )

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
