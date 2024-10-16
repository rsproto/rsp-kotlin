package com.google.protobuf

import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoNumber
import org.timemates.rrpc.ProtoType


/**
 * Represents a serialized protobuf message of any type.
 *
 * `ProtoAny` is a Kotlin equivalent of `google.protobuf.Any`, which can encapsulate any
 * protobuf message and provide type information for safe unpacking.
 *
 * #### Example
 * Here's a small example of how to use ProtoAny type:
 * ```kotlin
 * // packing
 * val any: ProtoAny = ProtoAny.pack(ProtoTimestamp.ofSeconds(...))
 * // unpacking
 * if (any.typeOf(ProtoTimestamp))
 *  println(any.unpack<ProtoTimestamp>().seconds)
 * else println("Other type: $typeName")
 * ```
 *
 * @property typeName The fully qualified type name of the serialized message.
 * @property value The serialized protobuf message as a byte array.
 */
@Serializable
public class ProtoAny private constructor(
    @ProtoNumber(1)
    public val typeName: String = "",
    @ProtoNumber(2)
    public val value: ByteArray = byteArrayOf(),
) : ProtoType {

    /**
     * Companion object for `ProtoAny` that provides helper functions for packing and unpacking messages.
     */
    public companion object : ProtoType.Definition<ProtoAny> {

        /**
         * Packs a given `ProtoType` instance into a `ProtoAny`.
         *
         * This function serializes the provided message and wraps it in a `ProtoAny` container.
         * It is useful when you want to send or store an arbitrary protobuf message using the `Any` type.
         *
         * @param T The type of the `ProtoType` to be packed.
         * @param value The message to be packed.
         * @param serializer The serializer for the type `T`.
         * @param protoBuf The `ProtoBuf` instance used for serialization, defaults to `ProtoBuf`.
         * @return A `ProtoAny` instance containing the serialized message.
         */
        @OptIn(ExperimentalSerializationApi::class)
        public fun <T : ProtoType> pack(
            value: T,
            serializer: SerializationStrategy<T>,
            protoBuf: ProtoBuf = ProtoBuf,
        ): ProtoAny {
            val bytes = protoBuf.encodeToByteArray(serializer, value)
            return ProtoAny(value.definition.typeUrl, bytes)
        }

        /**
         * Returns the type URL for the `ProtoAny` type.
         */
        override val typeUrl: String
            get() = "type.googleapis.com/google.protobuf.Any"

        /**
         * The default value for `ProtoAny`, which effectively means that there's no packed value.
         */
        override val Default: ProtoAny = ProtoAny()
    }

    /**
     * Provides the definition of the `ProtoAny` type.
     */
    override val definition: ProtoType.Definition<*>
        get() = Companion

    /**
     * Checks whether the `ProtoAny` contains a message of the given type.
     *
     * @param definition The definition of the type to check against.
     * @return `true` if the contained message is of the given type, `false` otherwise.
     */
    public fun typeOf(definition: ProtoType.Definition<*>): Boolean {
        return typeName == definition.typeUrl
    }

    /**
     * Unpacks the `ProtoAny` instance into the specified `ProtoType`.
     *
     * This function deserializes the `ProtoAny`'s `value` field back into its original message type.
     * It is useful when you want to retrieve the original message that was packed into an `Any` container.
     *
     * @param T The type of the `ProtoType` to be unpacked.
     * @param deserializer The deserializer for the type `T`.
     * @param protoBuf The `ProtoBuf` instance used for deserialization, defaults to `ProtoBuf`.
     * @return The deserialized `ProtoType` instance.
     * @throws SerializationException If the deserialization fails.
     */
    public fun <T : ProtoType> unpack(
        deserializer: DeserializationStrategy<T>,
        protoBuf: ProtoBuf = ProtoBuf,
    ): T {
        return protoBuf.decodeFromByteArray(deserializer, value)
    }

    override fun toString(): String {
        return "ProtoAny(typeName='$typeName', value=$value)"
    }
}

/**
 * Packs a given `ProtoType` instance into a `ProtoAny`.
 *
 * This function serializes the provided message and wraps it in a `ProtoAny` container.
 * It is useful when you want to send or store an arbitrary protobuf message using the `Any` type.
 *
 * @param protoBuf The `ProtoBuf` instance used for serialization, defaults to `ProtoBuf`.
 * @return A `ProtoAny` instance containing the serialized message.
 */
@OptIn(ExperimentalSerializationApi::class)
public inline fun <reified T : ProtoType> ProtoAny.Companion.pack(value: T, protoBuf: ProtoBuf = ProtoBuf): ProtoAny {
    return pack(value, serializer<T>(), protoBuf)
}

/**
 * Unpacks a `ProtoAny` instance into the specified `ProtoType`.
 *
 * This function deserializes the `ProtoAny`'s `value` field back into its original message type.
 * It is useful when you want to retrieve the original message that was packed into an `Any` container.
 *
 * @param protoBuf The `ProtoBuf` instance used for deserialization, defaults to `ProtoBuf`.
 * @return The deserialized `ProtoType` instance.
 * @throws SerializationException If the deserialization fails.
 */
@OptIn(ExperimentalSerializationApi::class)
public inline fun <reified T : ProtoType> ProtoAny.unpack(protoBuf: ProtoBuf = ProtoBuf): T {
    return this.unpack(serializer(), protoBuf)
}

/**
 * Tries to unpack the value and if it's failed, [defaultValue] is called
 * as a fallback.
 */
public inline fun <reified T : ProtoType> ProtoAny.unpackOr(
    protoBuf: ProtoBuf = ProtoBuf,
    defaultValue: () -> T,
): T {
    return try {
        unpack(protoBuf)
    } catch (_: Exception) {
        defaultValue()
    }
}