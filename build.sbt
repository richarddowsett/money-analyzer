import play.PlayScala
import play.twirl.sbt.Import.TwirlKeys

name := """money-analyser"""

version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

resolvers += "releases" at "https://oss.sonatype.org/content/repositories/releases/"

TwirlKeys.templateImports += "model._"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

libraryDependencies += "org.mongodb" %% "casbah" % "2.8.1"
