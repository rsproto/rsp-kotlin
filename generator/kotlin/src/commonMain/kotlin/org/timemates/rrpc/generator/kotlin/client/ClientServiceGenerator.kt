package org.timemates.rrpc.generator.kotlin.client

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import org.timemates.rrpc.codegen.typemodel.Annotations
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMService
import org.timemates.rrpc.generator.kotlin.options.ClientOptionsPropertyGenerator
import org.timemates.rrpc.generator.kotlin.typemodel.ImportRequirement

public object ClientServiceGenerator {

    public data class Result(val typeSpec: TypeSpec, val imports: List<ImportRequirement>)

    public fun generateService(
        service: RMService,
        resolver: RMResolver,
    ): Result {
        val className = ClassName("", "${service.name}Client")

        val functions = service.rpcs.map { rpc ->
            ClientRpcGenerator.generateRpc(service.name, rpc, resolver)
        }

        val (optionsProperty, imports) = ClientOptionsPropertyGenerator.generate(
            optionsMap = service.rpcs.associate { it.name to it.options },
            resolver = resolver,
        )

        return Result(
            typeSpec = TypeSpec.classBuilder(className)
                .addAnnotation(
                    Annotations.OptIn(Annotations.InternalRRpcAPI)
                )
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameter("config", Types.RRpcClientConfig)
                        .build()
                ).superclass(Types.RRpcClientService)
                .addSuperclassConstructorParameter("config")
                .addFunctions(functions)
                .addProperty(optionsProperty)
                .build(),
            imports = imports,
        )
    }
}