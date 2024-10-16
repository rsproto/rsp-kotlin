package org.timemates.rrpc.server.module

import org.timemates.rrpc.server.ServicesContainer
import org.timemates.rrpc.annotations.ExperimentalInterceptorsApi
import org.timemates.rrpc.instances.InstanceContainer
import org.timemates.rrpc.instances.ProvidableInstance
import org.timemates.rrpc.interceptors.Interceptors
import org.timemates.rrpc.server.OptionsContainer
import org.timemates.rrpc.server.module.descriptors.ProcedureDescriptor
import org.timemates.rrpc.server.module.descriptors.ServiceDescriptor

/**
 * Represents a Proto server that can handle remote method calls.
 *
 * @property services The list of service descriptors for the server.
 * @property interceptors The list of interceptors for the server.
 */
public interface RRpcModule : InstanceContainer, ServicesContainer {
    /**
     * Contains the list of interceptors for the RSocketProtoServer.
     *
     * Interceptors are used to intercept and modify the coroutine context and payload of remote method calls.
     * They are applied before the method is executed and can be used to perform actions such as authentication, logging,
     * or modifying the payload of the incoming request.
     *
     * Interceptors are instances of the [org.timemates.rrpc.interceptors.Interceptor] interface.
     *
     * @see [org.timemates.rrpc.interceptors.Interceptor]
     */
    @ExperimentalInterceptorsApi
    public val interceptors: Interceptors
}

/**
 * Represents a list of known procedure descriptors for the RSocketProtoServer.
 *
 * The `knownProcedures` property is a read-only property that returns a list of ProcedureDescriptor objects.
 * These represent the known procedures for the RSocketProtoServer. Each ProcedureDescriptor represents a remote
 * method call and contains information such as the name of the method, the kind of request, and the serializers
 * for the request and response objects.
 *
 * @return The list of known procedure descriptors.
 *
 * @see ProcedureDescriptor
 * @see RRpcModule
 */
public val RRpcModule.knownProcedures: List<ProcedureDescriptor>
    get() = services.flatMap { it.procedures }

/**
 * Internal implementation of the RRpcModule interface.
 *
 * @property services The list of service descriptors for the server.
 * @property interceptors The list of interceptors for the server.
 * @param instanceContainer The instance container.
 */
@OptIn(ExperimentalInterceptorsApi::class)
internal class RRpcModuleImpl(
    override val services: List<ServiceDescriptor>,
    override val interceptors: Interceptors,
    instanceContainer: InstanceContainer
) : RRpcModule, InstanceContainer {
    private val servicesMap = services.associateBy { it.name }
    private val instanceContainer = instanceContainer + this as ServicesContainer

    override val key: ProvidableInstance.Key<*>
        get() = ServicesContainer.Key

    override fun service(name: String): ServiceDescriptor? = servicesMap[name]

    override fun <T : ProvidableInstance> getInstance(key: ProvidableInstance.Key<T>): T? =
        instanceContainer.getInstance(key)
    override fun plus(instance: ProvidableInstance): InstanceContainer = instanceContainer + instance
    override fun plus(instances: List<ProvidableInstance>): InstanceContainer = instanceContainer + instances
    override fun plus(container: InstanceContainer): InstanceContainer = instanceContainer + container
    override fun asMap(): Map<ProvidableInstance.Key<*>, ProvidableInstance> = instanceContainer.asMap()
}
