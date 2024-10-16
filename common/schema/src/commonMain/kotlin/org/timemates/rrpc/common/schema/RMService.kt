package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl


@Serializable
public class RMService(
    /**
     * Name of the service.
     */
    public val name: String,

    /**
     * List of RPCs (Remote Procedure Calls) defined in this service.
     */
    public val rpcs: List<RMRpc>,

    /**
     * Options on service-level.
     */
    public val options: RMOptions,

    /**
     * String reference representation.
     */
    public val typeUrl: RMDeclarationUrl,
) : RMNode
