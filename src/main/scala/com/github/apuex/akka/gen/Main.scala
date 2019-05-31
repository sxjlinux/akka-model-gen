package com.github.apuex.akka.gen

import com.github.apuex.akka.gen.dao.mysql.ProjectGen


object Main extends App {
  if (args.length == 0) {
    println("Usage:\n" +
      "\tjava -jar <this jar> <arg list>\n" +
      "where\n" +
      "\tgenerate-projects <model xml> => generate project build scripts and configuration files.")
  } else {
    args(0) match {
      case "generate-projects" =>
        ProjectGen(args.drop(1)(0)).generate()
      case c =>
        println(s"unknown command '${c}'")
    }
  }
}
