package com.github.apuex.akka.gen


object Main extends App {
  if (args.length == 0) {
    println("Usage:\n" +
      "\tjava -jar <this jar> <arg list>")
  } else {
    args(0) match {
      case c =>
        println(s"unknown command '${c}'")
    }
  }
}
