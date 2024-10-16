package org.timemates.rrpc.generator.kotlin.types

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMType

internal object EnclosingTypeGenerator {

    fun generatorEnclosingType(incoming: RMType.Enclosing, schema: RMResolver): TypeSpec {
        val nested = incoming.nestedTypes.mapNotNull { TypeGenerator.generateType(it, schema) }

        return TypeSpec.classBuilder(incoming.name)
            .primaryConstructor(
                FunSpec.constructorBuilder().addModifiers(KModifier.PRIVATE).build()
            )
            .addType(
                TypeSpec.companionObjectBuilder()
                    .addFunctions(nested.mapNotNull(TypeGenerator.Result::constructorFun))
                    .build()
            )
            .addTypes(nested.map(TypeGenerator.Result::typeSpec))
            .build()
    }

}