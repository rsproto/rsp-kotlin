package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

@Serializable
public class RMTypeMemberUrl(
    public val typeUrl: RMDeclarationUrl,
    public val memberName: String,
) {
    override fun toString(): String {
        return "$typeUrl#$memberName"
    }
}