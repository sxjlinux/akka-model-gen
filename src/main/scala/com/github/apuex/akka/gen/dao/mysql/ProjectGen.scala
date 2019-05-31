package com.github.apuex.akka.gen.dao.mysql

import java.io.{File, PrintWriter}

import com.github.apuex.akka.gen.ModelLoader

object ProjectGen {
  def apply(model: String): ProjectGen = new ProjectGen(ModelLoader(model))

  def apply(model: ModelLoader): ProjectGen = new ProjectGen(model)
}

class ProjectGen(model: ModelLoader) {

  import model._

  def generate(): Unit = {
    cluster
    dao
    dbschema
    domain
    message
    project
    rest
    root
    service
    webapp
  }


  def cluster(): Unit = {
    val dir = "cluster"
    new File(dir).mkdirs()
    val pw = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    pw.println(
      s"""
         |import Dependencies._
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |parallelExecution in Test := false
         |fork := true
         |
         |libraryDependencies ++= {
         |  Seq(
         |    akkaActor,
         |    akkaStream,
         |    akkaPersistence,
         |    akkaCluster,
         |    akkaClusterTools,
         |    akkaClusterSharding,
         |    akkaSlf4j,
         |    playGuice,
         |    akkaPersistenceCassandra % Test,
         |    jodaTime % Test,
         |    slf4jApi % Test,
         |    slf4jSimple % Test,
         |    scalaTest % Test,
         |    akkaTestkit % Test,
         |    akkaMultiNodeTestKit % Test,
         |    scalaTestPlusPlay % Test
         |  )
         |}
         |
         |publishTo := localRepo
       """.stripMargin.trim)
    pw.close()
  }

  def dao(): Unit = {
    val dir = "dao-mysql"
    new File(dir).mkdirs()
    val pw = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    pw.println(
      s"""
         |import Dependencies._
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |libraryDependencies ++= Seq(
         |  jdbc,
         |  playAnorm,
         |  playSlick,
         |  mysqlDriver,
         |  slf4jApi % Test,
         |  slf4jSimple % Test,
         |  scalaTest % Test,
         |  scalaTestPlusPlay % Test
         |)
         |
         |publishTo := localRepo
      """.stripMargin.trim)
    pw.close()
  }

  def dbschema(): Unit = {
    val dir = "dbschema"
    new File(dir).mkdirs()
    val pw = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    pw.println(
      s"""
         |import Dependencies._
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |publishTo := localRepo
      """.stripMargin.trim)
    pw.close()
  }

  def domain(): Unit = {
    val dir = "domain"
    new File(dir).mkdirs()
    val pw = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    pw.println(
      s"""
         |import Dependencies._
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |libraryDependencies ++= {
         |  Seq(
         |    akkaActor,
         |    akkaStream,
         |    akkaPersistence,
         |    akkaSlf4j,
         |    jodaTime,
         |    akkaPersistenceCassandra,
         |    playGuice,
         |    slf4jApi % Test,
         |    slf4jSimple % Test,
         |    akkaTestkit % Test,
         |    scalaTest % Test
         |  )
         |}
         |
         |publishTo := localRepo
      """.stripMargin.trim)
    pw.close()
  }

  def message(): Unit = {
    val dir = "message"
    new File(dir).mkdirs()
    val pw = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    pw.println(
      s"""
         |import Dependencies._
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"
         |libraryDependencies ++= Seq(
         |  playGuice,
         |  jodaTime,
         |  akkaRemote,
         |  akkaParsing,
         |  akkaSlf4j,
         |  queryRuntime,
         |  scalapbRuntime % "protobuf",
         |  slf4jApi % Test,
         |  slf4jSimple % Test,
         |  scalaTestPlusPlay % Test
         |)
         |
         |PB.targets in Compile := Seq(
         |  scalapb.gen() -> (sourceManaged in Compile).value
         |)
         |
         |publishTo := localRepo
      """.stripMargin.trim)
    pw.close()
  }

