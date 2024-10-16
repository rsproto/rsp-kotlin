package org.timemates.rrpc.server.module

import org.timemates.rrpc.server.module.descriptors.ServiceDescriptor

/**
 * Annotation-marker for the services that are generated.
 */
public interface RRpcService {
    /**
     * Represents a descriptor for a service. Contains name, procedures and so on.
     *
     * @see ProcedureDescriptor
     */
    public val descriptor: ServiceDescriptor
}