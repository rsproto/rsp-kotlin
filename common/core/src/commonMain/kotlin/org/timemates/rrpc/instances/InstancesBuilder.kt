package org.timemates.rrpc.instances

import org.timemates.rrpc.annotations.InternalRRpcAPI

public class InstancesBuilder @InternalRRpcAPI constructor() {
    private val instances: MutableSet<ProvidableInstance> = mutableSetOf()

    public fun register(instance: ProvidableInstance) {
        if (instances.contains(instance))
            instances.remove(instance)
        instances += instance
    }

    @InternalRRpcAPI
    public fun build(): List<ProvidableInstance> {
        return instances.toList()
    }
}

public fun instances(block: InstancesBuilder.() -> Unit): List<ProvidableInstance> {
    @OptIn(InternalRRpcAPI::class)
    return InstancesBuilder().also(block).build()
}