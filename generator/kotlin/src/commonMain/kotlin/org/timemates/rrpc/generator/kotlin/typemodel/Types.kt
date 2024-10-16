package org.timemates.rrpc.codegen.typemodel

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName

internal object Types {
    fun Flow(ofTypeName: TypeName): ParameterizedTypeName =
        ClassName("kotlinx.coroutines.flow", "Flow")
            .parameterizedBy(ofTypeName)

    val ServiceDescriptor = ClassName("org.timemates.rrpc.server.module.descriptors", "ServiceDescriptor")

    val ByteReadPacket = ClassName("io.ktor.utils.io.core", "ByteReadPacket")

    val Payload = ClassName("io.rsocket.kotlin.payload", "Payload")

    object ProcedureDescriptor {
        val base = ClassName(
            "org.timemates.rrpc.server.module.descriptors", "ProcedureDescriptor"
        )

        val requestResponse = base.nestedClass("RequestResponse")

        val requestStream = base.nestedClass("RequestStream")

        val requestChannel = base.nestedClass("RequestChannel")
    }

    object Option {
        val Base = ClassName("org.timemates.rrpc.options", "Option")
        val File = ClassName("org.timemates.rrpc.options", "FileOption")
        val Service = ClassName("org.timemates.rrpc.options", "ServiceOption")
        val RPC = ClassName("org.timemates.rrpc.options", "RPCOption")
    }

    val Options = ClassName("org.timemates.rrpc.options", "Options")

    val ClientMetadata = ClassName("org.timemates.rrpc.metadata", "ClientMetadata")

    val ExtraMetadata = ClassName("org.timemates.rrpc.metadata", "ExtraMetadata")

    val ProtoBuf = ClassName("kotlinx.serialization.protobuf", "ProtoBuf")

    val RSocket = ClassName("io.rsocket.kotlin", "RSocket")

    val RRpcClientConfig = ClassName("org.timemates.rrpc.client.config", "RRpcClientConfig")

    val RRpcServerService = ClassName("org.timemates.rrpc.server.module", "RRpcService")

    val RRpcClientService = ClassName("org.timemates.rrpc.client", "RRpcServiceClient")

    val ExperimentalSerializationApi = ClassName("kotlinx.serialization", "ExperimentalSerializationApi")

    val KSerializer = ClassName("kotlinx.serialization", "KSerializer")

    val Interceptors = ClassName("org.timemates.rrpc.interceptors", "Interceptors")

    val RequestContext = ClassName("org.timemates.rrpc.server", "RequestContext")

    val ProtoType = ClassName("org.timemates.rrpc", "ProtoType")

    fun ProtoTypeDefinition(type: TypeName) = ProtoType.nestedClass("Definition")
            .parameterizedBy(type)

    object RM {
        val File = ClassName("org.timemates.rrpc.common.metadata", "RMFile")
        val Constant = ClassName("org.timemates.rrpc.common.metadata", "RMEnumConstant")
        val Extend = ClassName("org.timemates.rrpc.common.metadata", "RMEnumConstant")
        val Field = ClassName("org.timemates.rrpc.common.metadata", "RMField")
        val OneOf = ClassName("org.timemates.rrpc.common.metadata", "RMOneOf")
        val Option = ClassName("org.timemates.rrpc.common.metadata", "RMOption")
        val OptionValueRaw = Option.nestedClass("Value").nestedClass("Raw")
        val OptionValueRawMap = Option.nestedClass("Value").nestedClass("RawMap")
        val OptionValueMessageMap = Option.nestedClass("Value").nestedClass("MessageMap")
        val Options = ClassName("org.timemates.rrpc.common.metadata", "RMOptions")
        val Rpc = ClassName("org.timemates.rrpc.common.metadata", "RMRpc")
        val Message = ClassName("org.timemates.rrpc.common.metadata", "RMType", "Message")
        val Enum = ClassName("org.timemates.rrpc.common.metadata", "RMType", "Enum")
        val Enclosing = ClassName("org.timemates.rrpc.common.metadata", "RMType", "Enclosing")

        val Service = ClassName("org.timemates.rrpc.common.metadata", "RMService")

        val TypeMemberUrl = ClassName("org.timemates.rrpc.common.metadata", "RMTypeMemberUrl")
        val StreamableTypeUrl = ClassName("org.timemates.rrpc.common.metadata", "StreamableRMTypeUrl")

        object Value {
            val PackageName = ClassName("org.timemates.rrpc.common.metadata.value", "RMPackageName")
            val TypeUrl = ClassName("org.timemates.rrpc.common.metadata.value", "RMTypeUrl")
        }

        val Resolver = ClassName("org.timemates.rrpc.common.metadata", "RMResolver")

        object Server {
            val MetadataLookup = ClassName("org.timemates.rrpc.server.metadata", "MetadataLookup")
        }
    }
}