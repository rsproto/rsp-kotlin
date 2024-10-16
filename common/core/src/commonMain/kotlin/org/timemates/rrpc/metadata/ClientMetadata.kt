@file:OptIn(ExperimentalSerializationApi::class)

package org.timemates.rrpc.metadata

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.jvm.JvmField
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Represents metadata information.
 *
 * @property serviceName The name of the service.
 * @property procedureName The name of the procedure.
 * @property extra Additional key-value pairs of metadata.
 */
@Serializable
public data class ClientMetadata @JvmOverloads constructor(
    @ProtoNumber(1)
    public val schemaVersion: Int = RRpcMetadata.CURRENT_SCHEMA_VERSION,
    @ProtoNumber(2)
    public val serviceName: String = "",
    @ProtoNumber(3)
    public val procedureName: String = "",
    @ProtoNumber(4)
    public override val extra: ExtraMetadata = ExtraMetadata.EMPTY,
) : RRpcMetadata {
    public companion object {
        @JvmField
        public val EMPTY: ClientMetadata = ClientMetadata()
    }

    override fun extra(extra: ExtraMetadata): ClientMetadata {
        return if (extra == this.extra)
            this
        else copy(extra = extra)
    }
}