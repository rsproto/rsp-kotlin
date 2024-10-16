package org.timemates.rrpc.server.module

import io.ktor.server.routing.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket

/**
 * Creates and configures an RSocket server with the specified endpoint and RSocketProtoServer.
 *
 * @param endpoint The endpoint to bind the server to. Default value is `/rrpc`.
 * @param module The module that will be handling the incoming requests.
 */
public fun Routing.rrpcEndpoint(endpoint: String = "/rrpc", module: RRpcModule) {
    rSocket(endpoint) {
        RSocketRequestHandler {
            RRpcModuleHandler(module).setup(this)
        }
    }
}

/**
 * Creates an RSocket server endpoint on the specified routing path.
 *
 * @param endpoint The routing path for the RSocket server.
 * @param block The configuration block for the RRpcModuleBuilder.
 */
public fun Routing.rrpcEndpoint(endpoint: String = "/rrpc", block: RRpcModuleBuilder.() -> Unit) {
    rrpcEndpoint(endpoint, RRpcModuleBuilder().apply(block).build())
}
