package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMField
import org.timemates.rrpc.common.schema.RMResolver

internal object FieldMetadataGenerator {
    fun generate(field: RMField, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            addStatement("%T(")
            withIndent {
                addStatement("tag = %L,", field.tag)
                addStatement("name = %S,", field.name)
                addStatement(
                    format = "options = %P,",
                    OptionsMetadataGenerator.generate(field.options, resolver)
                )
                addStatement(
                    "documentation = %L,",
                    if (field.documentation == null) "null" else "\"${field.documentation}\""
                )
                addStatement(
                    format = "typeUrl = %T(%S)",
                    Types.RM.Value.TypeUrl,
                    field.typeUrl.value,
                )
                addStatement("isRepeated = %L,", field.isRepeated)
                addStatement("isInOneOf = %L,", field.isInOneOf)
                addStatement("isExtension = %L,", field.isExtension)
            }
            add(")")
        }
    }
}