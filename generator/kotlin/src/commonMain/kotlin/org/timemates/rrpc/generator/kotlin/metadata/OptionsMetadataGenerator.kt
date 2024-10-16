package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMOption
import org.timemates.rrpc.common.schema.RMOptions
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.generator.kotlin.ext.newline

internal object OptionsMetadataGenerator {
    fun generate(options: RMOptions, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            add("%T(", Types.RM.Options)
            newline()
            indent()
            add("listOf(")
            options.list.forEach { option ->
                val field = resolver.resolveField(option.fieldUrl)!!

                withIndent {
                    newline()
                    add("%T(", Types.RM.Option)
                    newline()
                    withIndent {
                        add("name = %S,", option.name)
                        newline()
                        add("tag = %L,", field.tag)
                        newline()
                        add("fieldUrl = %1T(", Types.RM.TypeMemberUrl, option.fieldUrl.typeUrl)
                        newline()
                        withIndent {
                            add("typeUrl = %S,", option.fieldUrl.typeUrl)
                            add("memberName = %S,", option.fieldUrl.memberName)
                        }
                        add("),")
                        option.value?.let { value ->
                            add("value = %P,", generateValue(value))
                        }
                    }
                    add("),")
                }
            }
            add(")")
            unindent()
        }
    }

    private fun generateValue(value: RMOption.Value): CodeBlock {
        return buildCodeBlock {
            when (value) {
                is RMOption.Value.Raw -> add("%T(%S)", Types.RM.OptionValueRaw)
                is RMOption.Value.RawMap -> {
                    add("%T(", Types.RM.Option)
                    newline()
                    withIndent {
                        add("map = mapOf(")
                        withIndent {
                            value.map.forEach { key, value ->
                                newline()
                                add(
                                    format = "%1T(%2S) to %1T(%3S),",
                                    Types.RM.OptionValueRaw,
                                    key.string,
                                    value.string,
                                )
                            }
                        }
                        add("),")
                    }
                    add("),")
                }
                is RMOption.Value.MessageMap -> {
                    add("%T(", Types.RM.OptionValueMessageMap)
                    newline()
                    withIndent {
                        add("map = mapOf(")
                        withIndent {
                            value.map.forEach { key, value ->
                                newline()
                                add(
                                    format = "%1T(%2S, %3S) to %P",
                                    Types.RM.TypeMemberUrl,
                                    key.typeUrl,
                                    key.memberName,
                                    generateValue(value)
                                )
                                add(")")
                            }
                        }
                        add("),")
                    }
                }
            }
        }
    }
}