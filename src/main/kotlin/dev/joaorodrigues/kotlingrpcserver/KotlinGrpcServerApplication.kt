package dev.joaorodrigues.kotlingrpcserver

import dev.joaorodrigues.kotlingrpcserver.server.HelloWorldServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinGrpcServerApplication

fun main(args: Array<String>) {
    val applicationContext = runApplication<KotlinGrpcServerApplication>(*args)
    val grpcServer = applicationContext.getBean(HelloWorldServer::class.java)

    grpcServer.start()
    grpcServer.blockUntilShutdown()
}

