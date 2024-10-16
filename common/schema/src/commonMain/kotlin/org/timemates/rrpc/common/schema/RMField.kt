package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

@Serializable
public class RMField(
    public val tag: Int,
    public val name: String,
    public val options: RMOptions,
    override val documentation: String?,
    public val typeUrl: RMDeclarationUrl,
    public val isRepeated: Boolean,
    public val isInOneOf: Boolean = false,
    public val isExtension: Boolean = false,
) : Documentable, RMNode

