package com.github.apuex.akka.gen.util

import com.github.apuex.akka.gen.ModelLoader
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.Text

class TextNodeSpec extends FlatSpec with Matchers {
  "A TextNode" should "extract text attribute from xml node" in {
    val xml = ModelLoader("src/test/resources/com/github/apuex/akka/gen/util/type_test_model.xml").xml
    val entity = xml.child.filter(x => x.label == "entity" && x.attribute("name") == Some(Text("product_type")))
    entity.nonEmpty should be(true)
    entity.map(x => TextNode.text(x.attribute("name"))) should be(Seq("product_type"))
  }
}
