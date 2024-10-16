package org.timemates.rrpc.generator.kotlin

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.PropertySpec
import org.timemates.rrpc.codegen.exception.GenerationException
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMExtend
import org.timemates.rrpc.common.schema.RMOptions
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

public object ExtendGenerator {
    public fun generateExtend(extend: RMExtend, resolver: RMResolver): List<PropertySpec> {
        return when (extend.typeUrl) {
            // we don't support for now options generation for anything except methods, files and services.
            RMOptions.FIELD_OPTIONS,
            RMOptions.MESSAGE_OPTIONS,
            RMOptions.ONEOF_OPTIONS,
            RMOptions.ENUM_OPTIONS,
            RMOptions.ENUM_VALUE_OPTIONS,
                -> emptyList()

            RMOptions.METHOD_OPTIONS,
            RMOptions.FILE_OPTIONS,
            RMOptions.SERVICE_OPTIONS,
                -> extend.fields.map {
                OptionGenerator.generateOption(
                    field = it,
                    type = getClassNameFromExtendType(extend.typeUrl),
                    resolver = resolver,
                )
            }

            else -> throw GenerationException("Extending messages are not supported.")
        }
    }

    private fun getClassNameFromExtendType(type: RMDeclarationUrl): ClassName {
        return when (type) {
            RMOptions.METHOD_OPTIONS -> Types.Option.RPC
            RMOptions.SERVICE_OPTIONS -> Types.Option.Service
            RMOptions.FILE_OPTIONS -> Types.Option.File
            else -> throw GenerationException("Should not reach this state.")
        }
    }
}