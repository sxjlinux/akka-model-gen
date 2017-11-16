package com.github.apuex.akka.gen.util

import org.scalatest.{FlatSpec, Matchers}

class IdentifierSpec extends FlatSpec with Matchers {
  "An Identifier" should "convert identifiers from c to pascal" in {
    val source = "hello_world"
    val expected = "HelloWorld"
    Identifier.toPascal(source) should be (expected)
  }

  it should "remove initial and trailing '_'s in identifiers from c to pascal" in {
    val source = "_hello_world_"
    val expected = "helloWorld"
    Identifier.toCamel(source) should be (expected)
  }

  it should "convert identifiers from c to camel" in {
    val source = "hello_world"
    val expected = "helloWorld"
    Identifier.toCamel(source) should be (expected)
  }

  it should "remove initial and trailing '_'s in  identifiers from c to camel" in {
    val source = "_hello_world_"
    val expected = "helloWorld"
    Identifier.toCamel(source) should be (expected)
  }
}
