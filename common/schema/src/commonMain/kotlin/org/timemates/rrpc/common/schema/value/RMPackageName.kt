package org.timemates.rrpc.common.schema.value

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
public value class RMPackageName(
    public val value: String,
)