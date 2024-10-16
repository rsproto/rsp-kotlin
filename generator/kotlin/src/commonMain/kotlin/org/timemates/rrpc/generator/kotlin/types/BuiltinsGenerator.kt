package org.timemates.rrpc.generator.kotlin.types

import com.squareup.kotlinpoet.*
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

internal object BuiltinsGenerator {
    fun generateBuiltin(incoming: RMDeclarationUrl): TypeName {
        return when (incoming) {
            RMDeclarationUrl.BOOL -> BOOLEAN
            RMDeclarationUrl.INT32, RMDeclarationUrl.SINT32, RMDeclarationUrl.FIXED32, RMDeclarationUrl.SFIXED32, RMDeclarationUrl.INT32_VALUE -> INT
            RMDeclarationUrl.INT64, RMDeclarationUrl.SINT64, RMDeclarationUrl.FIXED64, RMDeclarationUrl.SFIXED64, RMDeclarationUrl.INT64_VALUE -> LONG
            RMDeclarationUrl.BYTES, RMDeclarationUrl.BYTES_VALUE -> BYTE_ARRAY
            RMDeclarationUrl.FLOAT, RMDeclarationUrl.FLOAT_VALUE -> FLOAT
            RMDeclarationUrl.UINT32, RMDeclarationUrl.UINT32_VALUE -> U_INT
            RMDeclarationUrl.UINT64, RMDeclarationUrl.UINT64_VALUE -> U_LONG
            RMDeclarationUrl.DOUBLE, RMDeclarationUrl.DOUBLE_VALUE -> DOUBLE
            RMDeclarationUrl.STRING -> STRING
            else -> error("Unsupported protobuf type $incoming")
        }
    }

}