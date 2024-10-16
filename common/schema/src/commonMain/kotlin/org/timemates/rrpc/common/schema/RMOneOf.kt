package org.timemates.rrpc.common.schema

import kotlinx.serialization.SerialName

@SerialName("ONE_OF")
public data class RMOneOf(
    public val name: String,
    public val fields: List<RMField>,
    val documentation: String?,
    val options: RMOptions,
)