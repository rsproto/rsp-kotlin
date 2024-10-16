package org.timemates.rrpc.generator.kotlin.server

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMService

public object ServerServiceGenerator {
    public fun generateService(
        service: RMService,
        resolver: RMResolver,
    ): TypeSpec {
        return TypeSpec.classBuilder(service.name)
            .addModifiers(KModifier.ABSTRACT)
            .superclass(Types.RRpcServerService)
            .addProperty(ServerMetadataGenerator.generateMetadata(service, resolver))
            .addFunctions(service.rpcs.map { ServerRpcGenerator.generateRpc(it, resolver) })
            .build()
    }
}