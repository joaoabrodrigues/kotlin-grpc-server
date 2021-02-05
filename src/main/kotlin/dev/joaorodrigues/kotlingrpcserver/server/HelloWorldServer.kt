package dev.joaorodrigues.kotlingrpcserver.server

import dev.joaorodrigues.kotlingrpcserver.service.HelloWorldService
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.stereotype.Component

@Component
class HelloWorldServer(service: HelloWorldService) {
    private final val port = System.getenv("PORT")?.toInt() ?: 50051

    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(service)
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}