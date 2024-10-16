@file:OptIn(ExperimentalSerializationApi::class)

package com.google.protobuf

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlinx.serialization.protobuf.ProtoOneOf
import org.timemates.rrpc.ProtoType
import kotlin.jvm.JvmInline

/**
 * Represents a structured data type in ProtoBuf, similar to a JSON object.
 *
 * A [ProtoStruct] instance is a collection of fields, each with a name and a corresponding value.
 * The `fields` property is a map where each key is a field name (string) and the value is of type [ProtoStructValue].
 * This structure allows for dynamic and flexible data representation, where the values can be of various types, including nested structures.
 *
 * @property fields A map of field names to their corresponding values. The map is empty by default.
 */
@Serializable
public class ProtoStruct private constructor(
    public val fields: Map<String, ProtoStructValue> = emptyMap(),
) : ProtoType {
    public companion object : ProtoType.Definition<ProtoStruct> {
        override val typeUrl: String
            get() = "type.googleapis.com/google.protobuf.Struct"
        override val Default: ProtoStruct = ProtoStruct()

        public fun of(vararg fields: Pair<String, ProtoStructValueKind>): ProtoStruct {
            return of(mapOf(*fields))
        }

        public fun of(fields: Map<String, ProtoStructValueKind>): ProtoStruct {
            return ProtoStruct(fields.mapValues { (_, value) -> ProtoStructValue(value) })
        }
    }

    override val definition: ProtoType.Definition<*>
        get() = Companion

    override fun toString(): String {
        return "ProtoStruct(fields=$fields)"
    }
}

@Serializable
@JvmInline
public value class ProtoStructValue(
    @ProtoOneOf public val kind: ProtoStructValueKind,
)

@Serializable
public sealed class ProtoStructValueKind {
    @Serializable
    public class NullValue private constructor(
        @Suppress("unused")
        @ProtoNumber(1)
        private val value: _NullValue = _NullValue.NULL_VALUE,
    ) : ProtoStructValueKind() {
        public companion object {
            public val Default: NullValue = NullValue()
        }
    }

    @Serializable
    public data class NumberValue(@ProtoNumber(2) val value: Double) : ProtoStructValueKind()

    @Serializable
    public data class StringValue(@ProtoNumber(3) val value: String) : ProtoStructValueKind()

    @Serializable
    public data class BooleanValue(@ProtoNumber(4) val value: Boolean) : ProtoStructValueKind()

    @Serializable
    public data class StructValue(@ProtoNumber(5) val value: ProtoStruct) : ProtoStructValueKind()

    @Serializable
    public data class ListValue(@ProtoNumber(6) val value: List<ProtoStructValueKind>) : ProtoStructValueKind()
}

@Suppress("ClassName")
@Serializable
private enum class _NullValue {
    @ProtoNumber(0)
    NULL_VALUE;
}

internal suspend inline infix operator fun Boolean.div(other: Int) {}