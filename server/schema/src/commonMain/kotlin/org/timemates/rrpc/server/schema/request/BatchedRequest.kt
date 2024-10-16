package org.timemates.rrpc.server.schema.request

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.RMNode
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

/**
 * Represents a batched request to retrieve multiple metadata entities by their declaration URLs.
 *
 * @property urls A list of RMDeclarationUrl objects, representing the metadata to retrieve.
 */
@Serializable
public data class BatchedRequest(val urls: List<RMDeclarationUrl>) {
    /**
     * Response structure for batched requests.
     * Contains a map of RMDeclarationUrl to the corresponding resolved metadata (or null if not found).
     *
     * @param results A map where each RMDeclarationUrl is associated with the corresponding RMNode (or null if not found).
     */
    @Serializable
    public data class Response<R : RMNode>(public val services: Map<RMDeclarationUrl, R?>)
}