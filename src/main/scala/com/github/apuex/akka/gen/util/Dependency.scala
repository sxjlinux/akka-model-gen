package com.github.apuex.akka.gen.util

import scala.collection.mutable
import scala.xml.{Node, Text}

object Dependency {
  def apply(xml: Node): Dependency = new Dependency(xml)
}

class Dependency(xml: Node) {
  val basePackage = xml.attribute("package")

  def imports: Seq[String] = {
    protobuf.map {
      case "Any" => "import com.google.protobuf.any.Any"
      case "Timestamp" => "import com.google.protobuf.timestamp.Timestamp"
      case "ByteString" => "import com.google.protobuf.ByteString"
    } ++ entity.map(x => s"import ${basePackage}.message.${x}") ++
      dao.map(x => s"import ${basePackage}.dao.${x}")
  }

  def protobuf: Seq[String] = ???
  def entity: Seq[String] = ???
  def dao: Seq[String] = {
    var seq: Seq[String] = mutable.ArraySeq()
    xml.child.filter(x => x.label == "entity" && !(x.attribute("transient") == Some(Text("true"))))
      .foreach(x => {
        seq += x.label
      })
    seq
  }
}
