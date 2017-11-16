package com.github.apuex.akka.gen.util

import com.github.apuex.akka.gen.util.TextNode._
import scala.collection.mutable
import scala.xml.{Node, Text}

object Dependency {
  def apply(root: Node): Dependency = new Dependency(root)
  def apply(xml: Node, basePackage: String): Dependency = new Dependency(xml, basePackage)
}

class Dependency(xml: Node, basePackage: String) {
  def this(root: Node) = this(root, text(root.attribute("package")))
  def imports: Seq[String] = {
    protobuf.map {
      case "Any" => "import com.google.protobuf.any.Any"
      case "Timestamp" => "import com.google.protobuf.timestamp.Timestamp"
      case "ByteString" => "import com.google.protobuf.ByteString"
      case x => throw new IllegalArgumentException(x)
    } ++ entity.map(x => s"import ${basePackage}.message.${x}") ++
      dao.map(x => s"import ${basePackage}.dao.${x}")
  }

  def protobuf: Seq[String] = Seq()
  def entity: Seq[String] = Seq()
  def dao: Seq[String] = {
    var seq: Seq[String] = mutable.ArraySeq()
    val typeTest = Type(xml)
    def add(str: String) = {
      if(typeTest.isValidType(str) && !typeTest.isBasicType(str)) {
        seq +:= str
      }
      Some(seq.isEmpty)
    }
    xml.child.filter(x => x.label == "entity" && !(x.attribute("transient") == Some(Text("true"))))
      .foreach(x => {
        add(text(x.attribute("name")))
        x.child.filter(x => x.label == "field")
          .foreach(x => {
            optionalText(x.attribute("type")).flatMap(x => add(x))
            optionalText(x.attribute("valueType")).flatMap(x => add(x))
            optionalText(x.attribute("entity")).flatMap(x => add(x))
          })
      })
    seq
  }

  def daoVars: String = {
    if (dao.nonEmpty)
      dao.map(x => s"${Identifier.toCamel(x)}DAO").reduce((x, y) => s"${x}, ${y}")
    else
      ""
  }

  def daoDefs: String = {
    if (dao.nonEmpty)
      dao.map(x => s"${Identifier.toCamel(x)}DAO: ${Identifier.toPascal(x)}DAO").reduce((x, y) => s"${x}, ${y}")
    else
      ""
  }
}
