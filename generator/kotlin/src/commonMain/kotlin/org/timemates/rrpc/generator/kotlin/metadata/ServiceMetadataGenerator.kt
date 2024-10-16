package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMService
import org.timemates.rrpc.generator.kotlin.ext.newline

internal object ServiceMetadataGenerator {
    fun generate(service: RMService, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            addStatement("%T(", Types.RM.Service)
            withIndent {
                addStatement("name = %S,", service.name)
                addStatement("rpcs = listOf(").withIndent {
                    service.rpcs.forEach { rpc ->
                        newline()
                        add(RpcMetadataGenerator.generate(rpc, resolver))
                        newline(before = ",")
                    }
                }
                addStatement("),")
                add("options = %P,", OptionsMetadataGenerator.generate(service.options, resolver))
                newline()
                addStatement(
                    format = "typeUrl = %T(%S),",
                    Types.RM.Value.TypeUrl,
                    service.typeUrl.value,
                )
            }
            addStatement(")")
        }
    }
}