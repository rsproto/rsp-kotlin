package org.timemates.rrpc.client.schema.request

import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Represents a paginated request to retrieve metadata entities.
 *
 * @property cursor A token used for retrieving the next page of results.
 * @property size The maximum number of results to retrieve per page.
 */
@Serializable
public data class PagedRequest(
    public val cursor: String? = null,
    public val size: Int? = null,
) {
    public companion object {
        @OptIn(ExperimentalEncodingApi::class)
        internal fun encoded(string: String): String {
            return Base64.encode(string.toByteArray())
        }
    }

    /**
     * Response structure for paginated requests.
     * Contains the list of metadata nodes retrieved and a token for the next page.
     *
     * @param list A list of RMNode objects representing the metadata retrieved.
     * @param nextCursor A token to retrieve the next page of results, or null if no more results.
     */
    @Serializable
    public data class Response<T>(
        public val list: List<T>,
        public val nextCursor: String?,
    )
}

@OptIn(ExperimentalEncodingApi::class)
internal fun PagedRequest.decoded(): String? = this@decoded.cursor?.let { String(Base64.decode(it)) }