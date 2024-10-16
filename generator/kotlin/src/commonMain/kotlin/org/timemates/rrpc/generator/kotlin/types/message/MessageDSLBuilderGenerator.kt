package org.timemates.rrpc.generator.kotlin.types.message

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.timemates.rrpc.common.schema.RMField
import org.timemates.rrpc.generator.kotlin.types.TypeDefaultValueGenerator

internal object MessageDSLBuilderGenerator {
    fun generateMessageBuilder(
        name: String,
        declaredFields: List<Pair<PropertySpec, RMField>>,
        oneOfs: List<PropertySpec>,
    ): TypeSpec {
        val returnParametersSet = (declaredFields.map { it.first.name } + oneOfs.map { it.name })
            .joinToString(", ")

        return TypeSpec.classBuilder("DSLBuilder")
            .addProperties(declaredFields.map { (spec, type) ->
                spec.toBuilder().initializer(TypeDefaultValueGenerator.generateTypeDefault(type)).mutable(true).also {
                    it.annotations.clear()
                    it.addAnnotation(JvmField::class)
                }.build()
            })
            .addProperties(oneOfs.map {
                it.toBuilder().apply { annotations.clear() }.mutable(true).initializer("null").build()
            })
            .addFunction(
                FunSpec.builder("build")
                    .addCode("return ${name}(${returnParametersSet})")
                    .returns(ClassName("", name))
                    .build()
            )
            .build()
    }

}