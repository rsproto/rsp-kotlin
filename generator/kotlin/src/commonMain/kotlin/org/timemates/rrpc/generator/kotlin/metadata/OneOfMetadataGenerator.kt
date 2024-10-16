package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMOneOf
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.generator.kotlin.ext.newline

internal object OneOfMetadataGenerator {
    fun generate(oneOf: RMOneOf, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            add("%T(", Types.RM.OneOf)
            withIndent {
                addStatement("name = %S", oneOf.name)
                addDocumentation(oneOf.documentation)
                addStatement("fields = listOf(")
                withIndent {
                    oneOf.fields.forEach { field ->
                        newline()
                        add(FieldMetadataGenerator.generate(field, resolver))
                        add(",")
                    }
                }
                addStatement("),")
                addStatement("options = %P", OptionsMetadataGenerator.generate(oneOf.options, resolver))
            }
            addStatement(")")
        }
    }
}