package com.github.apuex.akka.gen.util

import scala.xml.{Node, Text}

object TextNode {
  def text(attribute: Option[Seq[Node]]): String = attribute match {
    case Some(Text(data)) => data
    case _ => "<Unknown>"
  }

  def optionalText(attribute: Option[Seq[Node]]): Option[String] = attribute match {
    case Some(Text(data)) => Some(data)
    case _ => None
  }
}
