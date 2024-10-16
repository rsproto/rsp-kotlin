package org.timemates.rrpc.options

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
public value class OptionsWithValue(private val map: Map<Option<*>, Any>) {
    public constructor(vararg pairs: Pair<Option<*>, Any>) : this(mapOf(*pairs))

    public companion object {
        @JvmStatic
        public val EMPTY: OptionsWithValue = OptionsWithValue(emptyMap())
    }

    public operator fun <T> get(option: Option<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return map[option] as? T
    }

    public operator fun plus(other: OptionsWithValue): OptionsWithValue {
        return OptionsWithValue(this.map + other.map)
    }

    public operator fun <T : Any> plus(other: Pair<Option<T>, T>): OptionsWithValue {
        return OptionsWithValue(this.map + (other.first to other.second))
    }

    public operator fun minus(option: Option<*>): OptionsWithValue {
        return OptionsWithValue(map - option)
    }

    public fun asMap(): Map<Option<*>, Any> = map
}

public fun OptionsWithValue.hasOption(option: Option<*>): Boolean = get(option) != null