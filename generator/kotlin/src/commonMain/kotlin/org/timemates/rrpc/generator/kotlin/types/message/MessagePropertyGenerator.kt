package org.timemates.rrpc.generator.kotlin.types.message

import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import org.timemates.rrpc.codegen.typemodel.Annotations
import org.timemates.rrpc.common.schema.RMType

internal object MessagePropertyGenerator {
    fun generateProperties(incoming: RMType.Message, parameterTypes: List<TypeName>): List<PropertySpec> {
        return incoming.fields.mapIndexed { index, field ->
            PropertySpec.builder(field.name, parameterTypes[index])
                .initializer(field.name)
                .addKdoc(field.documentation?.replace("%", "%%").orEmpty())
                .addAnnotation(Annotations.ProtoNumber(field.tag))
                .apply {
                    if (field.isRepeated && field.typeUrl.isScalar) addAnnotation(Annotations.ProtoPacked)
                }
                .build()
        }
    }
}