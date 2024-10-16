@file:Suppress("MemberVisibilityCanBePrivate")

package org.timemates.rrpc.interceptors

import org.timemates.rrpc.DataVariant
import org.timemates.rrpc.annotations.InternalRRpcAPI
import org.timemates.rrpc.instances.InstanceContainer
import org.timemates.rrpc.instances.ProvidableInstance
import org.timemates.rrpc.metadata.ExtraMetadata
import org.timemates.rrpc.metadata.RRpcMetadata
import org.timemates.rrpc.options.OptionsWithValue
import kotlin.jvm.JvmSynthetic

/**
 * Represents the context for interceptors to access and modify request-related data,
 * metadata, and options during request processing.
 *
 * @property data Request/response data linked to the request, which can be either
 * single-value or streaming data.
 * @property metadata Request metadata attached to the request.
 * @property options Options linked to the request.
 * @property instances Container for instances available within the request pipeline.
 */
@OptIn(InternalRRpcAPI::class)
public class InterceptorContext<TMetadata : RRpcMetadata> @InternalRRpcAPI constructor(
    public val data: DataVariant<*>,
    public val metadata: TMetadata,
    public val options: OptionsWithValue,
    public val instances: InstanceContainer,
) {

    /**
     * Provides a builder to modify the context immutably, preserving the current state of the context.
     */
    public fun builder(): Builder<TMetadata> {
        return Builder(
            instances, metadata, data, options,
        )
    }


    /**
     * Copies current [InterceptorContext] with the ability to change some of its fields.
     *
     * It's better to use [builder] as it ensures that no data was lost during the chain of interceptors.
     * Make sure that [instances] are properly handled and nothing lost.
     *
     * Marked as [JvmSynthetic], because named arguments are not supported from Java side.
     */
    @JvmSynthetic
    public fun copy(
        data: DataVariant<*> = this.data,
        extra: ExtraMetadata = this.metadata.extra,
        options: OptionsWithValue = this.options,
        instances: InstanceContainer = this.instances,
    ): InterceptorContext<TMetadata> {
        @Suppress("UNCHECKED_CAST")
        return InterceptorContext(
            data, metadata.extra(extra) as TMetadata, options, instances,
        )
    }

    /**
     * Builder class for modifying an immutable [InterceptorContext].
     */
    public class Builder<TMetadata : RRpcMetadata>(
        private var instances: InstanceContainer,
        private var metadata: TMetadata,
        private var data: DataVariant<*>,
        private var options: OptionsWithValue,
    ) {
        /**
         * Adds local instances to the given request pipeline. These instances do not affect
         * the global [InstanceContainer].
         *
         * @param instance The instance to add to the local request pipeline.
         * @return This builder instance for chaining method calls.
         */
        public fun addLocalInstance(instance: ProvidableInstance): Builder<TMetadata> {
            instances += instance
            return this
        }

        /**
         * Adds multiple local instances to the given request pipeline. These instances do not affect
         * the global [InstanceContainer].
         *
         * @param list The list of instances to add to the local request pipeline.
         * @return This builder instance for chaining method calls.
         */
        public fun addLocalInstances(list: List<ProvidableInstance>): Builder<TMetadata> {
            instances += list
            return this
        }

        /**
         * Adds extra metadata to the builder, modifying the current metadata instance.
         *
         * @param extra The extra metadata to add.
         * @return This builder instance for chaining method calls.
         */
        public fun extras(extra: ExtraMetadata): Builder<TMetadata> {
            @Suppress("UNCHECKED_CAST")
            metadata = metadata.extra(extra) as TMetadata
            return this
        }

        /**
         * Sets the request/response data in the builder. Ensures that the incoming data type matches
         * the current data type to prevent confusion and potential type errors.
         *
         * @param value The new value for request/response data.
         * @return This builder instance for chaining method calls.
         * @throws IllegalArgumentException if the data type of [value] does not match the current data type.
         */
        public fun data(value: DataVariant<*>): Builder<TMetadata> {
            data = value
            return this
        }

        /**
         * Sets the options in the builder.
         *
         * @param options The new options for the request.
         */
        public fun options(options: OptionsWithValue): Builder<TMetadata> {
            this.options = options
            return this
        }

        /**
         * Constructs an immutable [InterceptorContext] instance based on the current builder state.
         *
         * @return An immutable [InterceptorContext] instance.
         */
        public fun build(): InterceptorContext<TMetadata> {
            return InterceptorContext(
                data, metadata, options, instances
            )
        }
    }
}

/**
 * Adds multiple local instances to the [InterceptorContext.Builder] using vararg parameter syntax.
 *
 * @param instances The vararg list of instances to add to the builder.
 * @return The [InterceptorContext.Builder] instance with added local instances.
 */
public fun <T : RRpcMetadata> InterceptorContext.Builder<T>.addLocalInstances(
    vararg instances: ProvidableInstance
): InterceptorContext.Builder<T> {
    return addLocalInstances(instances.toList())
}

/**
 * Modifies the current [InterceptorContext] immutably using a builder pattern.
 *
 * @param block The configuration block to modify the [InterceptorContext.Builder].
 * @return The modified [InterceptorContext] instance.
 */
public inline fun <T : RRpcMetadata> InterceptorContext<T>.modify(
    block: InterceptorContext.Builder<T>.() -> Unit
): InterceptorContext<T> {
    return builder().apply(block).build()
}