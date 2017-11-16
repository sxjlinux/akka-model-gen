package com.github.apuex.akka.gen.util

object Identifier {
  def toPascal(id: String): String = id.split("_")
    .map(x => x.toLowerCase)
    .map(x => x.capitalize).reduce(_ + _)

  def toCamel(id: String): String = {
    id.trim match {
      case "" => ""
      case x =>
        val p = toPascal(x)
        val c = p.substring(0, 1)
        c.toLowerCase + p.substring(1)
    }
  }
}
