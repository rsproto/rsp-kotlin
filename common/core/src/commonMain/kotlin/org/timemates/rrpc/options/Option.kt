package org.timemates.rrpc.options

/**
 * This interface represents [protobuf options](https://protobuf.dev/programming-guides/proto3/#options).
 *
 * **For now, RRpc supports only service and rpc options.**
 *
 * @property name the name assigned to the given option. It's used mostly for debugging
 * and better understanding of the code. It has no actual connection with resolving
 * options within requests or responses.
 * @property tag unique identifier assigned to the given option.
 */
@Suppress("unused")
public interface Option<T> {
    public val name: String
    public val tag: Int
}

public data class ServiceOption<T>(
    override val name: String,
    override val tag: Int,
) : Option<T> {
    public companion object
}

public data class RPCOption<T>(
    override val name: String,
    override val tag: Int,
) : Option<T> {
    public companion object
}

public data class FileOption<T>(
    override val name: String,
    override val tag: Int,
) : Option<T> {
    public companion object
}

