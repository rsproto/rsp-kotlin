package org.timemates.rrpc.server.schema

import org.timemates.rrpc.server.module.RRpcModuleBuilder

/**
 * Registers a [SchemaService] for given module. By default, it will use
 * global instance of [SchemaLookup].
 */
public fun RRpcModuleBuilder.ServicesBuilder.schemaService(
    lookup: SchemaLookup = SchemaLookup.Global,
) {
    register(SchemaService(lookup))
}