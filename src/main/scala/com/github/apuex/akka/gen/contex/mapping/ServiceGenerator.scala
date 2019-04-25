package com.github.apuex.akka.gen.contex.mapping

import java.io.PrintWriter

import com.github.apuex.akka.gen.contex.mapping.MappingLoader.importPackages
import com.github.apuex.springbootsolution.runtime.SymbolConverters.{cToCamel, cToPascal}
import com.github.apuex.springbootsolution.runtime.TextUtils.indent

import scala.collection.mutable
import scala.xml._
import _root_.scala.xml.parsing._

class ServiceGenerator(mappingLoader: MappingLoader) {

  import mappingLoader._

  case class OperationDescription(name: String, req: String, resp: String)
  val serviceCalls: mutable.Map[String, mutable.Set[OperationDescription]] = mutable.Map()


  def generate(): Unit = {
    collectServiceCalls(xml, serviceCalls)
    serviceCalls.foreach(x => generateService(x))
  }

  private def collectServiceCalls(node: Node, calls: mutable.Map[String, mutable.Set[OperationDescription]]): Unit = {
    node.child.filter(x => x.label == "service-call")
      .foreach(x => collectServiceCall(x, calls))
    node.child.foreach(x => collectServiceCalls(x, calls))
  }

  private def collectServiceCall(call: Node, calls: mutable.Map[String, mutable.Set[OperationDescription]]): Unit = {
    val serviceName = call.\@("to")
    val operationName = call.\@("operation")
    val requestType = call.\@("request-type")
    val responseType = call.\@("response-type")
    val operations = calls.getOrElse(serviceName, mutable.Set())
    operations += OperationDescription(
      operationName,
      requestType,
      responseType
    )
    calls += (serviceName -> operations)
  }

  def generateService(service: (String, mutable.Set[OperationDescription])): Unit = {
    val serviceName = cToPascal(s"${service._1}_service")
    val jsonSupportName = cToPascal(s"${service._1}_json_support")
    val printWriter = new PrintWriter(s"${srcDir}/${serviceName}.scala", "utf-8")
    // package definition
    printWriter.println(s"package ${srcPackage}\n")
    // imports
    printWriter.println(s"${importPackages(xml)}")
    // companion object declaration
    printWriter.println(
      s"""
         |import play.api.libs.json.Json
         |import play.api.libs.ws._
         |
         |import scala.concurrent.Future
         |import scala.concurrent.duration._
         |
         |object ${serviceName} {
         |  def name = "${serviceName}"
         |}
       """.stripMargin)
    // begin class declaration
    printWriter.println(
      s"""class ${serviceName} @Inject()(baseUrl: String, ws: WSClient)
         |  extends ${jsonSupportName} {
         """.stripMargin)

    service._2.foreach(m => {
      printWriter.println(s"${indent(generateServiceOperation(service._1, m), 2, true)}\n")
    })
    // end class declaration
    printWriter.println("}")
    printWriter.close()
  }

  def generateServiceOperation(service: String, operation: OperationDescription): String = {
    val req = if(operation.req.isEmpty) s"${cToPascal(s"${operation.name}_${service}_cmd")}" else cToPascal(operation.req)
    val resp = if(operation.resp.isEmpty) "Unit" else cToPascal(operation.resp)
    s"""
       |def ${cToCamel(operation.name)}(cmd: ${req}): ${resp} = ???
     """.stripMargin
      .trim
  }
}
