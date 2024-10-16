package org.timemates.rrpc.codegen.typemodel

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName

@Suppress("FunctionName")
internal object Annotations {
    fun ProtoNumber(number: Int): AnnotationSpec =
        AnnotationSpec.builder(
            ClassName("kotlinx.serialization.protobuf", "ProtoNumber")
        ).addMember(number.toString()).build()

    val Serializable = AnnotationSpec.builder(ClassName("kotlinx.serialization", "Serializable")).build()

    val Deprecated = AnnotationSpec.builder(ClassName("kotlin", "Deprecated")).addMember(
        "\"Deprecated in .proto definition.\""
    ).build()

    fun OptIn(className: ClassName): AnnotationSpec = AnnotationSpec.builder(
        ClassName("kotlin", "OptIn")
    ).addMember("%T::class", className).build()

    fun Suppress(vararg warnings: String): AnnotationSpec = AnnotationSpec.builder(Suppress::class)
        .apply {
            warnings.forEach { warning ->
                addMember("\"$warning\"")
            }
        }
        .build()

    val ProtoPacked = AnnotationSpec.builder(ClassName("kotlinx.serialization.protobuf", "ProtoPacked")).build()
    val ProtoOneOf = AnnotationSpec.builder(ClassName("kotlinx.serialization.protobuf", "ProtoOneOf")).build()

    val InternalRRpcAPI = ClassName("org.timemates.rrpc.annotations", "InternalRRpcAPI")
}