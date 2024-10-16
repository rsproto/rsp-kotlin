package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@Serializable
public class RMOption(
    public val name: String,
    public val fieldUrl: RMTypeMemberUrl,
    /**
     * Value is present only if RPSOption is present in position where a specific value is
     * possible, it can also be default value if supported.
     */
    public val value: Value?,
) {
    @Serializable
    public sealed interface Value {
        @JvmInline
        public value class Raw(public val string: String) : Value
        @JvmInline
        public value class RawMap(public val map: Map<Raw, Raw>) : Value
        @JvmInline
        public value class MessageMap(public val map: Map<RMTypeMemberUrl, Value>) : Value
    }
}