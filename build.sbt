name := """play-scala-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
libraryDependencies ++= Seq("org.reactivemongo" %% "play2-reactivemongo" % "0.19.1-play27")


// Enables using binary json
play.sbt.routes.RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"