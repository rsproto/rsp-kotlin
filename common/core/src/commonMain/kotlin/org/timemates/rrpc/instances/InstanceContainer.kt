package org.timemates.rrpc.instances

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import org.timemates.rrpc.annotations.InternalRRpcAPI
import kotlin.coroutines.CoroutineContext

/**
 * Creates an [InstanceContainer] with the provided map of instances.
 *
 * @param instances A map where the key is a [ProvidableInstance.Key] and the value is a [ProvidableInstance].
 * @return A new [InstanceContainer] containing the provided instances.
 */
public fun InstanceContainer(instances: Map<ProvidableInstance.Key<*>, ProvidableInstance>): InstanceContainer =
    InstanceContainerImpl(instances)

/**
 * Container for holding and retrieving instances of [ProvidableInstance].
 *
 * This interface allows retrieving instances using a key and adding new instances to the container.
 */
public interface InstanceContainer {
    /**
     * Retrieves an instance of type [T] associated with the given [key].
     *
     * @param key The key associated with the instance to be retrieved.
     * @return The instance associated with the provided key.
     */
    public fun <T : ProvidableInstance> getInstance(key: ProvidableInstance.Key<T>): T?

    /**
     * Creates a new [InstanceContainer] that contains all instances from this container plus the provided [instance].
     *
     * @param instance The instance to be added to the container.
     * @return A new [InstanceContainer] containing the existing instances plus the provided instance.
     */
    public operator fun plus(instance: ProvidableInstance): InstanceContainer

    /**
     * Creates a new [InstanceContainer] that contains all instances from this container plus the provided [instances].
     *
     * @param instances The list of instances to be added to the container.
     * @return A new [InstanceContainer] containing the existing instances plus the provided instances.
     */
    public operator fun plus(instances: List<ProvidableInstance>): InstanceContainer

    /**
     * Creates a new [InstanceContainer] that contains all instances from this container plus the provided [instances].
     *
     * @param container another container with instances to be appended.
     * @return A new [InstanceContainer] containing the existing instances plus the provided instances.
     */
    public operator fun plus(container: InstanceContainer): InstanceContainer

    /**
     * Retrieves instances as map.
     */
    public fun asMap(): Map<ProvidableInstance.Key<*>, ProvidableInstance>
}

internal class InstanceContainerImpl(
    private val instances: Map<ProvidableInstance.Key<*>, ProvidableInstance>
) : InstanceContainer {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ProvidableInstance> getInstance(key: ProvidableInstance.Key<T>): T? =
        instances[key] as? T


    override operator fun plus(instance: ProvidableInstance): InstanceContainer {
        return InstanceContainerImpl(instances + (instance.key to instance))
    }


    override operator fun plus(instances: List<ProvidableInstance>): InstanceContainer {
        return InstanceContainerImpl(
            this.instances.plus(instances.associateBy(ProvidableInstance::key))
        )
    }

    override fun plus(container: InstanceContainer): InstanceContainer {
        return InstanceContainer(instances + container.asMap())
    }

    override fun asMap(): Map<ProvidableInstance.Key<*>, ProvidableInstance> {
        return instances
    }
}

@OptIn(ExperimentalSerializationApi::class)
public val InstanceContainer.protobuf: ProtoBuf?
    get() = getInstance(ProtobufInstance)?.protobuf

@InternalRRpcAPI
public class CoroutineContextInstanceContainer(
    public val container: InstanceContainer,
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key

    public companion object Key : CoroutineContext.Key<CoroutineContextInstanceContainer>
}