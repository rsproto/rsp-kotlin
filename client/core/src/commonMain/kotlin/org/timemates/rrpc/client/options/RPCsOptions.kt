package org.timemates.rrpc.client.options

import org.timemates.rrpc.options.OptionsWithValue

@JvmInline
public value class RPCsOptions(
    private val map: Map<String, OptionsWithValue>
) {
    public companion object {
        public val EMPTY: RPCsOptions = RPCsOptions(emptyMap())
    }

    public constructor(vararg pairs: Pair<String, OptionsWithValue>) : this(mapOf(*pairs))

    public operator fun get(name: String): OptionsWithValue? = map[name]
}

public fun RPCsOptions.getOrEmpty(name: String): OptionsWithValue = get(name) ?: OptionsWithValue.EMPTY