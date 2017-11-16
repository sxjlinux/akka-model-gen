package com.github.apuex.akka.gen.util

object Model {
  case class Field(name: String,
                   dataType: Option[String],
                   valueType: Option[String],
                   entity: Option[String],
                   keyField: Option[String],
                   valueField: Option[String],
                   joinKey: Option[String],
                   refField: Option[String],
                   length: Option[Int],
                   transient: Option[Boolean],
                   aggregation: Option[Boolean],
                   required: Option[Boolean])
  case class Key(name: String, fields: Seq[Field])
}
