package com.github.apuex.akka.gen

object Main extends App {
  if (args.length == 0) {
    println("Usage:\n <cmd> <xml file>")
    System.exit(-1)
  }

  val xml = ModelLoader(args(0)).xml

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
