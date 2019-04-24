package com.github.apuex.akka.gen.contex.mapping

import java.io.{File, PrintWriter}

import com.github.apuex.akka.gen.contex.mapping.MappingLoader._
import com.github.apuex.springbootsolution.runtime.SymbolConverters._

import scala.xml.Node

object ContextMapping extends App {
  val model = MappingLoader(args(0), "mapping")

  import model._

  new File(srcDir).mkdirs()

  serviceMappings

  private def serviceMappings = {
    model.xml.child.filter(x => x.label == "service")
      .foreach(x => serviceMapping(x))
  }


  private def serviceMapping(service: Node): Unit = {
    val mappingName = cToPascal(s"${service.\@("from")}_${service.\@("to")}_${projectName}")
    val printWriter = new PrintWriter(s"${docsDir}/${mappingName}.scala", "utf-8")
    // package definition
    printWriter.println(s"package ${srcPackage}")
    // imports
    printWriter.println(s"package ${importPackagesForService(model.xml, service)}")
    printWriter.close()
  }

}
