package org.timemates.rrpc.generator.kotlin.typemodel

import org.timemates.rrpc.common.schema.value.RMPackageName

public data class ImportRequirement(
    public val packageName: RMPackageName,
    public val simpleNames: List<String> = emptyList(),
)