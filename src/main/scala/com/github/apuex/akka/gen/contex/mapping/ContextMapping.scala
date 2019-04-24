package com.github.apuex.akka.gen.contex.mapping

import java.io.{File, PrintWriter}

import com.github.apuex.akka.gen.contex.mapping.MappingLoader._
import com.github.apuex.springbootsolution.runtime.SymbolConverters._
import com.github.apuex.springbootsolution.runtime.TextUtils._

import scala.xml.Node

object ContextMapping extends App {
  val model = MappingLoader(args(0), "mapping")
  new ProjectGenerator(model).generate()

  import model._

  new File(srcDir).mkdirs()



  serviceMappings

  private def serviceMappings: Unit = {
    model.xml.child.filter(x => x.label == "service")
      .foreach(x => serviceMapping(x))
  }


  private def serviceMapping(service: Node): Unit = {
    val from = service.\@("from")
    val to = service.\@("to")
    val mappingName = cToPascal(s"${from}_${to}_${project}")
    val printWriter = new PrintWriter(s"${srcDir}/${mappingName}.scala", "utf-8")
    // package definition
    printWriter.println(s"package ${srcPackage}\n")
    // imports
    printWriter.println(s"${importPackagesForService(model.xml, service)}")
    // companion object declaration
    printWriter.println(
      s"""
         |object ${mappingName} {
         |  def name = "${mappingName}"
         |}
       """.stripMargin)
    // begin class declaration
    printWriter.println(
      s"""class ${mappingName} @Inject()(@Named("${cToCamel(from)}") ${cToCamel(from)}: ActorRef, @Named("${cToCamel(to)}") ${cToCamel(to)}: ActorRef)
         |  extends PersistentActor
         |    with ActorLogging {
         |  implicit val executionContext = context.system.dispatcher
         |  implicit val materializer = ActorMaterializer()
         |
         |  override def persistenceId: String = ${mappingName}.name
         |
         |  // state
         |  var lastEvent: Option[EventEnvelope] = None
         """.stripMargin)

    printWriter.println(s"${indent(receiveRecover(service), 2, true)}\n")
    printWriter.println(s"${indent(receiveCommand(service), 2, true)}\n")
    printWriter.println(s"${indent(updateState(service), 2, true)}\n")

    // end class declaration
    printWriter.println("}")
    printWriter.close()
  }

  private def receiveRecover(service: Node): String = {
    s"""
       |override def receiveRecover: Receive = {
       |  case SnapshotOffer(metadata: SnapshotMetadata, snapshot: EventEnvelope) =>
       |    lastEvent = Some(snapshot)
       |  case _: RecoveryCompleted =>
       |    // TODO: connect to source service and start event subscription.
       |  case x =>
       |    updateState(x)
       |}
     """.stripMargin
      .trim
  }

  private def receiveCommand(service: Node): String = {
    s"""
       |override def receiveCommand: Receive = {
       |  case x: EventEnvelope =>
       |    // TODO: process event
       |    persist(x)(updateState)
       |  case x =>
       |    log.info("unhandled command: {}", x)
       |}
     """.stripMargin
      .trim
  }

  private def updateState(service: Node): String = {
    s"""
       |private def updateState: (Any => Unit) = {
       |  case x: EventEnvelope =>
       |    lastEvent = Some(x)
       |    log.info("unhandled update state: {}", x)
       |}
     """.stripMargin
      .trim
  }
}
