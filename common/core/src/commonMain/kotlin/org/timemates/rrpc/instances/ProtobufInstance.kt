package org.timemates.rrpc.instances

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoBufBuilder
import kotlin.jvm.JvmInline

@OptIn(ExperimentalSerializationApi::class)
@JvmInline
public value class ProtobufInstance(public val protobuf: ProtoBuf) : ProvidableInstance {

    public companion object Key : ProvidableInstance.Key<ProtobufInstance>

    override val key: ProvidableInstance.Key<ProtobufInstance>
        get() = Key
}

/**
 * Register the protobuf instance for the [InstanceContainer].
 *
 * @param protobuf The ProtoBuf instance to register.
 */
@OptIn(ExperimentalSerializationApi::class)
public fun InstancesBuilder.protobuf(
    protobuf: ProtoBuf = ProtoBuf
) {
    register(ProtobufInstance(protobuf))
}

/**
 * Registers a Protobuf instance for the [InstanceContainer].
 * This allows the client/server to handle remote method calls using Protobuf encoding.
 *
 * @param builder A lambda function that configures the Protobuf instance using the [ProtoBufBuilder].
 */
@OptIn(ExperimentalSerializationApi::class)
public fun InstancesBuilder.protobuf(
    builder: ProtoBufBuilder.() -> Unit
) {
    register(ProtobufInstance(ProtoBuf(builderAction = builder)))
}