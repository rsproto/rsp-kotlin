package org.timemates.rsproto.codegen

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.wire.schema.ProtoFile
import com.squareup.wire.schema.Schema
import org.timemates.rsproto.codegen.services.client.ClientServiceApiGenerator
import org.timemates.rsproto.codegen.services.server.ServerServiceTransformer
import org.timemates.rsproto.codegen.types.TypeTransformer

internal object FileTransformer {
    fun transform(schema: Schema, protoFile: ProtoFile, clientGeneration: Boolean, serverGeneration: Boolean): FileSpec {
        val fileName = ClassName(protoFile.javaPackage() ?: protoFile.packageName ?: "", protoFile.name())

        return FileSpec.builder(fileName).apply {
            addAnnotation(Annotations.Suppress("UNUSED", "RedundantVisibilityModifier"))
            addAnnotation(Annotations.OptIn(Types.experimentalSerializationApi))
            addFileComment(Constant.GENERATED_COMMENT)

            if(serverGeneration && protoFile.services.isNotEmpty()) {
                addImport(Types.serviceDescriptor.packageName, Types.serviceDescriptor.simpleName)
                addTypes(protoFile.services.map { ServerServiceTransformer.transform(it, schema) })
            }

            if(clientGeneration && protoFile.services.isNotEmpty()) {
                addTypes(protoFile.services.map { ClientServiceApiGenerator.generate(it, schema) })
                addImport(Types.payload.packageName, Types.payload.simpleName)
                addImport("kotlinx.serialization", listOf("encodeToByteArray", "decodeFromByteArray"))
                addImport("kotlinx.coroutines.flow", listOf("map"))
                addImport("io.ktor.utils.io.core", "readBytes")
            }

            val types = protoFile.types.map { TypeTransformer.transform(it, schema) }

            types.mapNotNull(TypeTransformer.Result::constructorFun)
                .forEach(::addFunction)
            addTypes(types.map { it.typeSpec })
        }.build()
    }
}