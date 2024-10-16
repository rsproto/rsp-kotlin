package org.timemates.rrpc

import kotlinx.coroutines.flow.Flow
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.jvm.JvmSynthetic

/**
 * Represents a value that can be in one of three states: a single value, a streaming value, or a failure.
 *
 * @param T The type of the value.
 */
public sealed interface DataVariant<out T : Any> {

    /**
     * Checks if the other [DataVariant] is of the same type as this one.
     *
     * @param other The other [DataVariant] to compare with.
     * @return `true` if the other [DataVariant] is of the same type, `false` otherwise.
     */
    public fun isSameType(other: DataVariant<*>): Boolean
}

/**
 * Represents a single value.
 *
 * @param T The type of the value.
 * @property value The single value.
 */
public data class Single<T : Any>(public val value: T) : DataVariant<T> {
    public companion object {
        /**
         * Denotes that single has no value inside. Usually, it's applicable only
         * to the Metadata Push requests.
         */
        public val EMPTY: Single<Unit> = Single(Unit)
    }

    override fun isSameType(other: DataVariant<*>): Boolean {
        return other is Single
    }

    override fun toString(): String {
        return "Single(value=$value)"
    }
}

/**
 * Represents a failure state, usually relevant only for clients as the server can return an error without a body.
 * In addition, it's applicable only if original expected type is [Single]. For [Streaming] use flow's
 * operators to handle failures.
 *
 * @param T The type of the value that was expected.
 * @property exception The exception representing the failure.
 */
public data class Failure(public val exception: Exception) : DataVariant<Nothing> {
    override fun isSameType(other: DataVariant<*>): Boolean {
        return other is Failure
    }
}

/**
 * Represents a streaming value.
 *
 * @param T The type of the values in the stream.
 * @property flow The flow of values.
 */
public class Streaming<T : Any>(
    @get:JvmSynthetic
    public val flow: Flow<T>,
) : DataVariant<T> {
    override fun isSameType(other: DataVariant<*>): Boolean {
        return other is Streaming
    }

    override fun toString(): String {
        return "Streaming(flow=$flow)"
    }
}

/**
 * Checks if the [DataVariant] is a single value.
 *
 * @return `true` if the variant is a [Single], `false` otherwise.
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T : Any> DataVariant<T>.isSingle(): Boolean {
    contract {
        returns(true) implies (this@isSingle is Single<T>)
        returns(false) implies (this@isSingle !is Single<T>)
    }

    return this is Single<T>
}

/**
 * Checks if the [DataVariant] is a streaming value.
 *
 * @return `true` if the variant is a [Streaming], `false` otherwise.
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T : Any> DataVariant<T>.isStreaming(): Boolean {
    contract {
        returns(true) implies (this@isStreaming is Streaming<T>)
        returns(false) implies (this@isStreaming !is Streaming<T>)
    }

    return this is Streaming<T>
}

/**
 * Checks if the [DataVariant] represents a failure.
 *
 * @return `true` if the variant is a [Failure], `false` otherwise.
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T : Any> DataVariant<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Failure)
        returns(false) implies (this@isFailure !is Failure)
    }

    return this is Failure
}

/**
 * Requires that the [DataVariant] is a single value and returns it.
 *
 * @return The single value.
 * @throws IllegalStateException if the variant is not a [Single].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T : Any> DataVariant<T>.requireSingle(): T {
    contract {
        returns() implies (this@requireSingle is Single<T>)
    }
    return if (isSingle()) this.value else error("Expected a single value, but got: $this.")
}

/**
 * Requires that the [DataVariant] is a streaming value and returns it.
 *
 * @return The streaming value.
 * @throws IllegalStateException if the variant is not a [Streaming].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T : Any> DataVariant<T>.requireStreaming(): Flow<T> {
    contract {
        returns() implies (this@requireStreaming is Streaming<T>)
    }
    return if (isStreaming()) this.flow else error("Expected a streaming value, but was not.")
}

/**
 * Requires that the [DataVariant] is a failure and returns it.
 *
 * @return [Failure]
 * @throws IllegalStateException if the variant is not a [Single].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T : Any> DataVariant<T>.requireFailure(): Exception {
    contract {
        returns() implies (this@requireFailure is Failure)
    }
    return if (isFailure()) this.exception else error("Expected a single value, but got: $this.")
}

