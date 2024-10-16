package org.timemates.rrpc.generator.kotlin

import org.timemates.rrpc.codegen.adapters.SchemaAdapter
import org.timemates.rrpc.codegen.exception.GenerationException
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.annotations.NonPlatformSpecificAccess
import org.timemates.rrpc.generator.kotlin.metadata.CombinedFilesMetadataGenerator

public object KotlinMetadataSchemaAdapter : SchemaAdapter {
    @OptIn(NonPlatformSpecificAccess::class)
    override fun process(
        config: SchemaAdapter.Config,
        resolver: RMResolver,
    ): RMResolver {
        if (config.metadata.enabled && config.metadata.scoped && config.metadata.name == null)
            throw GenerationException("Metadata config requires generation to be scoped, but name isn't provided.")

        resolver.resolveAvailableFiles()
            .filterNot { file -> file.packageName.value.startsWith("wire") }
            .toList()
            .let {
                CombinedFilesMetadataGenerator.generate(
                    name = config.metadata.name,
                    scoped = config.metadata.scoped,
                    resolver = RMResolver(it)
                )
            }.apply {
                config.output.forEach { outputKind ->
                    when (outputKind) {
                        is SchemaAdapter.Config.Output.Custom -> {}
                        is SchemaAdapter.Config.Output.FS -> writeTo(outputKind.path.toNioPath())
                    }
                }
            }

        return resolver
    }

}