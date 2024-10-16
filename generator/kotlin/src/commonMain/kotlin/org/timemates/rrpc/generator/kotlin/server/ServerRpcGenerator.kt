package org.timemates.rrpc.generator.kotlin.server

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.*
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl
import org.timemates.rrpc.generator.kotlin.ext.asClassName
import org.timemates.rrpc.generator.kotlin.ext.deprecated

public object ServerRpcGenerator {
    public fun generateRpc(
        rpc: RMRpc,
        resolver: RMResolver,
    ): FunSpec {
        val (requestType, returnType) = getRpcType(rpc, resolver)

        return FunSpec.builder(rpc.kotlinName())
            .addKdoc(rpc.documentation?.replace("%", "%%").orEmpty())
            .addModifiers(KModifier.ABSTRACT)
            .deprecated(rpc.options.isDeprecated)
            .addParameter("context", Types.RequestContext)
            .apply {
                if (rpc.isRequestResponse)
                    addModifiers(KModifier.SUSPEND)

                if (rpc.requestType.type != RMDeclarationUrl.ACK)
                    addParameter(
                        "request",
                        requestType,
                    )

                if (rpc.responseType.type != RMDeclarationUrl.ACK)
                    returns(returnType)
            }
            .build()
    }

    private fun getRpcType(rpc: RMRpc, schema: RMResolver): Pair<TypeName, TypeName> {
        val requestClassName = rpc.requestType.asClassName(schema)
        val responseClassName = rpc.responseType.asClassName(schema)

        return (if (rpc.requestType.isStreaming) Types.Flow(requestClassName) else requestClassName) to
            (if (rpc.responseType.isStreaming) Types.Flow(responseClassName) else responseClassName)
    }
}