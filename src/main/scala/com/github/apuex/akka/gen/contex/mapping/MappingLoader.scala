package com.github.apuex.akka.gen.contex.mapping

import com.github.apuex.springbootsolution.runtime.SymbolConverters.cToShell

import _root_.scala.xml.parsing._
import scala.xml._

object MappingLoader {
  def apply(fileName: String): MappingLoader = {
    val factory = new NoBindingFactoryAdapter
    new MappingLoader(factory.load(fileName))
  }

  def apply(xml: Node): MappingLoader = new MappingLoader(xml)
}

class MappingLoader(val xml: Node) {
  val modelName = xml.\@("name")
  val modelPackage = xml.\@("package")
  val projectRoot = s"${System.getProperty("project.root", "target/generated")}"
  def projectDir(name: String) = s"${projectRoot}/${cToShell(modelName)}/${cToShell(modelName)}-${name}"
  def srcDir(name: String) = s"${projectDir(name)}/src/main/java/${modelPackage.replace('.', '/')}/${name}"
  val symboConverter = if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}")
    "new IdentityConverter()" else "new CamelToCConverter()"
  val docsDir = s"${projectRoot}/${cToShell(modelName)}/docs"
  val hyphen = if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}") "" else "-"
}
