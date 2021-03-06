logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.4")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.3")
