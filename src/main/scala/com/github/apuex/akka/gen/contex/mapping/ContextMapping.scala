package com.github.apuex.akka.gen.contex.mapping

import java.io.{File, PrintWriter}

import com.github.apuex.akka.gen.contex.mapping.MappingLoader._
import com.github.apuex.springbootsolution.runtime.SymbolConverters._

import scala.xml.Node

object ContextMapping extends App {
  val model = MappingLoader(args(0), "mapping")

  import model._

  new File(srcDir).mkdirs()

  project

  serviceMappings

  private def serviceMappings: Unit = {
    model.xml.child.filter(x => x.label == "service")
      .foreach(x => serviceMapping(x))
  }


  private def serviceMapping(service: Node): Unit = {
    val from = service.\@("from")
    val to = service.\@("to")
    val mappingName = cToPascal(s"${from}_${to}_${projectName}")
    val printWriter = new PrintWriter(s"${srcDir}/${mappingName}.scala", "utf-8")
    // package definition
    printWriter.println(s"package ${srcPackage}")
    // imports
    printWriter.println(s"${importPackagesForService(model.xml, service)}")
    // companion object declaration
    printWriter.println(
      s"""
         |object ${mappingName}{
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
         |  override def persistenceId: String = ${mappingName}.name
         |
         |  // state
         |  var lastEvent: Option[EventEnvelope] = None
         |
         """.stripMargin)

    // end class declaration
    printWriter.println("}")
    printWriter.close()
  }

  private def project : Unit = {

  }
}