  def project(): Unit = {
    val dir = "project"
    new File(dir).mkdirs()

    val buildProps = new PrintWriter(s"${dir}/build.properties", "utf-8")
    buildProps.println(
      s"""
         |sbt.version=1.2.8
      """.stripMargin.trim)
    buildProps.close()

    val plugins = new PrintWriter(s"${dir}/plugins.sbt", "utf-8")
    plugins.println(
      s"""
         |addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")
         |addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.2")
         |addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")
         |addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.20")
         |libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.0-M5"
      """.stripMargin.trim)
    plugins.close()

    val dependencies = new PrintWriter(s"${dir}/Dependencies.scala", "utf-8")
    dependencies.println(
      s"""
         |import sbt._
         |import scalapb.compiler.Version.scalapbVersion
         |
         |object Dependencies {
         |  lazy val scalaVersionNumber    = "2.12.8"
         |  lazy val akkaVersion           = "2.5.22"
         |  lazy val artifactVersionNumber = "1.0.0"
         |  lazy val artifactGroupName      = "${modelPackage}"
         |  lazy val sprayVersion          = "1.3.5"
         |  lazy val playVersion           = "2.7.2"
         |
         |  lazy val scalaXml        = "org.scala-lang.modules"    %%  "scala-xml"                          % "1.0.6"
         |  lazy val message         = artifactGroupName           %%  "message"                            % artifactVersionNumber
         |  lazy val domain          = artifactGroupName           %%  "domain"                             % artifactVersionNumber
         |  lazy val service         = artifactGroupName           %%  "service"                            % artifactVersionNumber
         |  lazy val rest            = artifactGroupName           %%  "rest"                               % artifactVersionNumber
         |
         |  lazy val akkaActor       = "com.typesafe.akka"         %%  "akka-actor"                          % akkaVersion
         |  lazy val akkaRemote      = "com.typesafe.akka"         %%  "akka-remote"                         % akkaVersion
         |  lazy val akkaStream      = "com.typesafe.akka"         %%  "akka-stream"                         % akkaVersion
         |  lazy val akkaPersistence = "com.typesafe.akka"         %%  "akka-persistence"                    % akkaVersion
         |  lazy val akkaCluster     = "com.typesafe.akka"         %%  "akka-cluster"                        % akkaVersion
         |  lazy val akkaClusterMetrics = "com.typesafe.akka"      %%  "akka-cluster-metrics"                % akkaVersion
         |  lazy val akkaClusterTools = "com.typesafe.akka"        %%  "akka-cluster-tools"                  % akkaVersion
         |  lazy val akkaClusterSharding = "com.typesafe.akka"     %%  "akka-cluster-sharding"               % akkaVersion
         |  lazy val akkaSlf4j       = "com.typesafe.akka"         %%  "akka-slf4j"                          % akkaVersion
         |  lazy val akkaTestkit     = "com.typesafe.akka"         %%  "akka-testkit"                        % akkaVersion
         |  lazy val play            = "com.typesafe.play"         %%  "play"                                % playVersion
         |  lazy val playTest        = "com.typesafe.play"         %%  "play-test"                           % playVersion
         |  lazy val akkaMultiNodeTestKit = "com.typesafe.akka"    %%  "akka-multi-node-testkit"             % akkaVersion
         |  lazy val akkaStreamCassandra = "com.lightbend.akka"    %%  "akka-stream-alpakka-cassandra"       % "1.0.2"
         |  lazy val leveldbjniAll   = "org.fusesource.leveldbjni" % "leveldbjni-all"                        % "1.8"
         |  lazy val akkaPersistenceCassandra = "com.typesafe.akka" %%  "akka-persistence-cassandra"         % "0.98"
         |  lazy val jodaTime        = "joda-time"                 %   "joda-time"                           % "2.9.9"
         |  lazy val playAnorm       = "org.playframework.anorm"   %%  "anorm"                               % "2.6.2"
         |  lazy val playGuice       = "com.typesafe.play"         %%  "play-guice"                          % playVersion
         |  lazy val playJson        = "com.typesafe.play"         %%  "play-json"                           % playVersion
         |  lazy val playSlick       = "com.typesafe.play"         %%  "play-slick"                          % "4.0.1"
         |  lazy val scalapbRuntime  = "com.thesamet.scalapb"      %% "scalapb-runtime"                      % scalapbVersion
         |  lazy val scalapbJson4s   = "com.thesamet.scalapb"      %% "scalapb-json4s"                       % "0.9.0-M1"
         |  lazy val mysqlDriver     = "mysql"                     %   "mysql-connector-java"                % "6.0.6"
         |
         |  lazy val queryRuntime    = "com.github.apuex.springbootsolution" %%  "runtime"                   % "1.0.7"
         |  lazy val playEvents      = "com.github.apuex"          %%  "play-events"                         % "1.0.2"
         |  lazy val logback         = "ch.qos.logback"            %   "logback-classic"                     % "1.2.3"
         |  lazy val slf4jApi        = "org.slf4j"                 %  "slf4j-api"                            % "1.7.25"
         |  lazy val slf4jSimple     = "org.slf4j"                 %  "slf4j-simple"                         % "1.7.25"
         |  lazy val guava           = "com.google.guava"          %   "guava"                               % "22.0"
         |  lazy val macwireMicros   = "com.softwaremill.macwire"  %%  "macros"                              % "2.3.0"
         |  lazy val jms               = "javax.jms"               % "jms"                                   % "1.1"
         |  lazy val imq               = "org.glassfish.mq"        % "imq"                                    % "5.1"
         |  lazy val alpakkaJms       = "com.lightbend.akka"      %% "akka-stream-alpakka-jms"              % "1.0-M3"
         |  lazy val akkaHttp       = "com.typesafe.akka"          %% "akka-http"                            % "10.1.8"
         |  lazy val akkaParsing     = "com.typesafe.akka"         %%  "akka-parsing"                        % "10.1.8"
         |  lazy val scalaTest       = "org.scalatest"             %% "scalatest"                            % "3.0.4"
         |  lazy val scalacheck      = "org.scalacheck"            %%  "scalacheck"                          % "1.13.4"
         |  lazy val scalaTestPlusPlay = "org.scalatestplus.play"  %%  "scalatestplus-play"                  % "3.1.2"
         |
         |  lazy val confPath = "../conf"
         |
         |  lazy val localRepo = Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
         |}
      """.stripMargin.trim)
    dependencies.close()
  }

