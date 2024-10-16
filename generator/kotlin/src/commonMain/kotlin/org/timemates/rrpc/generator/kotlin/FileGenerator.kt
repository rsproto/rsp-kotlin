package org.timemates.rrpc.generator.kotlin

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import org.timemates.rrpc.codegen.typemodel.Annotations
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMFile
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.kotlinPackage
import org.timemates.rrpc.generator.kotlin.client.ClientServiceGenerator
import org.timemates.rrpc.generator.kotlin.ext.addImports
import org.timemates.rrpc.generator.kotlin.server.ServerServiceGenerator
import org.timemates.rrpc.generator.kotlin.types.TypeGenerator

internal object FileGenerator {
    fun generateFile(
        resolver: RMResolver,
        file: RMFile,
        clientGeneration: Boolean,
        serverGeneration: Boolean,
    ): FileSpec {
        val fileName = ClassName(file.kotlinPackage().value, file.name)

        return FileSpec.builder(fileName).apply {
            addAnnotation(Annotations.Suppress("UNUSED", "RedundantVisibilityModifier"))
            addAnnotation(Annotations.OptIn(Types.ExperimentalSerializationApi))
            addFileComment(Constant.GENERATED_COMMENT)

            file.extends.forEach {
                ExtendGenerator.generateExtend(it, resolver).forEach(::addProperty)
            }

            if (serverGeneration && file.services.isNotEmpty()) {
                addImport(Types.ServiceDescriptor.packageName, Types.ServiceDescriptor.simpleName)
                addImport(Types.ServiceDescriptor.packageName, Types.ProcedureDescriptor.base.simpleName)
                addTypes(file.services.map { ServerServiceGenerator.generateService(it, resolver) })
            }

            if (clientGeneration && file.services.isNotEmpty()) {
                val genResult = file.services.map { ClientServiceGenerator.generateService(it, resolver) }
                addTypes(genResult.map(ClientServiceGenerator.Result::typeSpec))
                addImports(genResult.flatMap { it.imports })
            }

            val types = file.types.mapNotNull { TypeGenerator.generateType(it, resolver) }

            if (types.isNotEmpty()) {
                addImport(Types.ProtoType.packageName, Types.ProtoType.simpleName)
            }

            types.mapNotNull(TypeGenerator.Result::constructorFun)
                .forEach(::addFunction)
            addTypes(types.map { it.typeSpec })
        }.build()
    }
}