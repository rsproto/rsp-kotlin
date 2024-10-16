@file:OptIn(ExperimentalSerializationApi::class)

package com.google.protobuf

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import org.timemates.rrpc.ProtoType

/**
 * Constructs a [ProtoTimestamp] using a builder.
 *
 * This function allows you to create a [ProtoTimestamp] instance by applying configuration
 * to a [Builder] and then building it. This is a convenient way to initialize a [ProtoTimestamp]
 * with specific values for `seconds` and `nanos`.
 *
 * @param builder A lambda function to configure the [Builder] instance.
 * @return A [ProtoTimestamp] instance with the configured values.
 */
public fun ProtoTimestamp(builder: ProtoTimestamp.Builder.() -> Unit): ProtoTimestamp =
    ProtoTimestamp.create(builder)

/**
 * Represents a timestamp as defined by the ProtoBuf specification.
 * This corresponds to the [google.protobuf.Timestamp] type in Protocol Buffers.
 *
 * A [ProtoTimestamp] holds the number of seconds and nanoseconds since the Unix epoch.
 *
 * Refer to the [official documentation](https://protobuf.dev/reference/protobuf/google.protobuf/#timestamp)
 * for more information.
 *
 * @property seconds The number of seconds since the Unix epoch (1970-01-01T00:00:00Z).
 * @property nanos The number of nanoseconds within the second.
 */
@Serializable
public class ProtoTimestamp private constructor(
    @ProtoNumber(1)
    public val seconds: Long = 0,
    @ProtoNumber(2)
    public val nanos: Int = 0,
) : ProtoType {
    public companion object : ProtoType.Definition<ProtoTimestamp> {
        /**
         * Creates a [ProtoTimestamp] representing a specific number of seconds since the Unix epoch.
         *
         * This function initializes the `seconds` property with the given value and sets `nanos` to 0.
         *
         * @param seconds The number of seconds since the Unix epoch.
         * @return A [ProtoTimestamp] instance with the specified number of seconds and zero nanoseconds.
         */
        public fun ofSeconds(seconds: Long): ProtoTimestamp {
            return ProtoTimestamp(seconds, 0)
        }

        /**
         * Creates a [ProtoTimestamp] instance using a builder.
         *
         * This function allows you to configure a [ProtoTimestamp] instance using a builder pattern.
         * It applies the provided lambda function to a [Builder] and then builds the [ProtoTimestamp].
         *
         * @param builder A lambda function to configure the [Builder] instance.
         * @return A [ProtoTimestamp] instance with the configured values.
         */
        public fun create(builder: Builder.() -> Unit): ProtoTimestamp {
            return Builder().apply(builder).build()
        }

        override val typeUrl: String
            get() = "type.googleapis.com/google.protobuf.Timestamp"

        /**
         * The default value for [ProtoTimestamp], representing the Unix epoch (January 1, 1970).
         *
         * This default value also signifies the starting point of Unix time.
         */
        override val Default: ProtoTimestamp = ProtoTimestamp()
    }

    public class Builder {
        public var seconds: Long = 0
        public var nanos: Int = 0

        public fun build(): ProtoTimestamp {
            return ProtoTimestamp(seconds, nanos)
        }
    }

    override val definition: ProtoType.Definition<*>
        get() = Companion

    override fun toString(): String {
        return "Timestamp(second=$seconds, nanos=$nanos)"
    }
}
