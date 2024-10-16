package org.timemates.rrpc.common.schema.value

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
public value class RMDeclarationUrl(public val value: String) {
    public val isScalar: Boolean get() = this in SCALAR_TYPES
    public val isWrapper: Boolean get() = this in WRAPPER_TYPES
    public val isGoogleBuiltin: Boolean get() = this in GOOGLE_BUILTIN_TYPES

    public val isMap: Boolean get() = value.startsWith("map<")
    public val firstTypeArgument: RMDeclarationUrl? get() =
        if (isMap)
            RMDeclarationUrl(value.substringAfter('<').substringBefore(','))
        else null

    public val secondTypeArgument: RMDeclarationUrl? get() =
        if (isMap)
            RMDeclarationUrl(value.substringAfter(',').substringBefore('>').trim())
        else null

    public val simpleName: String get() = value.substringAfterLast('.')
    public val enclosingTypeOrPackage: String? get() {
        val string = value.substringAfterLast('/')
        val dot = string.lastIndexOf('.')
        return if (dot == -1) null else string.substring(0, dot)
    }

    public companion object {
        public val UNKNOWN: RMDeclarationUrl = RMDeclarationUrl("unknown")

        public val INT32: RMDeclarationUrl = RMDeclarationUrl("int32")
        public val INT64: RMDeclarationUrl = RMDeclarationUrl("int32")

        public val STRING: RMDeclarationUrl = RMDeclarationUrl("string")

        public val SINT32: RMDeclarationUrl = RMDeclarationUrl("int32")
        public val SINT64: RMDeclarationUrl = RMDeclarationUrl("int32")

        public val BOOL: RMDeclarationUrl = RMDeclarationUrl("bool")

        public val UINT32: RMDeclarationUrl = RMDeclarationUrl("uint32")
        public val UINT64: RMDeclarationUrl = RMDeclarationUrl("uint64")

        public val SFIXED32: RMDeclarationUrl = RMDeclarationUrl("sfixed32")
        public val SFIXED64: RMDeclarationUrl = RMDeclarationUrl("sfixed64")

        public val FIXED32: RMDeclarationUrl = RMDeclarationUrl("fixed32")
        public val FIXED64: RMDeclarationUrl = RMDeclarationUrl("fixed64")

        public val FLOAT: RMDeclarationUrl = RMDeclarationUrl("float")
        public val DOUBLE: RMDeclarationUrl = RMDeclarationUrl("double")

        public val BYTES: RMDeclarationUrl = RMDeclarationUrl("bytes")

        public fun ofMap(first: RMDeclarationUrl, second: RMDeclarationUrl): RMDeclarationUrl {
            return RMDeclarationUrl("map<${first.value}, ${second.value}>")
        }

        public val SCALAR_TYPES: List<RMDeclarationUrl> = listOf(
            INT32,
            INT64,
            STRING,
            SINT32,
            SINT64,
            BOOL,
            UINT32,
            UINT64,
            STRING,
            FIXED32,
            FIXED64,
            FLOAT,
            DOUBLE,
            BYTES,
        )

        public val ANY: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Any")
        public val TIMESTAMP: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Timestamp")
        public val DURATION: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Duration")
        public val EMPTY: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Empty")
        public val STRUCT: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Struct")
        public val STRUCT_MAP: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.StructMap")
        public val STRUCT_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Value")
        public val STRUCT_NULL: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.NullValue")
        public val STRUCT_LIST: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.ListValue")

        public val GOOGLE_BUILTIN_TYPES: List<RMDeclarationUrl> = listOf(
            ANY,
            TIMESTAMP,
            DURATION,
            EMPTY,
            STRUCT,
            STRUCT_MAP,
            STRUCT_VALUE,
            STRUCT_NULL,
            STRUCT_LIST,
        )

        public val DOUBLE_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.DoubleValue")
        public val FLOAT_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.FloatValue")
        public val INT32_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Int32Value")
        public val INT64_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.Int64Value")
        public val UINT32_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.UInt32Value")
        public val UINT64_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.UINT64Value")
        public val STRING_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.StringValue")
        public val BYTES_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.BytesValue")
        public val BOOL_VALUE: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/google.protobuf.BoolValue")

        public val ACK: RMDeclarationUrl = RMDeclarationUrl("type.googleapis.com/timemates.rrpc.Ack")

        public val WRAPPER_TYPES: List<RMDeclarationUrl> = listOf(
            DOUBLE_VALUE,
            FLOAT_VALUE,
            INT32_VALUE,
            INT64_VALUE,
            UINT32_VALUE,
            UINT64_VALUE,
            STRING_VALUE,
            BYTES_VALUE,
        )
    }
}