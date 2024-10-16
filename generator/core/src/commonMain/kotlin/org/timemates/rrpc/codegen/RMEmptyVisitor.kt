package org.timemates.rrpc.codegen

import org.timemates.rrpc.common.schema.*

public abstract class RMEmptyVisitor<D, R> : RMVisitor<D, R> {
    public abstract fun defaultHandler(node: RMNode, data: D): R

    override fun visitFile(file: RMFile, data: D): R {
        return defaultHandler(file, data)
    }

    override fun visitService(service: RMService, data: D): R {
        return defaultHandler(service, data)
    }

    override fun visitType(type: RMType, data: D): R {
        return defaultHandler(type, data)
    }

    override fun visitRpc(rpc: RMRpc, data: D): R {
        return defaultHandler(rpc, data)
    }

    override fun visitField(field: RMField, data: D): R {
        return defaultHandler(field, data)
    }

    override fun visitConstant(constant: RMEnumConstant, data: D): R {
        return defaultHandler(constant, data)
    }
}