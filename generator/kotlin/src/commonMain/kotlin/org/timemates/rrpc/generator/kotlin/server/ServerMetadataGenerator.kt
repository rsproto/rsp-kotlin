package org.timemates.rrpc.generator.kotlin.server

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.buildCodeBlock
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.*
import org.timemates.rrpc.common.schema.annotations.NonPlatformSpecificAccess
import org.timemates.rrpc.generator.kotlin.ext.asClassName
import org.timemates.rrpc.generator.kotlin.ext.newline
import org.timemates.rrpc.generator.kotlin.options.RawOptionsCodeGeneration

public object ServerMetadataGenerator {
    public fun generateMetadata(
        service: RMService,
        resolver: RMResolver,
    ): PropertySpec {
        return PropertySpec.builder("descriptor", Types.ServiceDescriptor)
            .addModifiers(KModifier.OVERRIDE)
            .initializer(
                buildCodeBlock {
                    add("%T(", Types.ServiceDescriptor)
                    newline()
                    indent()
                    add("name = %S,", service.name)
                    newline()
                    add("procedures = listOf(")
                    indent()

                    service.rpcs.forEach { rpc ->
                        val requestType = rpc.requestType.asClassName(resolver)
                        val responseType = rpc.responseType.asClassName(resolver)

                        val type = when {
                            rpc.isRequestResponse -> Types.ProcedureDescriptor.requestResponse
                            rpc.isRequestStream -> Types.ProcedureDescriptor.requestStream
                            rpc.isRequestChannel -> Types.ProcedureDescriptor.requestChannel
                            else -> error("Unsupported type of request for ${service.name}#${rpc.name}")
                        }

                        newline()
                        add("%T(", type)
                        newline()
                        indent()
                        @OptIn(NonPlatformSpecificAccess::class)
                        add("name = %S", rpc.name)
                        newline(",")
                        add("inputSerializer = %T.serializer()", requestType)
                        newline(",")
                        add("outputSerializer = %T.serializer()", responseType)
                        newline(",")
                        add("procedure = { context, data -> %L(context, data) }", rpc.kotlinName())
                        newline(",")
                        add(
                            "options = %L",
                            RawOptionsCodeGeneration.generate(rpc.options, resolver, RMOptions.METHOD_OPTIONS)
                        )
                        newline(before = ",")
                        unindent()
                        add("),")
                    }
                    unindent()
                    newline()
                    add("),")
                    newline()
                    add(
                        "options = %L",
                        RawOptionsCodeGeneration.generate(service.options, resolver, RMOptions.SERVICE_OPTIONS)
                    )
                    newline()
                    unindent()
                    add(")")
                }
            )
            .build()
    }
}