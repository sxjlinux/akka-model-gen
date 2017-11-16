package com.github.apuex.akka.gen.dao.mysql

import com.github.apuex.akka.gen.util._
import com.github.apuex.akka.gen.util.TextNode._

import scala.xml.Node

class MySqlDaoGenerator(xml: Node) {
  def generate(): Unit = {
    var dependency = Dependency(xml)
    xml.filter(x => x.label == "entity")
      .foreach(x => {
        generateDao(x, dependency)
      })
  }

  def generateDao(entity: Node, dependency: Dependency): Unit = {
    var content: String =
      s"""
${dependency.imports}|
@Singleton
class ${text(entity.attribute("name"))} @Inject() (${dependency.daoDefs}) {

}
      """.stripMargin
  }
}
