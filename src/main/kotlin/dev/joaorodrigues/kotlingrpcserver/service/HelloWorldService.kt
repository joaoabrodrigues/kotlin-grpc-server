package dev.joaorodrigues.kotlingrpcserver.service

import main.proto.GreeterGrpcKt
import main.proto.HelloReply
import main.proto.HelloRequest
import org.springframework.stereotype.Service

@Service
class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest): HelloReply = HelloReply
        .newBuilder()
        .setMessage("Hello ${request.name}")
        .build()
}