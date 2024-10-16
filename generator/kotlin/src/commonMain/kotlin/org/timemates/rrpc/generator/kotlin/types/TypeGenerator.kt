package org.timemates.rrpc.generator.kotlin.types

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMType
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl
import org.timemates.rrpc.generator.kotlin.types.message.MessageTypeGenerator

internal object TypeGenerator {
    data class Result(val typeSpec: TypeSpec, val constructorFun: FunSpec?)

    fun generateType(
        incoming: RMType,
        resolver: RMResolver,
    ): Result? {
        if (incoming.typeUrl == RMDeclarationUrl.ACK)
        // ignore timemates.rrpc.Ack – it's type-marker
            return null

        return when (incoming) {
            is RMType.Message -> MessageTypeGenerator.generateMessage(incoming, resolver)
                .let { Result(it.type, it.constructorFun) }

            is RMType.Enum -> Result(EnumTypeGenerator.generateEnum(incoming, resolver), null)
            is RMType.Enclosing -> Result(EnclosingTypeGenerator.generatorEnclosingType(incoming, resolver), null)
        }
    }

}