package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMFile
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.annotations.NonPlatformSpecificAccess
import org.timemates.rrpc.generator.kotlin.ext.newline

internal object FileMetadataGenerator {
    fun generate(file: RMFile, resolver: RMResolver): CodeBlock {
        return CodeBlock.builder().apply {
            addStatement("%T(", Types.RM.File)
            indent()
            addStatement("name = %S,", file.name)
            @OptIn(NonPlatformSpecificAccess::class)
            addStatement("packageName = %T(%S),", Types.RM.Value.PackageName, file.packageName)
            addStatement("options = %P,", OptionsMetadataGenerator.generate(file.options, resolver))
            addStatement("services = listOf(")
            withIndent {
                file.services.forEach { service ->
                    newline()
                    add(ServiceMetadataGenerator.generate(service, resolver))
                }
            }
            addStatement("),")
            addStatement("extends = listOf(")
            withIndent {
                file.extends.forEach { extend ->
                    newline()
                    add(ExtendMetadataGenerator.generate(extend, resolver))
                    add(",")
                }
            }
            addStatement("),")
        }.build()
    }
}