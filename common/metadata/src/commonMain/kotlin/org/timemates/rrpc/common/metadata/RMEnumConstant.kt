package org.timemates.rrpc.common.metadata

import kotlinx.serialization.Serializable

@Serializable
public data class RMEnumConstant(
    val name: String,
    val tag: Int,
    public val options: RMOptions,
    override val documentation: String?,
) : RMNode, Documentable