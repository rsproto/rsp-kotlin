package org.timemates.rrpc.codegen

import org.timemates.rrpc.common.schema.*

/**
 * Visitor interface for traversing RRpc schema objects.
 *
 * @param D The type of data passed during the traversal.
 * @param R The type of result returned from the visit methods.
 */
public interface RMVisitor<D, R> {
    public fun visitFile(file: RMFile, data: D): R
    public fun visitService(service: RMService, data: D): R
    public fun visitType(type: RMType, data: D): R
    public fun visitField(field: RMField, data: D): R
    public fun visitConstant(constant: RMEnumConstant, data: D): R
    public fun visitRpc(rpc: RMRpc, data: D): R
}