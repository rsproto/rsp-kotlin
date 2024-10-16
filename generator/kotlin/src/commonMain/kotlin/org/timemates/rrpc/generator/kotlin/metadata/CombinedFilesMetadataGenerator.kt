package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.generator.kotlin.ext.newline
import kotlin.random.Random

public object CombinedFilesMetadataGenerator {
    public fun generate(
        name: String?,
        scoped: Boolean,
        resolver: RMResolver
    ): FileSpec {
        val isPrivate = name == null
        // assign random value if name wasn't provided
        val name = name ?: "MetadataLookupGroup${Random.nextInt(0, 999999)}"

        return FileSpec.builder(ClassName("org.timemates.rrpc.generated", name))
            .addType(
                TypeSpec.objectBuilder(name)
                    .addSuperinterface(
                        superinterface = Types.RM.Server.MetadataLookup,
                        delegate = buildCodeBlock {
                            add("RMResolver(")
                            withIndent {
                                resolver.resolveAvailableFiles().forEach { file ->
                                    newline()
                                    add(FileMetadataGenerator.generate(file, resolver))
                                    add(",")
                                }
                            }
                            addStatement(")")
                        }
                    )
                    .apply {
                        if (!scoped) {
                            addInitializerBlock(buildCodeBlock {
                                add("%T.register(this)", Types.RM.Server.MetadataLookup.nestedClass("Global"))
                            })
                        }

                        if (isPrivate)
                            addModifiers(KModifier.PRIVATE)
                    }
                    .build()
            )
            .build()
    }
}