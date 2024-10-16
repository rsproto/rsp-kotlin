package org.timemates.rrpc

/**
 * Interface representing a type that can be serialized and deserialized using ProtoBuf.
 *
 * Implementing this interface allows a class to integrate with the ProtoBuf serialization framework.
 * It provides access to the type's definition, which includes metadata and default instances.
 */
public interface ProtoType {

    /**
     * Provides the definition of the ProtoBuf type.
     *
     * The definition includes metadata about the type, such as its unique identifier and default instance.
     *
     * @return The definition of the ProtoBuf type.
     */
    public val definition: Definition<*>

    /**
     * Interface representing the definition of a ProtoBuf type.
     *
     * This interface contains metadata and a default instance for the ProtoBuf type. It helps in identifying
     * the type and provides a way to access a default instance, which is useful for serialization and deserialization.
     *
     * @param T The type of the ProtoBuf type being defined. Must extend [ProtoType].
     */
    public interface Definition<T : ProtoType> {

        /**
         * The unique identifier for the ProtoBuf type.
         *
         * This URL is used to identify the type within the ProtoBuf framework, particularly when working
         * with `Any` types that encapsulate different messages.
         *
         * @return The type URL as a string.
         */
        public val typeUrl: String

        /**
         * The default instance of the ProtoBuf type.
         *
         * This instance represents the default or zero-value state of the type. It is often used as a starting point
         * or as a placeholder when no specific value is provided.
         *
         * @return A default instance of the ProtoBuf type.
         */
        public val Default: T
    }
}
