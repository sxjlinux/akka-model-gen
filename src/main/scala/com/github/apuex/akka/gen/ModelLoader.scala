package com.github.apuex.akka.gen

import _root_.scala.xml.parsing._

case class ModelLoader(fileName: String) {
  val factory = new NoBindingFactoryAdapter
  val xml = factory.load(fileName)
}
