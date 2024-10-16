package org.timemates.rrpc.server.module

import kotlinx.serialization.ExperimentalSerializationApi
import org.timemates.rrpc.annotations.ExperimentalInterceptorsApi
import org.timemates.rrpc.annotations.InternalRRpcAPI
import org.timemates.rrpc.instances.InstanceContainer
import org.timemates.rrpc.instances.InstancesBuilder
import org.timemates.rrpc.instances.protobuf
import org.timemates.rrpc.interceptors.Interceptors
import org.timemates.rrpc.interceptors.RequestInterceptor
import org.timemates.rrpc.interceptors.ResponseInterceptor

/**
 * Creates an [RRpcModule] using the provided [block] to configure its builder.
 *
 * @param block A lambda with receiver of type [RRpcModuleBuilder] used to configure the module.
 * @return A configured [RRpcModule].
 */
@OptIn(ExperimentalSerializationApi::class)
public fun RRpcModule(block: RRpcModuleBuilder.() -> Unit): RRpcModule {
    return RRpcModuleBuilder().apply {
        instances {
            protobuf {
                encodeDefaults = true
            }
        }
    }.apply(block).build()
}

/**
 * Builder class for constructing an [RRpcModule].
 */
@OptIn(InternalRRpcAPI::class)
public class RRpcModuleBuilder internal constructor() {
    private val instances: InstancesBuilder = InstancesBuilder()
    private val services: ServicesBuilder = ServicesBuilder()
    @OptIn(ExperimentalInterceptorsApi::class)
    private val interceptors: InterceptorsBuilder = InterceptorsBuilder()

    public fun services(builder: ServicesBuilder.() -> Unit) {
        services.apply(builder).build()
    }

    @ExperimentalInterceptorsApi
    public fun interceptors(builder: InterceptorsBuilder.() -> Unit) {
        interceptors.builder()
    }

    /**
     * Configures instances for the module using the provided [block].
     *
     * @param block A lambda with receiver of type [InstancesBuilder] used to configure instances.
     */
    public fun instances(block: InstancesBuilder.() -> Unit) {
        instances.apply(block).build()
    }

    /**
     * Builds the [RRpcModule] with the configured services, interceptors, and instances.
     *
     * @return A configured [RRpcModule].
     */
    @OptIn(ExperimentalInterceptorsApi::class)
    public fun build(): RRpcModule {
        return RRpcModuleImpl(
            services = services.build().map { it.descriptor },
            interceptors = interceptors.build(),
            instanceContainer = InstanceContainer(instances.build().associateBy { it.key }),
        )
    }

    public class ServicesBuilder {
        private val services: MutableList<RRpcService> = mutableListOf()

        public fun register(service: RRpcService) {
            services += service
        }

        public fun build(): List<RRpcService> = services.toList()
    }

    @ExperimentalInterceptorsApi
    public class InterceptorsBuilder {
        private val requestInterceptors: MutableList<RequestInterceptor> = mutableListOf()
        private val responseInterceptors: MutableList<ResponseInterceptor> = mutableListOf()

        /**
         * Adds a request interceptor to the module.
         *
         * @param interceptor The [RequestInterceptor] to add.
         */
        @ExperimentalInterceptorsApi
        public fun request(interceptor: RequestInterceptor) {
            requestInterceptors += interceptor
        }

        /**
         * Adds a response interceptor to the module.
         *
         * @param interceptor The [ResponseInterceptor] to add.
         */
        @ExperimentalInterceptorsApi
        public fun response(interceptor: ResponseInterceptor) {
            responseInterceptors += interceptor
        }

        @ExperimentalInterceptorsApi
        public fun build(): Interceptors = Interceptors(requestInterceptors.toList(), responseInterceptors.toList())
    }
}

/**
 * Adds multiple request interceptors to the module.
 *
 * @param interceptors Vararg of [RequestInterceptor] to add.
 */
@ExperimentalInterceptorsApi
public fun RRpcModuleBuilder.InterceptorsBuilder.request(vararg interceptors: RequestInterceptor) {
    interceptors.forEach { request(it) }
}

/**
 * Adds multiple response interceptors to the module.
 *
 * @param interceptors Vararg of [ResponseInterceptor] to add.
 */
@ExperimentalInterceptorsApi
public fun RRpcModuleBuilder.InterceptorsBuilder.response(vararg interceptors: ResponseInterceptor) {
    interceptors.forEach { response(interceptor = it) }
}
