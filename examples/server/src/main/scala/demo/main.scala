package demo

import ba.sake.sharaf.*
import ba.sake.sharaf.undertow.UndertowSharafServer
import demo.controllers.PetsController

@main def run(): Unit = {

    val routes = new PetsController().routes

    UndertowSharafServer("localhost", 8181, routes).start()

    println(s"Server started at http://localhost:8181")
}