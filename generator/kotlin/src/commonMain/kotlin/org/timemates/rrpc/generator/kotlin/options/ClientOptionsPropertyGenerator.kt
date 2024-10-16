package org.timemates.rrpc.generator.kotlin.options

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.buildCodeBlock
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMOptions
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.kotlinPackage
import org.timemates.rrpc.generator.kotlin.ext.newline
import org.timemates.rrpc.generator.kotlin.typemodel.ImportRequirement

public object ClientOptionsPropertyGenerator {
    private val RPCS_OPTIONS_TYPE = ClassName("org.timemates.rrpc.client.options", "RPCsOptions")

    public data class Result(
        val property: PropertySpec,
        val imports: List<ImportRequirement>,
    )

    public fun generate(
        optionsMap: Map<String, RMOptions>,
        resolver: RMResolver,
    ): Result {
        val imports = mutableListOf<ImportRequirement>()

        val code = buildCodeBlock {
            if (optionsMap.isEmpty()) {
                add("RPCsOptions.EMPTY")
                return@buildCodeBlock
            }
            add("%T(", RPCS_OPTIONS_TYPE)
            indent()

            optionsMap.filter { it.value.list.isNotEmpty() }.forEach { (rpc, options) ->
                add("\n%S to %T(", rpc, Types.Options)
                newline()
                indent()
                add("mapOf(")
                newline()
                indent()
                options.list.forEach { option ->
                    val field = resolver.resolveField(option.fieldUrl)!!
                    val type = field.typeUrl

                    resolver.resolveFileOf(type)
                        ?.kotlinPackage()
                        ?.let {
                            imports.add(ImportRequirement(it, listOf(field.name)))
                        }

                    add("%T.${option.name} to ", Types.Option.RPC)
                    add(OptionValueGenerator.generate(type, option.value, resolver))
                    add(",\n")
                }
                unindent()
                add(")")
                unindent()
                newline()
                add("),")
            }
            newline()
            unindent()
            add(")")
        }

        return Result(
            property = PropertySpec.builder("rpcsOptions", RPCS_OPTIONS_TYPE)
                .addModifiers(KModifier.OVERRIDE)
                .initializer(code)
                .build(),
            imports = imports,
        )
    }
}