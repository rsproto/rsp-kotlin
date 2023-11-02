package com.y9vad9.rsocket.proto.codegen.types

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.wire.schema.MessageType
import com.y9vad9.rsocket.proto.codegen.Annotations
import com.y9vad9.rsocket.proto.codegen.Transformer

internal object MessageTypeTransformer : Transformer<MessageType, TypeSpec> {
    override fun transform(incoming: MessageType): TypeSpec {
        val parameterTypes = incoming.declaredFields.map { field ->
            val fieldType = field.type!!

            when {
                fieldType.isScalar || fieldType.isWrapper || field.isRepeated || fieldType.isMap -> BuiltinsTransformer.transform(fieldType)
                else -> ClassName(
                    fieldType.enclosingTypeOrPackage ?: "",
                    fieldType.simpleName,
                ).let {
                    when {
                        field.isRepeated -> LIST.parameterizedBy(it)
                        field.isOneOf -> ANY
                        else -> it
                    }
                }.copy(nullable = true)
            }
        }

        val properties = incoming.declaredFields.mapIndexed { index, field ->
            PropertySpec.builder(field.name, parameterTypes[index])
                .initializer(field.name)
                .addAnnotation(Annotations.ProtoNumber(field.tag))
                .build()
        }

        return TypeSpec.classBuilder(incoming.name)
            .addKdoc(incoming.documentation)
            .addAnnotation(Annotations.Serializable)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameters(incoming.declaredFields.mapIndexed { index, field ->
                        val type = parameterTypes[index]

                        ParameterSpec.builder(field.name, type)
                            .defaultValue(
                                if (type.isNullable)
                                    "null"
                                else field.default ?: TypeDefaultValueTransformer.transform(field.type!!)
                            )
                            .build()
                    })
                    .build()
            )
            .addType(
                TypeSpec.companionObjectBuilder()
                    .addFunction(
                        FunSpec.builder("create")
                            .addParameter(
                                "builder", LambdaTypeName.get(receiver = ClassName("", "Builder"), returnType = ClassName("", incoming.name))
                            )
                            .addCode("return Builder().apply(builder).build()")
                            .build()
                    )
                    .build()
            )
            .addTypes(incoming.nestedTypes.map { TypeTransformer.transform(it) })
            .addType(MessageBuilderTransformer.transform(incoming.name to properties))
            .addProperties(properties)
            .build()
    }
}