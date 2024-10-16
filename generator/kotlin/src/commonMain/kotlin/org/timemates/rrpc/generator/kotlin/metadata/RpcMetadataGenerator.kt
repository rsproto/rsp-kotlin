package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMRpc
import org.timemates.rrpc.common.schema.annotations.NonPlatformSpecificAccess
import org.timemates.rrpc.generator.kotlin.ext.newline

internal object RpcMetadataGenerator {
    fun generate(rpc: RMRpc, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            add("%T(", Types.RM.Rpc)
            withIndent {
                newline()
                @OptIn(NonPlatformSpecificAccess::class)
                add("name = %S,", rpc.name)
                newline()
                add(
                    format = "requestType = %1T(isStreaming = %2L, %3T(%4S)),",
                    Types.RM.StreamableTypeUrl,
                    rpc.requestType.isStreaming,
                    Types.RM.Value.TypeUrl,
                    rpc.requestType.type.value
                )
                newline()
                add(
                    format = "responseType = %1T(isStreaming = %2L, %3T(%4S)),",
                    Types.RM.StreamableTypeUrl,
                    rpc.responseType.isStreaming,
                    Types.RM.Value.TypeUrl,
                    rpc.responseType.type.value
                )
                newline()
                add("options = %P,", OptionsMetadataGenerator.generate(rpc.options, resolver))
                add(
                    "documentation = %L,",
                    if (rpc.documentation == null) "null" else "\"${rpc.documentation}\""
                )
            }
            add(")")
        }
    }
}