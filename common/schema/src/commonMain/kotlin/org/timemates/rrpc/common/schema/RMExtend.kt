package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

@Serializable
public class RMExtend(
    public val typeUrl: RMDeclarationUrl,
    public val name: String,
    public val fields: List<RMField>,
    override val documentation: String?,
) : Documentable, RMNode