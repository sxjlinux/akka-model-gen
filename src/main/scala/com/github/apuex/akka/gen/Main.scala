package com.github.apuex.akka.gen

import scala.xml.{Node, Text}

object Main extends App {
  if (args.length == 0) {
    println("Usage:\n <cmd> <xml file>")
    System.exit(-1)
  }

  val xml: Node = ModelLoader(args(0)).xml

  xml.child.filter(x => x.label == "entity")
    .foreach(x => {
      println(s"${x.label} ${x.attribute("name")}")
      x.child.filter(x => x.label == "field")
        .foreach(x => {
          println("  " + x.label)
          println("    " + x.attribute("name"))
        })
    })

  xml.child.filter(x => x.label == "entity")
    .foreach(x => {
      x.child.filter(x => x.label == "field")
        .foreach(x => {
          println(x.attribute("name") == Some(Text("product_id")))
        })
    })
}
