package com.github.apuex.akka.gen.util

import scala.xml.{Node, Text}

object Type {
  def apply(xml: Node): Type = new Type(xml)


}

class Type(xml: Node) {
  def isValidType(typeName: String): Boolean = {
    if (isBasicType(typeName)) true
    else {
      val entities = xml.child.filter(x => x.label == "entity"
        && x.attribute("name") == Some(Text(typeName)))
      val aggregates = entities.map(x => {
        x.child.filter(x => (x.label == "aggregation" || x.label == "message")
          && x.attribute("name") == Some(Text(typeName))).nonEmpty
      })
      entities.nonEmpty || aggregates.reduce(_ || _)
    }
  }

  def toScalaType(typeName: String): String = {
    if (isBasicType(typeName))
      return toBasicType(typeName)
    else if (isEnumType(typeName))
      return toEnumType(typeName)
    else
      return toValueType(typeName)
  }

  def isEnumType(typeName: String): Boolean = {
    xml.child
      .filter(x =>
        x.label == "entity"
          & (x.attribute("name") == Some(Text(typeName)))
          & (x.attribute("enum") == Some(Text("true")))
      ).nonEmpty
  }

  def isBasicType(typeName: String): Boolean = typeName match {
    case "bool" => true
    case "int" => true
    case "long" => true
    case "string" => true
    case "text" => true
    case "timestamp" => true
    case _ => false
  }

  def toBasicType(typeName: String): String = typeName match {
    case "bool" => "Boolean"
    case "int" => "Int"
    case "long" => "Long"
    case "string" => "String"
    case "text" => "String"
    case "timestamp" => "Timestamp"
    case _ => "<Unknown>"
  }

  def toEnumType(typeName: String): String = s"${Identifier.toPascal(typeName)}"

  def toValueType(typeName: String): String = s"${Identifier.toPascal(typeName)}Vo"
}
