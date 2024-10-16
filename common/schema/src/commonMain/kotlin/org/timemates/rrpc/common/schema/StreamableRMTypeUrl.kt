package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

/**
 * Denotes that type might be of streaming type.
 */
@Serializable
public class StreamableRMTypeUrl(public val isStreaming: Boolean, public val type: RMDeclarationUrl)