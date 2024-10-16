package org.timemates.rrpc.server.schema

import org.timemates.rrpc.common.schema.RMResolver

public fun MetadataLookup(resolver: RMResolver): SchemaLookup = DelegatedSchemaLookupGroup(resolver)

public interface SchemaLookup : RMResolver {
    public companion object Global : SchemaLookup by _resolver {
        /**
         * Register a [SchemaLookup] to the global scope that is used by default.
         */
        public fun register(group: SchemaLookup) {
            _resolver = DelegatedSchemaLookupGroup(RMResolver(this, group))
        }

        /**
         * Register a [RMResolver] to the global scope that is used by default.
         */
        public fun register(resolver: RMResolver): Unit = register(MetadataLookup(resolver))
    }
}

internal class DelegatedSchemaLookupGroup(resolver: RMResolver) : SchemaLookup, RMResolver by _resolver

private var _resolver: SchemaLookup = DelegatedSchemaLookupGroup(RMResolver(emptyList()))