  def rest(): Unit = {
    val dir = "rest"
    new File(dir).mkdirs()
    val buildSbt = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    buildSbt.println(
      s"""
         |import Dependencies._
         |import sbtassembly.MergeStrategy
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |libraryDependencies ++= {
         |  Seq(
         |    playEvents,
         |    scalapbJson4s,
         |    playJson,
         |    playAnorm,
         |    playSlick,
         |    playTest % Test,
         |    scalaTestPlusPlay % Test
         |  )
         |}
         |
         |routesGenerator := InjectedRoutesGenerator
         |
         |publishTo := localRepo
         |
         |assemblyJarName in assembly := s"$${name.value}-assembly-$${version.value}.jar"
         |mainClass in assembly := Some("play.core.server.ProdServerStart")
         |fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)
         |
         |assemblyExcludedJars in assembly := {
         |  val cp = (fullClasspath in assembly).value
         |  cp.filter( x =>
         |    x.data.getName.contains("javax.activation-api")
         |      || x.data.getName.contains("lagom-logback")
         |  )
         |}
         |
         |assemblyMergeStrategy in assembly := {
         |  case manifest if manifest.contains("MANIFEST.MF") =>
         |    // We don't need manifest files since sbt-assembly will create
         |    // one with the given settings
         |    MergeStrategy.discard
         |  case PathList("META-INF", "io.netty.versions.properties") =>
         |    MergeStrategy.discard
         |  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
         |    // Keep the content for all reference-overrides.conf files
         |    MergeStrategy.concat
         |  case x =>
         |    // For all the other files, use the default sbt-assembly merge strategy
         |    val oldStrategy = (assemblyMergeStrategy in assembly).value
         |    oldStrategy(x)
         |}
      """.stripMargin.trim)
    buildSbt.close()

    val refConf = new PrintWriter(s"${dir}/conf/reference.conf", "utf-8")
    refConf.println(
      s"""
         |# https://www.playframework.com/documentation/latest/Configuration
         |play {
         |  modules.enabled += ${modelPackage}.events.Module
         |}
       """.stripMargin.trim)
    refConf.close()
  }

  def root(): Unit = {
    val dir = "."
    new File(dir).mkdirs()
    val pw = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    pw.println(
      s"""
         |import Dependencies._
         |
         |name         := "${modelName}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |lazy val root = (project in file("."))
         |  .aggregate(
         |    model,
         |    dbschema,
         |    message,
         |    `dao-mysql`,
         |    domain,
         |    cluster,
         |    service,
         |    rest,
         |    webapp
         |  )
         |
         |lazy val model = (project in file("model"))
         |lazy val dbschema = (project in file("dbschema"))
         |lazy val message = (project in file("message"))
         |lazy val `dao-mysql` = (project in file("dao-mysql")).dependsOn(message)
         |lazy val domain = (project in file("domain")).dependsOn(`dao-mysql`)
         |lazy val cluster = (project in file("cluster")).dependsOn(domain)
         |lazy val service = (project in file("service")).dependsOn(cluster)
         |lazy val rest = (project in file("rest")).enablePlugins(PlayScala, RoutesCompiler).dependsOn(cluster, service)
         |lazy val webapp = (project in file("webapp")).enablePlugins(PlayScala, RoutesCompiler).dependsOn(rest)
         |
         |publishTo := localRepo
         |
      """.stripMargin.trim)
    pw.close()
  }

