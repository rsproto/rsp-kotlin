package org.timemates.rrpc.generator.kotlin.options

import com.squareup.kotlinpoet.CodeBlock
import org.timemates.rrpc.common.schema.RMOption
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.RMType
import org.timemates.rrpc.common.schema.RMTypeMemberUrl
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl
import org.timemates.rrpc.generator.kotlin.ext.asClassName
import org.timemates.rrpc.generator.kotlin.ext.protoByteStringToByteArray

public object OptionValueGenerator {
    public fun generate(
        typeUrl: RMDeclarationUrl,
        value: RMOption.Value?,
        resolver: RMResolver,
    ): String {
        @Suppress("UNCHECKED_CAST")
        return when {
            typeUrl.isScalar || typeUrl == RMDeclarationUrl.STRING || typeUrl == RMDeclarationUrl.STRING_VALUE -> generateScalarOrString(
                typeUrl,
                value
            )

            typeUrl.isWrapper -> generateWrapperValue(typeUrl, (value as RMOption.Value.MessageMap).map)
            typeUrl.isMap -> generateMap(typeUrl, (value as RMOption.Value.RawMap).map, resolver)
            else -> generateCustomType(typeUrl, value!!, resolver)
        }
    }

    private fun generateScalarOrString(
        protoType: RMDeclarationUrl,
        value: Any?,
    ): String {
        return when (protoType) {
            RMDeclarationUrl.STRING, RMDeclarationUrl.STRING_VALUE -> "\"${value}\""
            RMDeclarationUrl.BOOL -> "$value"
            // We don't support special types such as fixed32, sfixed32 and sint32,
            // because it makes no sense if not used in serialization.
            // All they are represented as regular Int.
            RMDeclarationUrl.INT32, RMDeclarationUrl.FIXED32, RMDeclarationUrl.SFIXED32, RMDeclarationUrl.SINT32 -> "$value"
            // We don't support special types such as fixed64, sfixed64 and sint64,
            // because it makes no sense if not used in serialization.
            // All they are represented as regular Long.
            RMDeclarationUrl.INT64, RMDeclarationUrl.FIXED64, RMDeclarationUrl.SFIXED64, RMDeclarationUrl.SINT64 -> "${value}L"
            RMDeclarationUrl.FLOAT -> "${value}f"
            RMDeclarationUrl.DOUBLE -> "${value}.toDouble()"
            RMDeclarationUrl.UINT32 -> "${value}.toUInt()"
            RMDeclarationUrl.UINT64 -> "${value}uL"
            RMDeclarationUrl.BYTES -> byteArrayToSourceCode((value as String).protoByteStringToByteArray())
            else -> error("Unsupported type")
        }
    }

    private fun generateWrapperValue(
        protoType: RMDeclarationUrl,
        value: Map<RMTypeMemberUrl, Any>,
    ): String {
        val element = value.values.first().toString()

        return when (protoType) {
            RMDeclarationUrl.STRING_VALUE -> "\"${element}\""
            RMDeclarationUrl.BOOL_VALUE -> element
            RMDeclarationUrl.INT32_VALUE -> element
            RMDeclarationUrl.INT64_VALUE -> "${element}L"
            RMDeclarationUrl.FLOAT_VALUE -> "${element}f"
            RMDeclarationUrl.DOUBLE -> "${element}.toDouble()"
            RMDeclarationUrl.UINT32_VALUE -> "${element}u"
            RMDeclarationUrl.UINT64_VALUE -> "${element}uL"
            else -> TODO("Unsupported for now: $protoType")
        }
    }

    private fun generateMap(
        typeUrl: RMDeclarationUrl,
        value: Map<RMOption.Value.Raw, RMOption.Value.Raw>,
        schema: RMResolver,
    ): String {
        requireNotNull(typeUrl.firstTypeArgument)
        requireNotNull(typeUrl.secondTypeArgument)

        return CodeBlock.builder()
            .add("mapOf(")
            .indent()
            .apply {
                value.entries.forEach { (key, value) ->
                    add(
                        "\n" + generate(typeUrl.firstTypeArgument!!, key, schema) +
                            " to " +
                            generate(typeUrl.secondTypeArgument!!, value, schema) + ","
                    )
                }
            }
            .unindent()
            .add(")")
            .toString()
    }

    private fun generateCustomType(
        typeUrl: RMDeclarationUrl,
        value: RMOption.Value?,
        schema: RMResolver,
    ): String {
        require(typeUrl != RMDeclarationUrl.ANY) { "google.protobuf.Any type is not supported." }
        val type = schema.resolveType(typeUrl)
        val className = typeUrl.asClassName(schema)

        return when (type) {
            is RMType.Message -> {
                return CodeBlock.builder()
                    .add("%T.create {", className)
                    .indent()
                    .apply {
                        @Suppress("UNCHECKED_CAST")
                        (value as Map<RMTypeMemberUrl, RMOption.Value>).entries.forEach { (key, value) ->
                            val field = type.field(key.memberName)!!
                            add("\n${field.name} = ${generate(field.typeUrl, value, schema)}")
                        }
                    }
                    .unindent()
                    .add("\n")
                    .add("}")
                    .build()
                    .toString()
            }

            is RMType.Enum -> CodeBlock.of("%T.%L", className, value).toString()
            is RMType.Enclosing -> "null"
            null -> error("Unable to resolve custom type for: $typeUrl.")
        }
    }

    private fun byteArrayToSourceCode(byteArray: ByteArray): String {
        return byteArray.joinToString(prefix = "byteArrayOf(", postfix = ")") { byte ->
            "0x${byte.toUByte().toString(16).uppercase().padStart(2, '0')}"
        }
    }
}