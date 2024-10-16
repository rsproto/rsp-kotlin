package org.timemates.rrpc.generator.kotlin.types.message

import com.squareup.kotlinpoet.*
import org.timemates.rrpc.codegen.typemodel.Annotations
import org.timemates.rrpc.common.schema.RMOneOf
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMType
import org.timemates.rrpc.generator.kotlin.ext.asClassName
import org.timemates.rrpc.generator.kotlin.ext.capitalized
import org.timemates.rrpc.generator.kotlin.types.TypeDefaultValueGenerator

internal object OneOfGenerator {
    data class Result(
        val oneOfClass: TypeSpec,
        val property: PropertySpec,
    )

    fun generateOneOf(oneof: RMOneOf, schema: RMResolver): Result {
        val oneOfName = "${oneof.name.capitalized()}OneOf"
        val oneOfClassName = ClassName("", oneOfName)

        val oneOfClass = TypeSpec.interfaceBuilder(oneOfName)
            .addAnnotation(Annotations.Serializable)
            .addModifiers(KModifier.SEALED)
            .addTypes(oneof.fields.map { field ->
                val defaultValue = TypeDefaultValueGenerator.generateTypeDefault(field)
                val typeName = field.typeUrl.asClassName(schema)
                val builder = typeName.nestedClass("DSLBuilder")

                val fieldName = field.name.capitalized()

                TypeSpec.classBuilder(fieldName)
                    .addModifiers(KModifier.VALUE)
                    .addAnnotation(Annotations.Serializable)
                    .addAnnotation(JvmInline::class)
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter(
                                ParameterSpec.builder("value", typeName)
                                    .defaultValue(
                                        defaultValue.takeUnless { it == "null" } ?: "%T.Default", typeName,
                                    )
                                    .build()
                            )
                            .build()
                    ).apply {
                        val type = schema.resolveType(field.typeUrl)
                        if (type is RMType.Message && type.fields.isNotEmpty())
                            addFunction(
                                FunSpec.constructorBuilder()
                                    .addParameter(
                                        name = "builder",
                                        type = LambdaTypeName.get(builder, returnType = UNIT),
                                    )
                                    .callThisConstructor(CodeBlock.of("%T().also(builder).build()", builder))
                                    .build()
                            )
                    }
                    .addProperty(
                        PropertySpec.builder("value", typeName).initializer("value")
                            .addAnnotation(Annotations.ProtoNumber(field.tag))
                            .build()
                    )
                    .addType(
                        TypeSpec.companionObjectBuilder()
                            .addProperty(
                                PropertySpec.builder("Default", ClassName("", fieldName))
                                    .initializer("$fieldName()")
                                    .build()
                            )
                            .build()
                    )
                    .addSuperinterface(oneOfClassName)
                    .build()
            })
            .build()

        val property = PropertySpec.builder(oneof.name, oneOfClassName.copy(nullable = true))
            .addAnnotation(Annotations.ProtoOneOf)
            .addKdoc(oneof.documentation?.replace("%", "%%").orEmpty())
            .initializer(oneof.name)
            .build()

        return Result(oneOfClass, property)
    }
}