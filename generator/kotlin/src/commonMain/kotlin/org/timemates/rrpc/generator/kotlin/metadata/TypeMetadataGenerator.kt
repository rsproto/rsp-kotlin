package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMExtend
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMType
import org.timemates.rrpc.generator.kotlin.ext.newline

internal object TypeMetadataGenerator {
    fun generate(type: RMType, resolver: RMResolver): CodeBlock {
        return when (type) {
            is RMType.Enclosing -> generateEnclosing(type, resolver)
            is RMType.Enum -> generateEnum(type, resolver)
            is RMType.Message -> generateMessage(type, resolver)
        }
    }

    private fun generateMessage(message: RMType.Message, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            add("%T(", Types.RM.Enum)
            withIndent {
                addStatement("name = %S", message.name)
                addDocumentation(message.documentation)
                addStatement("fields = listOf(")
                withIndent {
                    message.fields.forEach { field ->
                        newline()
                        add(FieldMetadataGenerator.generate(field, resolver))
                        add(",")
                    }
                }
                addStatement("),")
                addStatement("oneOfs = listOf(")
                withIndent {
                    message.oneOfs.forEach { oneOf ->
                        newline()
                        add(OneOfMetadataGenerator.generate(oneOf, resolver))
                    }
                }
                addStatement("),")
                addStatement("options = %P", OptionsMetadataGenerator.generate(message.options, resolver))
                addStatement("typeUrl = %T(%S)", Types.RM.Value.TypeUrl, message.typeUrl)
                addNestedTypes(message.nestedTypes, resolver)
                addNestedExtends(message.nestedExtends, resolver)
            }
            addStatement(")")
        }
    }

    private fun generateEnclosing(enclosing: RMType.Enclosing, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            add("%T(", Types.RM.Enum)
            withIndent {
                addStatement("name = %S,", enclosing.name)
                addDocumentation(enclosing.documentation)
                addStatement("typeUrl = %T(%S),", Types.RM.Value.TypeUrl, enclosing.typeUrl)
                addNestedTypes(enclosing.nestedTypes, resolver)
                addNestedExtends(enclosing.nestedExtends, resolver)
            }
            add(")")
        }
    }

    private fun generateEnum(enum: RMType.Enum, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            add("%T(", Types.RM.Enum)
            withIndent {
                addStatement("name = %S", enum.name)
                addStatement("constants = listOf(")
                withIndent {
                    enum.constants.forEach { constant ->
                        newline()
                        add("%T(")
                        withIndent {
                            addStatement("name = %S,", constant.name)
                            addStatement("tag = %L,", constant.tag)
                            addStatement("options = %P,", OptionsMetadataGenerator.generate(constant.options, resolver))
                            addStatement(
                                format = "documentation = %L,",
                                if (constant.documentation == null) "null" else "\"${constant.documentation}\""
                            )
                        }
                        add("),")
                    }
                }
                add("),")
                addDocumentation(enum.documentation)
                addStatement("options = %P,", OptionsMetadataGenerator.generate(enum.options, resolver))
                addNestedTypes(enum.nestedTypes, resolver)
                addNestedExtends(enum.nestedExtends, resolver)
                addStatement(
                    format = "typeUrl = %T(%S),",
                    Types.RM.Value.TypeUrl,
                    enum.typeUrl.value
                )
            }
            add(")")
        }
    }

    private fun CodeBlock.Builder.addNestedTypes(nestedTypes: List<RMType>, resolver: RMResolver) {
        addStatement("nestedTypes = listOf(")
        withIndent {
            nestedTypes.forEach { nestedType ->
                newline()
                add(generate(nestedType, resolver))
                add(",")
            }
        }
        add("),")
    }

    private fun CodeBlock.Builder.addNestedExtends(nestedExtends: List<RMExtend>, resolver: RMResolver) {
        addStatement("nestedExtends = listOf(")
        withIndent {
            nestedExtends.forEach { nested ->
                newline()
                add(ExtendMetadataGenerator.generate(nested, resolver))
                add(",")
            }
        }
        add("),")
    }
}
