package com.github.apuex.akka.gen

import com.github.apuex.akka.gen.contex.mapping.ContextMappingGenerator

object Main extends App {
  if (args.length == 0) {
    println("Usage:\n" +
      "\tjava -jar <this jar> <arg list>")
  } else {
    args(0) match {
      case "generate-context-mapping" => new ContextMappingGenerator(args.drop(1)(0)).generate()
      case c =>
        println(s"unknown command '${c}'")
    }
  }
}
