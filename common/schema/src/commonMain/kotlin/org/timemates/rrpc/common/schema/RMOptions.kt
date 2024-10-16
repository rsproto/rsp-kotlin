package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl
import kotlin.jvm.JvmInline


@Serializable
@JvmInline
public value class RMOptions(
    public val list: List<RMOption>
) {
    public companion object {
        public val EMPTY: RMOptions = RMOptions(emptyList())

        public val FILE_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.FileOptions")
        public val MESSAGE_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.MessageOptions")
        public val SERVICE_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.ServiceOptions")
        public val FIELD_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.FieldOptions")
        public val ONEOF_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.OneofOptions")
        public val ENUM_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.EnumOptions")
        public val ENUM_VALUE_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.EnumValueOptions")
        public val METHOD_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.MethodOptions")
        public val EXTENSION_RANGE_OPTIONS: RMDeclarationUrl = RMDeclarationUrl("google.protobuf.ExtensionRangeOptions")
    }

    public operator fun get(fieldUrl: RMTypeMemberUrl): RMOption? {
        return list.firstOrNull { it.fieldUrl == fieldUrl }
    }

    public operator fun contains(fieldUrl: RMTypeMemberUrl): Boolean {
        return get(fieldUrl) != null
    }
}

public val RMOptions.isDeprecated: Boolean get() = TODO()