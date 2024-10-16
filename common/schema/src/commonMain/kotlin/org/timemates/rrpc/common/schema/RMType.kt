package org.timemates.rrpc.common.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

@Serializable
public sealed interface RMType : RMNode, Documentable {
    public val name: String
    public val typeUrl: RMDeclarationUrl
    public val nestedTypes: List<RMType>
    public val nestedExtends: List<RMExtend>

    @SerialName("ENUM")
    public class Enum(
        override val name: String,
        public val constants: List<RMEnumConstant>,
        override val documentation: String?,
        public val options: RMOptions,
        override val nestedTypes: List<RMType>,
        override val typeUrl: RMDeclarationUrl,
        override val nestedExtends: List<RMExtend>,
    ) : RMType

    @SerialName("MESSAGE")
    public class Message(
        override val name: String,
        override val documentation: String?,
        public val fields: List<RMField>,
        public val oneOfs: List<RMOneOf>,
        public val options: RMOptions,
        override val typeUrl: RMDeclarationUrl,
        override val nestedTypes: List<RMType>,
        override val nestedExtends: List<RMExtend>,
    ) : RMType {
        public val allFields: List<RMField> get() = fields + oneOfs.flatMap { it.fields }

        /**
         * Gets [RMField] in current [RMType.Message] by given [tag].
         * If a field with a tag persists in the oneof field – oneof field is returned,
         *  where the field occurs.
         */
        public fun field(tag: Int): RMField? {
            return fields.firstOrNull { field ->
                field.tag == tag
            }
        }

        /**
         * Gets [RMField] in current [RMType.Message] by given [name].
         * If a field with a tag persists in the oneof field – oneof field is returned,
         *  where the field occurs.
         */
        public fun field(name: String): RMField? {
            return fields.firstOrNull { field ->
                field.name == name
            }
        }

        public override fun equals(other: Any?): Boolean {
            return other is Message && other.typeUrl == typeUrl
        }

        override fun hashCode(): Int {
            return typeUrl.hashCode()
        }
    }

    @SerialName("ENCLOSING_TYPE")
    public class Enclosing(
        override val name: String,
        override val documentation: String?,
        override val typeUrl: RMDeclarationUrl,
        override val nestedTypes: List<RMType>,
        override val nestedExtends: List<RMExtend>,
    ) : RMType
}