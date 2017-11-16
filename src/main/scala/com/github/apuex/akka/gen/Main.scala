package com.github.apuex.akka.gen

object Main extends App {
  if (args.length == 0) {
    println("Usage:\n <cmd> <xml file>")
    System.exit(-1)
  }

  val doc = ModelLoader(args(0)).xml 
}
