package com.github.apuex.akka.gen.contex.mapping

import java.io.{File, PrintWriter}

import com.github.apuex.springbootsolution.runtime.SymbolConverters._

import scala.xml.Node

object ContextMapping extends App {
  val model = MappingLoader(args(0))
  import model._

  new File(docsDir).mkdirs()

  val printWriter = new PrintWriter(s"${docsDir}/api-list.csv", "utf-8")

  model.xml.child.filter(x => x.label == "entity")
    .filter(x => x.\@("aggregationRoot") == "true")
    .foreach(x => apiForEntity(modelPackage, x))

  printWriter.close()

  private def apiForEntity(modelPackage: String, entity: Node): Unit = {
    val entityName = entity.\@("name")
    val format = "%s, %s\n"
    printWriter.print(format.format(s"${cToShell(entityName)}", s"${cToShell("%s%s%s".format("create", hyphen, cToShell(entityName)))}"))
    printWriter.print(format.format("", s"${cToShell("%s%s%s".format("retrieve", hyphen, cToShell(entityName)))}"))
    printWriter.print(format.format("", s"${cToShell("%s%s%s".format("update", hyphen, cToShell(entityName)))}"))
    printWriter.print(format.format("", s"${cToShell("%s%s%s".format("delete", hyphen, cToShell(entityName)))}"))
    printWriter.print(format.format("", s"${cToShell("%s%s%s".format("query", hyphen, cToShell(entityName)))}"))
  }

}
