package org.timemates.rrpc.generator.kotlin.types.message

import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMType
import org.timemates.rrpc.generator.kotlin.ext.asClassName
import org.timemates.rrpc.generator.kotlin.types.BuiltinsGenerator

internal object MessageParameterTypeGenerator {
    fun generateParameterTypes(incoming: RMType.Message, schema: RMResolver): List<TypeName> {
        return incoming.fields.map { field ->
            val fieldType = field.typeUrl
            when {
                fieldType.isScalar || fieldType.isWrapper || fieldType.isMap -> BuiltinsGenerator.generateBuiltin(
                    fieldType
                ).let {
                    if (field.isRepeated) LIST.parameterizedBy(it) else if (fieldType.isWrapper) it.copy(nullable = true) else it
                }

                else -> fieldType.asClassName(schema).let {
                    if (field.isRepeated) LIST.parameterizedBy(it) else it.copy(nullable = true)
                }
            }
        }
    }
}