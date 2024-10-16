package org.timemates.rrpc.generator.kotlin

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.timemates.rrpc.common.schema.RMField
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl
import org.timemates.rrpc.generator.kotlin.ext.asClassName

public object OptionGenerator {
    public fun generateOption(field: RMField, type: ClassName, resolver: RMResolver): PropertySpec =
        PropertySpec.builder(field.name, type.parameterizedBy(toKotlinTypeName(field.typeUrl, resolver)))
            .addKdoc(field.documentation?.replace("%", "%%").orEmpty())
            .receiver(type.nestedClass("Companion"))
            .delegate("lazy·{·%T(%S, %L)·}", type, field.name, field.tag)
            .build()

    private fun toKotlinTypeName(type: RMDeclarationUrl, resolver: RMResolver): TypeName {
        return when (type) {
            RMDeclarationUrl.STRING, RMDeclarationUrl.STRING_VALUE -> return STRING
            RMDeclarationUrl.DOUBLE, RMDeclarationUrl.DOUBLE_VALUE -> return DOUBLE
            RMDeclarationUrl.BYTES, RMDeclarationUrl.BYTES_VALUE -> return BYTE_ARRAY
            RMDeclarationUrl.BOOL, RMDeclarationUrl.BOOL_VALUE -> return BOOLEAN
            RMDeclarationUrl.UINT64, RMDeclarationUrl.UINT64_VALUE -> return U_LONG
            RMDeclarationUrl.UINT32, RMDeclarationUrl.UINT32_VALUE -> return U_INT
            RMDeclarationUrl.INT32, RMDeclarationUrl.INT32_VALUE, RMDeclarationUrl.SFIXED32, RMDeclarationUrl.FIXED32 -> return INT
            RMDeclarationUrl.INT64, RMDeclarationUrl.INT64_VALUE, RMDeclarationUrl.SFIXED64, RMDeclarationUrl.FIXED64 -> LONG
            RMDeclarationUrl.FLOAT, RMDeclarationUrl.FLOAT_VALUE -> return FLOAT
            else -> type.asClassName(resolver)
        }
    }
}