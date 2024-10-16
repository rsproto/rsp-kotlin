package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.RMExtend
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.generator.kotlin.ext.newline

internal object ExtendMetadataGenerator {
    fun generate(extend: RMExtend, resolver: RMResolver): CodeBlock {
        return buildCodeBlock {
            addStatement("%T(", Types.RM.Extend)
            withIndent {
                addStatement(
                    format = "typeUrl = %1T(%2S),",
                    Types.RM.Value.TypeUrl,
                    extend.typeUrl.value,
                )
                addStatement("name = %S,", extend.name)
                addStatement("fields = listOf(")
                withIndent {
                    extend.fields.forEach { field ->
                        newline()
                        add(FieldMetadataGenerator.generate(field, resolver))
                        add(",")
                    }
                }
                addStatement("),")
                addStatement(
                    format = "documentation = %L,",
                    if (extend.documentation != null) "null" else "\"${extend.documentation}\""
                )
            }
            add(")")
        }
    }
}