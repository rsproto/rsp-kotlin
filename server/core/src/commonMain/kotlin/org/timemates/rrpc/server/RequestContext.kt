package org.timemates.rrpc.server

import org.timemates.rrpc.instances.InstanceContainer
import org.timemates.rrpc.interceptors.InterceptorContext
import org.timemates.rrpc.metadata.ClientMetadata
import org.timemates.rrpc.options.OptionsWithValue

public data class RequestContext(
    public val instances: InstanceContainer,
    public val metadata: ClientMetadata,
    public val options: OptionsWithValue,
)

internal fun InterceptorContext<ClientMetadata>.toRequestContext(): RequestContext {
    return RequestContext(
        instances, metadata, options,
    )
}