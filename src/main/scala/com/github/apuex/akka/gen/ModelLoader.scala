package com.github.apuex.akka.gen


case class ModelLoader(fileName: String) {
  val factory = new scala.xml.parsing.NoBindingFactoryAdapter
  val xml = factory.load(fileName)
  
  xml.child.filter(x => x.label == "entity")
    .foreach(x => {
        println(s"${x.label} ${x.attribute("name")}")
        x.child.filter(x => x.label == "field")
          .foreach(x => {
            println("  " + x.label)
            println("    " + x.attribute("name"))
          })
    })
}