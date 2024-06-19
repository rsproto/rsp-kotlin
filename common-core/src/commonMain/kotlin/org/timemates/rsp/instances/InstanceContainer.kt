package org.timemates.rsp.instances

import org.timemates.rsp.annotations.ExperimentalInstancesApi
import kotlin.coroutines.CoroutineContext

public interface InstanceContainer {
    /**
     * Represents a map of instances that can be provided based on a specified key.
     *
     * @see ProvidableInstance
     * @see ProvidableInstance.Key
     *
     * @since 1.0
     */
    @ExperimentalInstancesApi
    public val instances: Map<ProvidableInstance.Key<*>, ProvidableInstance>
}

@ExperimentalInstancesApi
@Suppress("UNCHECKED_CAST")
public fun <T : ProvidableInstance> InstanceContainer.getInstance(key: ProvidableInstance.Key<T>): T? =
    instances[key] as? T


public class CoroutineContextInstanceContainer(
    public val container: InstanceContainer,
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key

    public companion object Key : CoroutineContext.Key<CoroutineContextInstanceContainer>
}