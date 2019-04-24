package com.github.apuex.akka.gen

import com.github.apuex.akka.gen.contex.mapping.ContextMapping

object Main extends App {
  if(args.length == 0) {
    println("Usage:\n" +
      "\tjava -jar <this jar> <arg list>")
  } else {
    args(0) match {
      case "generate-context-mapping" => ContextMapping.main(args.drop(1))
      case c =>
        println(s"unknown command '${c}'")
    }
  }
}
