package org.timemates.rrpc.server.schema

import io.rsocket.kotlin.RSocketError
import org.timemates.rrpc.common.schema.RMExtend
import org.timemates.rrpc.common.schema.RMFile
import org.timemates.rrpc.common.schema.RMService
import org.timemates.rrpc.common.schema.RMType
import org.timemates.rrpc.options.OptionsWithValue
import org.timemates.rrpc.server.schema.request.BatchedRequest
import org.timemates.rrpc.server.schema.request.PagedRequest
import org.timemates.rrpc.server.schema.request.decoded
import org.timemates.rrpc.server.module.RRpcService
import org.timemates.rrpc.server.module.descriptors.ProcedureDescriptor
import org.timemates.rrpc.server.module.descriptors.ServiceDescriptor

/**
 * Handles metadata-related operations for the RSocket-based reflection service.
 * This service exposes APIs that allow clients to query information about
 * available services, types, and extensions in the system.
 *
 * It supports both paged requests for larger datasets and batched requests
 * for retrieving multiple types of metadata in one go.
 *
 * @param group The metadata lookup group used for resolving services, types, and extensions.
 */
public class SchemaService(
    private val group: SchemaLookup = SchemaLookup.Global,
) : RRpcService {
    override val descriptor: ServiceDescriptor = ServiceDescriptor(
        name = "timemates.rrpc.server.schema.SchemaService",
        procedures = listOf(
            ProcedureDescriptor.RequestResponse(
                name = "GetAvailableServices",
                inputSerializer = PagedRequest.serializer(),
                outputSerializer = PagedRequest.Response.serializer(RMService.serializer()),
                procedure = { _, request -> getAvailableServices(request) },
                options = OptionsWithValue.EMPTY,
            ),
            ProcedureDescriptor.RequestResponse(
                name = "GetAvailableFiles",
                inputSerializer = PagedRequest.serializer(),
                outputSerializer = PagedRequest.Response.serializer(RMFile.serializer()),
                procedure = { _, request -> getAvailableFiles(request) },
                options = OptionsWithValue.EMPTY,
            ),
            ProcedureDescriptor.RequestResponse(
                name = "GetTypeDetailsBatch",
                inputSerializer = BatchedRequest.serializer(),
                outputSerializer = BatchedRequest.Response.serializer(RMType.serializer()),
                procedure = { _, request -> getTypeDetailsBatch(request) },
                options = OptionsWithValue.EMPTY,
            ),
            ProcedureDescriptor.RequestResponse(
                name = "GetExtendDetailsBatch",
                inputSerializer = BatchedRequest.serializer(),
                outputSerializer = BatchedRequest.Response.serializer(RMExtend.serializer()),
                procedure = { _, request -> getExtendDetailsBatch(request) },
                options = OptionsWithValue.EMPTY,
            ),
        ),
        options = OptionsWithValue.EMPTY,
    )

    /**
     * Retrieves a paginated list of available services from the metadata.
     *
     * @param request The paged request containing the pagination token and size.
     * @return A paginated response containing a list of RMService objects.
     */
    public fun getAvailableServices(request: PagedRequest): PagedRequest.Response<RMService> {
        val decoded = request.decoded()?.split(":")
        val index = decoded?.getOrElse(1) { invalidPageToken() }?.toInt() ?: -1

        val result = group.resolveAllServices().drop(index + 1).take(request.size ?: 20).toList()

        return PagedRequest.Response(
            list = result,
            nextCursor = if (result.size == request.size) "c:${index + result.size}" else null
        )
    }

    /**
     * Retrieves available files that are loaded into [SchemaLookup].
     *
     * @param request The paged request containing pagination information.
     * @return A paged response containing a list of available RMFiles.
     */
    public fun getAvailableFiles(request: PagedRequest): PagedRequest.Response<RMFile> {
        val decoded = request.decoded()?.split(":")
        val index = decoded?.getOrElse(1) { invalidPageToken() }?.toInt() ?: -1

        val result = group.resolveAvailableFiles().drop(index + 1).take(30).toList()

        return PagedRequest.Response(
            list = result,
            nextCursor = "c:${index + result.size}",
        )
    }

    /**
     * Retrieves type details based on a batch of requested URLs.
     *
     * Each RMDeclarationUrl is mapped to either the found RMType or null if no type was found.
     *
     * @param request The batched request containing a list of RMDeclarationUrl objects.
     * @return A response containing a map of RMDeclarationUrl to RMType (or null if not found).
     */
    public fun getTypeDetailsBatch(request: BatchedRequest): BatchedRequest.Response<RMType> {
        val types = request.urls.associateWith { group.resolveType(it) }
        return BatchedRequest.Response(types)
    }

    /**
     * Retrieves extension details based on a batch of requested URLs.
     *
     * Each RMDeclarationUrl is mapped to either the found RMExtend or null if no extension was found.
     *
     * @param request The batched request containing a list of RMDeclarationUrl objects.
     * @return A response containing a map of RMDeclarationUrl to RMExtend (or null if not found).
     */
    public fun getExtendDetailsBatch(request: BatchedRequest): BatchedRequest.Response<RMExtend> {
        val extensions = request.urls.associateWith { group.resolveExtend(it) }
        return BatchedRequest.Response(extensions)
    }
}

private fun invalidPageToken(): Nothing = throw RSocketError.Invalid("Invalid page token")