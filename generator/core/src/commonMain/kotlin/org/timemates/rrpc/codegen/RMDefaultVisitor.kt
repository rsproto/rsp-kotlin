package org.timemates.rrpc.codegen

import org.timemates.rrpc.common.schema.*

public abstract class RMDefaultVisitor<D, R> : RMEmptyVisitor<D, R>() {
    override fun visitFile(file: RMFile, data: D): R {
        file.services.forEach { service ->
            visitService(service, data)
        }
        file.types.forEach { type ->
            visitType(type, data)
        }
        return super.visitFile(file, data)
    }

    override fun visitService(service: RMService, data: D): R {
        service.rpcs.forEach { rpc -> visitRpc(rpc, data) }
        return super.visitService(service, data)
    }

    override fun visitType(type: RMType, data: D): R {
        when (type) {
            is RMType.Enum -> type.constants.forEach { constant -> visitConstant(constant, data) }
            is RMType.Message -> type.fields.forEach { field -> visitField(field, data) }
            is RMType.Enclosing -> type.nestedTypes.forEach { type -> visitType(type, data) }
        }
        return super.visitType(type, data)
    }
}