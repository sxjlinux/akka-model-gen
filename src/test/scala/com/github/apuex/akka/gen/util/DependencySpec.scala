package com.github.apuex.akka.gen.util

import com.github.apuex.akka.gen.ModelLoader
import org.scalatest.{FlatSpec, Matchers}

class DependencySpec extends FlatSpec with Matchers {
  "A Dependency Util" should "return dao params list" in {
    val xml = ModelLoader("src/test/resources/com/github/apuex/akka/gen/util/type_test_model.xml").xml
    val dependency = Dependency(xml)
    dependency.dao should be(Seq("product_type"))
    dependency.daoVars should be("productTypeDAO")
  }

  it should "return dao param definitions" in {
    val xml = ModelLoader("src/test/resources/com/github/apuex/akka/gen/util/type_test_model.xml").xml
    val dependency = Dependency(xml)
    dependency.daoDefs should be("productTypeDAO: ProductTypeDAO")
  }

  it should "return empty string" in {
    val xml = <model></model>
    val dependency = Dependency(xml)
    dependency.daoDefs should be("")
  }
}
