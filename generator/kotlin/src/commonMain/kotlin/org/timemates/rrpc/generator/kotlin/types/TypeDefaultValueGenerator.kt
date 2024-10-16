package org.timemates.rrpc.generator.kotlin.types

import org.timemates.rrpc.common.schema.RMField
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

internal object TypeDefaultValueGenerator {
    fun generateTypeDefault(field: RMField): String {
        if (field.isRepeated)
            return "emptyList()"

        return when (field.typeUrl) {
            RMDeclarationUrl.INT32,
            RMDeclarationUrl.INT64,
            RMDeclarationUrl.DURATION,
            RMDeclarationUrl.FIXED32,
            RMDeclarationUrl.FIXED64,
            RMDeclarationUrl.SFIXED32,
            RMDeclarationUrl.SFIXED64,
            RMDeclarationUrl.SINT32,
            RMDeclarationUrl.SINT64,
                -> "0"

            RMDeclarationUrl.UINT32, RMDeclarationUrl.UINT64 -> "0u"
            RMDeclarationUrl.STRING -> "\"\""
            RMDeclarationUrl.BOOL -> "false"
            RMDeclarationUrl.BYTES -> "byteArrayOf()"
            RMDeclarationUrl.DOUBLE -> "0.0"
            RMDeclarationUrl.FLOAT -> "0.0f"
            else -> "null"
        }
    }

}