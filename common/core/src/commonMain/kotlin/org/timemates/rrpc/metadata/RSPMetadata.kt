package org.timemates.rrpc.metadata

public sealed interface RRpcMetadata {
    public companion object {
        /**
         * This property implies the version of communication. It has
         * different versioning from RRpc as such, only for global changes to the scheme.
         */
        public const val CURRENT_SCHEMA_VERSION: Int = 1
    }

    public val extra: ExtraMetadata

    public fun extra(extra: ExtraMetadata): RRpcMetadata
}