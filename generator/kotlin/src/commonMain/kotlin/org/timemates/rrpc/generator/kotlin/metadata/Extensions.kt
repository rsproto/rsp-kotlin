package org.timemates.rrpc.generator.kotlin.metadata

import com.squareup.kotlinpoet.CodeBlock

internal fun CodeBlock.Builder.addDocumentation(doc: String?) {
    if (doc.isNullOrBlank())
        return

    addStatement(
        format = "documentation = %S,",
        doc,
    )
}