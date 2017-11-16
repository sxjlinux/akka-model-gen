package com.github.apuex.akka.gen

import _root_.scala.xml.parsing._
import scala.xml.Node

case class ModelLoader(fileName: String) {
  val factory = new NoBindingFactoryAdapter
  val xml: Node = factory.load(fileName)
}
