package com.github.apuex.akka.gen.util

import scala.xml.Node

object Dependency {
  def apply(xml: Node): Dependency = new Dependency(xml)
}

class Dependency(xml: Node) {
  def imports: Seq[String] = {
    protobuf.map(x => {
      x match {
        case "Any" => "import com.google.protobuf.any.Any"
        case "Timestamp" => "import com.google.protobuf.timestamp.Timestamp"
        case "ByteString" => "import com.google.protobuf.ByteString"
      }
    })
  }
  def protobuf: Seq[String] = ???
  def entity: Seq[String] = ???
  def dao: Seq[String] = ???
}
