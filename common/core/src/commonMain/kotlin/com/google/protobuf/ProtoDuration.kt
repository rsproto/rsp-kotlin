@file:OptIn(ExperimentalSerializationApi::class)

package com.google.protobuf

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import org.timemates.rrpc.ProtoType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

/**
 * Constructs a [ProtoDuration] using a builder.
 *
 * @param builder A lambda function to configure the [ProtoDuration.Builder] instance.
 * @return A [ProtoDuration] instance with the configured values.
 */
public fun ProtoDuration(builder: ProtoDuration.Builder.() -> Unit): ProtoDuration =
    ProtoDuration.create(builder)

/**
 * Represents a duration as defined by the ProtoBuf specification.
 * This corresponds to the `google.protobuf.Duration` type in Protocol Buffers.
 *
 * Refer to the [official documentation](https://protobuf.dev/reference/protobuf/google.protobuf/#duration)
 * for more information.
 */
@Serializable
public class ProtoDuration private constructor(
    @ProtoNumber(1)
    public val seconds: Long = 0,
    @ProtoNumber(2)
    public val nanos: Int = 0,
) : ProtoType {
    public companion object : ProtoType.Definition<ProtoDuration> {
        public fun ofSeconds(seconds: Long): ProtoDuration {
            return ProtoDuration(seconds, 0)
        }

        /**
         * Creates a [ProtoDuration] instance using a builder.
         */
        public fun create(builder: Builder.() -> Unit): ProtoDuration {
            return Builder().apply(builder).build()
        }

        override val typeUrl: String
            get() = "type.googleapis.com/google.protobuf.Duration"

        override val Default: ProtoDuration = ProtoDuration()
    }

    public class Builder {
        public var seconds: Long = 0
        public var nanos: Int = 0

        public fun build(): ProtoDuration {
            return ProtoDuration(seconds, nanos)
        }
    }

    override val definition: ProtoType.Definition<*>
        get() = Companion

    override fun toString(): String {
        return "ProtoDuration(seconds=$seconds, nanos=$nanos)"
    }
}

public fun ProtoDuration.toKotlinDuration(): Duration {
    return seconds.seconds + nanos.nanoseconds
}