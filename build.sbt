name := "scala-roguelike"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.102-R11",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.lwjgl" % "lwjgl" % "3.1.2"
)

lazy val (os, separator) = System.getProperty("os.name").split(" ")(0).toLowerCase match {
  case "linux" => "linux" -> ":"
  case "mac" => "macosx" -> ":"
  case "windows" => "windows" -> ";"
  case "sunos" => "solaris" -> ":"
  case x => x -> ":"
}

javaOptions ++= Seq("-Djava.library.path=" + System.getProperty("java.library.path") + separator + s"lib/native/$os")

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)



    