package com.github.apuex.akka.gen.contex.mapping

import com.github.apuex.springbootsolution.runtime.SymbolConverters._

import _root_.scala.xml.parsing._
import scala.xml._

object MappingLoader {
  def apply(fileName: String, project: String): MappingLoader = {
    val factory = new NoBindingFactoryAdapter
    new MappingLoader(factory.load(fileName), project)
  }

  def apply(xml: Node, projectName: String): MappingLoader = new MappingLoader(xml, projectName)

  def importPackagesForService(model: Node, service: Node): String = {
    s"""
       |${importPackages(service)}
       |${importPackages(model)}
     """.stripMargin
      .trim
  }

  def importPackages(node: Node): String = {
    node.child.filter(x => x.label == "imports")
      .flatMap(x => x.child.filter(c => c.label == "import"))
      .map(x => x.text.trim)
      .map(x => x.replace("*", "_"))
      .map(x => x.replace("static", ""))
      .map(x => s"import ${x}")
      .reduceLeft((l, r) => s"${l}\n${r}")
  }
}

class MappingLoader(val xml: Node, val project: String) {
  val modelName = xml.\@("name")
  val modelPackage = xml.\@("package")
  val modelVersion = xml.\@("version")
  val outputDir = s"${System.getProperty("output.dir", "target/generated")}"
  val rootProjectName = s"${cToShell(modelName)}"
  val rootProjectDir = s"${outputDir}/${rootProjectName}"
  val projectName = s"${cToShell(modelName)}-${cToShell(project)}"
  val projectDir = s"${rootProjectDir}/${projectName}"
  val srcPackage = s"${modelPackage}.${cToCamel(modelName)}"
  val srcDir = s"${projectDir}/src/main/scala/${srcPackage.replace('.', '/')}"
  val symboConverter = if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}")
    "new IdentityConverter()" else "new CamelToCConverter()"
  val docsDir = s"${rootProjectDir}/docs"
  val classNamePostfix = s"${cToPascal(project)}"
  val hyphen = if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}") "" else "-"
}
