package org.timemates.rrpc.metadata

import kotlinx.serialization.Serializable
import org.timemates.rrpc.instances.InstanceContainer
import org.timemates.rrpc.instances.ProvidableInstance
import kotlin.jvm.JvmInline

/**
 * Represents extra metadata within incoming request associated with a coroutine context.
 *
 * @property extra The map containing the extra metadata.
 */
@JvmInline
@Serializable
public value class ExtraMetadata(public val extra: Map<String, ByteArray>) : ProvidableInstance {
    public companion object : ProvidableInstance.Key<ExtraMetadata> {
        public val EMPTY: ExtraMetadata = ExtraMetadata(emptyMap())
    }

    override val key: ProvidableInstance.Key<*>
        get() = Companion

    public operator fun get(key: String): ByteArray? = extra[key]
}

public val InstanceContainer.extraMetadata: ExtraMetadata
    get() = getInstance(ExtraMetadata) ?: error("Called from the wrong context")