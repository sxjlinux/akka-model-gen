name := "akka-model-gen"
organization := "com.github.apuex"
version := "1.0.0"
scalaVersion := "2.12.3"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

libraryDependencies ++= {
  val playVersion = "2.6.5"
  val jodaTimeVersion = "2.9.9"
  Seq(
    "joda-time"                 %   "joda-time"      % jodaTimeVersion
  )
}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.rename
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

mainClass in assembly := Some("com.github.apuex.akka.gen.Main")
assemblyJarName in assembly := "akka-model-gen.jar"


