package com.github.apuex.akka.gen.util

import com.github.apuex.akka.gen.ModelLoader
import org.scalatest.{FlatSpec, Matchers}

class TypeSpec extends FlatSpec with Matchers {
  "A Type util" should "returns true if is basic data type" in {
    val input = Seq("bool", "int", "long", "string", "text", "timestamp")
    val typeTest = Type(ModelLoader("src/test/resources/com/github/apuex/akka/gen/util/type_test_model.xml").xml)
    input.map(x => typeTest.isBasicType(x)).reduce((a, b) => a && b) should be(true)
  }

  it should "returns false if is not basic data type" in {
    val input = Seq("product_type", "any")
    val typeTest = Type(ModelLoader("src/test/resources/com/github/apuex/akka/gen/util/type_test_model.xml").xml)
    input.map(x => typeTest.isBasicType(x)).reduce((a, b) => a && b) should be(false)
  }

  it should "returns true if is enum type" in {
    val typeTest = Type(ModelLoader("src/test/resources/com/github/apuex/akka/gen/util/type_test_model.xml").xml)
    typeTest.isEnumType("product_type") should be(true)
  }

  it should "returns false if is not enum type" in {
    val typeTest = Type(ModelLoader("src/test/resources/com/github/apuex/akka/gen/util/type_test_model.xml").xml)
    typeTest.isEnumType("text") should be(false)
  }
}
