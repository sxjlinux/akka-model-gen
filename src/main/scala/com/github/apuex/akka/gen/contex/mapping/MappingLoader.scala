package com.github.apuex.akka.gen.contex.mapping

import com.github.apuex.springbootsolution.runtime.SymbolConverters._

import _root_.scala.xml.parsing._
import scala.xml._

object MappingLoader {
  def apply(fileName: String, projectName: String): MappingLoader = {
    val factory = new NoBindingFactoryAdapter
    new MappingLoader(factory.load(fileName), projectName)
  }

  def apply(xml: Node, projectName: String): MappingLoader = new MappingLoader(xml, projectName)

  def importPackagesForService(model: Node, service: Node): String = {
    s"""
       |${importPackages(service)}
       |${importPackages(model)}
     """.stripMargin
  }

  def importPackages(node: Node): String = {
    node.child.filter(x => x.label == "imports")
      .flatMap(x => x.child.filter(c => c.label == "import"))
      .map(x => x.text.trim)
      .map(x => x.replace("*", "_"))
      .map(x => x.replace("static", ""))
      .reduceLeft((l, r) => s"${l}\n${r}")
  }
}

class MappingLoader(val xml: Node, val projectName: String) {
  val modelName = xml.\@("name")
  val modelPackage = xml.\@("package")
  val projectRoot = s"${System.getProperty("project.root", "target/generated")}"
  val projectDir = s"${projectRoot}/${cToShell(modelName)}/${cToShell(modelName)}-${cToShell(projectName)}"
  val srcPackage = s"${modelPackage}.${cToCamel(modelName)}"
  val srcDir = s"${projectDir}/src/main/scala/${srcPackage.replace('.', '/')}"
  val symboConverter = if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}")
    "new IdentityConverter()" else "new CamelToCConverter()"
  val docsDir = s"${projectRoot}/${cToShell(modelName)}/docs"
  val classNamePostfix = s"${cToPascal(projectName)}"
  val hyphen = if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}") "" else "-"
}
