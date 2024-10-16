@file:OptIn(InternalRRpcAPI::class)

package org.timemates.rrpc.client

import org.timemates.rrpc.annotations.InternalRRpcAPI
import org.timemates.rrpc.client.config.RRpcClientConfig
import org.timemates.rrpc.client.options.RPCsOptions

/**
 * The abstraction for the clients that are generated by `RRpc`.
 */
public abstract class RRpcServiceClient(
    config: RRpcClientConfig,
) {
    public constructor(
        creator: RRpcClientConfig.Builder.() -> Unit,
    ) : this(RRpcClientConfig.create(creator))

    /**
     * Responsible for the logic around RSocket for interceptors and request/response data management.
     * No direct access to RSocket is used in the generated code.
     */
    protected val handler: ClientRequestHandler = ClientRequestHandler(config)

    /**
     * Container of the options related to the RPC methods by name.
     * Used and generated internally by the code-generation.
     */
    protected abstract val rpcsOptions: RPCsOptions
}
