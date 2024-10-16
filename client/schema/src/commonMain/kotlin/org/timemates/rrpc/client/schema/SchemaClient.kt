package org.timemates.rrpc.client.schema

import org.timemates.rrpc.annotations.InternalRRpcAPI
import org.timemates.rrpc.client.RRpcServiceClient
import org.timemates.rrpc.client.config.RRpcClientConfig
import org.timemates.rrpc.client.options.RPCsOptions
import org.timemates.rrpc.client.schema.request.PagedRequest
import org.timemates.rrpc.common.schema.RMFile
import org.timemates.rrpc.common.schema.RMService
import org.timemates.rrpc.metadata.ClientMetadata
import org.timemates.rrpc.options.OptionsWithValue
import org.timemates.rrpc.client.schema.request.BatchedRequest
import org.timemates.rrpc.common.schema.RMExtend
import org.timemates.rrpc.common.schema.RMType
import io.rsocket.kotlin.RSocketError

/**
 * A client to interact with the `SchemaService` of the server, which provides metadata about
 * available services, types, and extensions. This client sends requests to the server and
 * receives the corresponding responses using the RSocket-based communication framework.
 *
 * @param config The configuration for the `RRpcServiceClient`, which defines connection settings.
 */
@OptIn(InternalRRpcAPI::class)
public class SchemaClient(
    config: RRpcClientConfig,
) : RRpcServiceClient(config) {

    public companion object {
        private const val SERVICE_NAME: String = "timemates.rrpc.server.schema.SchemaService"
    }

    /**
     * Creates an instance of the client using a configuration builder.
     *
     * @param creator A lambda to configure and build the `RRpcClientConfig` object.
     */
    public constructor(
        creator: RRpcClientConfig.Builder.() -> Unit,
    ) : this(RRpcClientConfig.create(creator))

    override val rpcsOptions: RPCsOptions = RPCsOptions.EMPTY

    /**
     * Fetches a paged list of available services from the server.
     *
     * @param request A [PagedRequest] defining pagination settings.
     * @return A [PagedRequest.Response] containing a list of [RMService] and the next page token.
     *
     * @throws RSocketError if the request fails.
     */
    public suspend fun getAvailableServices(request: PagedRequest): PagedRequest.Response<RMService> {
        return handler.requestResponse(
            metadata = ClientMetadata(
                serviceName = SERVICE_NAME,
                procedureName = "GetAvailableServices",
            ),
            data = request,
            options = OptionsWithValue.EMPTY,
            serializationStrategy = PagedRequest.serializer(),
            deserializationStrategy = PagedRequest.Response.serializer(RMService.serializer()),
        )
    }

    /**
     * Fetches a paged list of available files from the server.
     *
     * @param request A [PagedRequest] specifying pagination options.
     * @return A [PagedRequest.Response] containing a list of [RMFile] and the next page token.
     *
     * @throws RSocketError if the request fails.
     */
    public suspend fun getAvailableFiles(request: PagedRequest): PagedRequest.Response<RMFile> {
        return handler.requestResponse(
            metadata = ClientMetadata(
                serviceName = SERVICE_NAME,
                procedureName = "GetAvailableFiles",
            ),
            data = request,
            options = OptionsWithValue.EMPTY,
            serializationStrategy = PagedRequest.serializer(),
            deserializationStrategy = PagedRequest.Response.serializer(RMFile.serializer()),
        )
    }

    /**
     * Retrieves detailed information about multiple types in a batched request.
     *
     * @param request A [BatchedRequest] containing a list of [RMDeclarationUrl]s for the requested types.
     * @return A [BatchedRequest.Response] containing a map of each requested [RMDeclarationUrl] to its associated [RMType].
     *
     * @throws RSocketError if the request fails.
     */
    public suspend fun getTypeDetailsBatch(request: BatchedRequest): BatchedRequest.Response<RMType> {
        return handler.requestResponse(
            metadata = ClientMetadata(
                serviceName = SERVICE_NAME,
                procedureName = "GetTypeDetailsBatch",
            ),
            data = request,
            options = OptionsWithValue.EMPTY,
            serializationStrategy = BatchedRequest.serializer(),
            deserializationStrategy = BatchedRequest.Response.serializer(RMType.serializer()),
        )
    }

    /**
     * Retrieves detailed information about multiple extensions in a batched request.
     *
     * @param request A [BatchedRequest] containing a list of [RMDeclarationUrl]s for the requested extensions.
     * @return A [BatchedRequest.Response] containing a map of each requested [RMDeclarationUrl] to its associated [RMExtend].
     *
     * @throws RSocketError if the request fails.
     */
    public suspend fun getExtendDetailsBatch(request: BatchedRequest): BatchedRequest.Response<RMExtend> {
        return handler.requestResponse(
            metadata = ClientMetadata(
                serviceName = SERVICE_NAME,
                procedureName = "GetExtendDetailsBatch",
            ),
            data = request,
            options = OptionsWithValue.EMPTY,
            serializationStrategy = BatchedRequest.serializer(),
            deserializationStrategy = BatchedRequest.Response.serializer(RMExtend.serializer()),
        )
    }
}