  def service(): Unit = {
    val dir = "service"
    new File(dir).mkdirs()
    val pw = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    pw.println(
      s"""
         |import Dependencies._
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |libraryDependencies ++= {
         |  Seq(
         |    akkaActor,
         |    akkaSlf4j,
         |    slf4jApi % Test,
         |    slf4jSimple % Test,
         |    playTest % Test,
         |    scalaTest % Test,
         |    akkaTestkit % Test,
         |    akkaMultiNodeTestKit % Test,
         |    scalaTestPlusPlay % Test
         |  )
         |}
         |
         |publishTo := localRepo
      """.stripMargin.trim)
    pw.close()
  }

  def webapp(): Unit = {
    val dir = "webapp"
    new File(dir).mkdirs()
    val buildSbt = new PrintWriter(s"${dir}/build.sbt", "utf-8")
    buildSbt.println(
      s"""
         |import Dependencies._
         |
         |name := "${dir}"
         |scalaVersion := scalaVersionNumber
         |organization := artifactGroupName
         |version      := artifactVersionNumber
         |
         |import Dependencies._
         |import sbtassembly.MergeStrategy
         |
         |libraryDependencies ++= Seq(
         |  jdbc,
         |  ehcache,
         |  ws,
         |  filters,
         |  playAnorm,
         |  playSlick,
         |  mysqlDriver,
         |  scalaTestPlusPlay % Test
         |)
         |
         |publishTo := localRepo
         |
         |routesGenerator := InjectedRoutesGenerator
         |
         |assemblyJarName in assembly := s"$${name.value}-assembly-$${version.value}.jar"
         |mainClass in assembly := Some("play.core.server.ProdServerStart")
         |fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)
         |
         |assemblyExcludedJars in assembly := {
         |  val cp = (fullClasspath in assembly).value
         |  cp.filter( x =>
         |    x.data.getName.contains("javax.activation-api")
         |      || x.data.getName.contains("lagom-logback")
         |  )
         |}
         |
         |assemblyMergeStrategy in assembly := {
         |  case PathList("router", clazz) if clazz.matches("Routes.*.class$$") => MergeStrategy.first
         |  case PathList("routes") => MergeStrategy.first
         |  case manifest if manifest.contains("MANIFEST.MF") =>
         |    // We don't need manifest files since sbt-assembly will create
         |    // one with the given settings
         |    MergeStrategy.discard
         |  case PathList("META-INF", "io.netty.versions.properties") =>
         |    MergeStrategy.discard
         |  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
         |    // Keep the content for all reference-overrides.conf files
         |    MergeStrategy.concat
         |  case x =>
         |    // For all the other files, use the default sbt-assembly merge strategy
         |    val oldStrategy = (assemblyMergeStrategy in assembly).value
         |    oldStrategy(x)
         |}
      """.stripMargin.trim)
    buildSbt.close()

    val appConf = new PrintWriter(s"${dir}/conf/application.conf", "utf-8")
    appConf.println(
      s"""
         |# https://www.playframework.com/documentation/latest/Configuration
         |${modelName} {
         |  entity {
         |    passivate-timeout = 6 seconds
         |
         |    number-of-shards = 100
         |  }
         |
         |  alarm {
         |    eval-period = 5 seconds
         |    delay-raise = 30 seconds
         |    delay-end = 30 seconds
         |  }
         |
         |  cluster {
         |    min-nr-of-nodes = 1
         |    activity-check-period = 1 minutes
         |  }
         |}
         |
         |play {
         |  modules.enabled += ${modelPackage}.events.Module
         |  modules.enabled += ${modelPackage}.shard.Module
         |  modules.enabled += ${modelPackage}.tx.Module
         |  modules.enabled += ${modelPackage}.service.Module
         |  http {
         |    secret {
         |      key="cfd16c3a-f0f2-4fa9-8e58-ff9a2ad2a422"
         |      key=$${?APPLICATION_SECRET}
         |    }
         |  }
         |  filters {
         |    hosts {
         |      allowed=[$${HOSTNAME}]
         |    }
         |    headers {
         |      frameOptions=null
         |      xssProtection=null
         |      contentTypeOptions=null
         |      permittedCrossDomainPolicies=null
         |      contentSecurityPolicy=null
         |    }
         |  }
         |  server {
         |    http {
         |      port = 9000
         |    }
         |  }
         |  akka {
         |    actor-system = "${modelName}-system"
         |  }
         |}
         |
         |db {
         |  ${modelName}_system{
         |    driver = com.mysql.cj.jdbc.Driver
         |    url = "jdbc:mysql://$${mysql.host}:3306/${modelName}_system?characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&verifyServerCertificate=false"
         |    username = root
         |    password = 123qwer
         |  }
         |}
         |
         |akka {
         |  log-dead-letters = off
         |  log-dead-letters-during-shutdown = off
         |
         |  actor {
         |    provider = "akka.cluster.ClusterActorRefProvider"
         |    serializers {
         |      protobuf = "akka.remote.serialization.ProtobufSerializer"
         |    }
         |    serialization-bindings {
         |      "java.io.Serializable" = none
         |      // scalapb 0.8.4
         |      "scalapb.GeneratedMessage" = protobuf
         |      // google protobuf-java 3.6.1
         |      // "com.google.protobuf.GeneratedMessageV3" = protobuf
         |    }
         |  }
         |
         |  remote {
         |    netty.tcp {
         |      hostname = "localhost" // default to the first seed node
         |      port = 2553               // default port
         |      hostname = $${?HOSTNAME}   // override with -DHOSTNAME
         |      port = $${?PORT}           // override with -DPORT
         |    }
         |  }
         |
         |  cluster {
         |    seed-nodes = [
         |      "akka.tcp://${modelName}-system@$${akka.seed.host}:2553",
         |    ]
         |  }
         |
         |  persistence {
         |    journal {
         |      plugin = "cassandra-journal"
         |    }
         |    snapshot-store {
         |      plugin = "cassandra-snapshot-store"
         |    }
         |  }
         |}
         |
         |cassandra-journal {
         |  class = "akka.persistence.cassandra.journal.CassandraJournal"
         |  contact-points = ["$${cassandra.host}"]
         |  port = 9042
         |  cluster-id = "${modelName}-cluster"
         |  keyspace = "${modelName}_journal"
         |}
         |
         |cassandra-snapshot-store {
         |  class = "akka.persistence.cassandra.snapshot.CassandraSnapshotStore"
         |  contact-points = ["$${cassandra.host}"]
         |  port = 9042
         |  cluster-id = "${modelName}-cluster"
         |  keyspace = "${modelName}_snapshot"
         |}
       """.stripMargin.trim)
    appConf.close()

    val logConf = new PrintWriter(s"${dir}/conf/logback.xml", "utf-8")
    logConf.println(
      s"""
         |<!-- https://www.playframework.com/documentation/latest/SettingsLogger -->
         |<configuration>
         |
         |  <conversionRule conversionWord="coloredLevel" converterClass="play.api.libs.logback.ColoredLevel" />
         |
         |  <appender name="FILE" class="ch.qos.logback.core.FileAppender">
         |    <file>$${application.home:-.}/logs/application.log</file>
         |    <encoder>
         |      <pattern>%date [%level] from %logger in %thread - %message%n%xException</pattern>
         |    </encoder>
         |  </appender>
         |
         |  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
         |    <encoder>
         |      <pattern>%coloredLevel %logger{15} - %message%n%xException{10}</pattern>
         |    </encoder>
         |  </appender>
         |
         |  <appender name="ASYNCFILE" class="ch.qos.logback.classic.AsyncAppender">
         |    <appender-ref ref="FILE" />
         |  </appender>
         |
         |  <appender name="ASYNCSTDOUT" class="ch.qos.logback.classic.AsyncAppender">
         |    <appender-ref ref="STDOUT" />
         |  </appender>
         |
         |  <logger name="play" level="INFO" />
         |  <logger name="application" level="INFO" />
         |  <logger name="${modelPackage}" level="DEBUG" />
         |
         |  <root level="WARN">
         |    <appender-ref ref="ASYNCSTDOUT" />
         |  </root>
         |
         |</configuration>
       """.stripMargin.trim)
    logConf.close()
  }
}
