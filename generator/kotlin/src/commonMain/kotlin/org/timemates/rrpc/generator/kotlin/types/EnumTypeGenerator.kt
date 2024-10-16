package org.timemates.rrpc.generator.kotlin.types

import com.squareup.kotlinpoet.*
import org.timemates.rrpc.codegen.typemodel.Annotations
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMType

internal object EnumTypeGenerator {
    fun generateEnum(incoming: RMType.Enum, schema: RMResolver): TypeSpec {
        val nested = incoming.nestedTypes.mapNotNull { TypeGenerator.generateType(it, schema) }

        return TypeSpec.enumBuilder(incoming.name)
            .addAnnotation(Annotations.OptIn(Types.ExperimentalSerializationApi))
            .addAnnotation(Annotations.Serializable)
            .apply {
                incoming.constants.forEach { constant ->
                    addEnumConstant(
                        constant.name,
                        TypeSpec.anonymousClassBuilder()
                            .addKdoc(constant.documentation?.replace("%", "%%").orEmpty())
                            .addAnnotation(Annotations.ProtoNumber(constant.tag))
                            .build()
                    )
                }
            }
            .addTypes(nested.map(TypeGenerator.Result::typeSpec))
            .addType(
                TypeSpec.companionObjectBuilder()
                    .addSuperinterface(Types.ProtoTypeDefinition(ClassName("", incoming.name)))
                    .addProperty(
                        PropertySpec.builder("Default", ClassName("", incoming.name))
                            .addModifiers(KModifier.OVERRIDE)
                            .initializer(incoming.constants.first { it.tag == 0 }.name)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("typeUrl", STRING)
                            .addModifiers(KModifier.OVERRIDE)
                            .initializer("%S", incoming.typeUrl)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("definition", Types.ProtoTypeDefinition(STAR))
                            .addModifiers(KModifier.OVERRIDE)
                            .getter(
                                FunSpec.getterBuilder()
                                    .addCode("return Companion")
                                    .build()
                            )
                            .build()
                    )
                    .addFunctions(nested.mapNotNull(TypeGenerator.Result::constructorFun))
                    .build()
            )
            .build()
    }
}