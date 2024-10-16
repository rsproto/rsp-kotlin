package org.timemates.rrpc.server

import org.timemates.rrpc.options.Option
import org.timemates.rrpc.options.OptionsWithValue

/**
 * An interface representing a container that holds a list of options.
 */
public interface OptionsContainer {
    /**
     * The list of options contained within this container.
     */
    public val options: OptionsWithValue

    /**
     * Checks if the specified option is present in the container.
     *
     * @param option The option to check for.
     * @return True if the option is present, false otherwise.
     */
    public fun hasOption(option: Option<*>): Boolean

    /**
     * Retrieves the value of the specified option.
     *
     * @param option The option to retrieve.
     * @return The value of the specified option.
     * @throws NoSuchElementException if the option is not present.
     */
    public fun <T> getOption(option: Option<T>): T
}

/**
 * Retrieves the value of the specified option if it is present, or null if it is not.
 *
 * @param option The option to retrieve.
 * @return The value of the option, or null if the option is not present.
 */
public fun <T> OptionsContainer.getOptionOrNull(option: Option<T>): T? {
    return if (hasOption(option)) getOption(option) else null
}

/**
 * Retrieves the value of the specified option if it is present, or a default value if it is not.
 *
 * @param option The option to retrieve.
 * @param defaultValue The default value to return if the option is not present.
 * @return The value of the option, or the default value if the option is not present.
 */
public fun <T> OptionsContainer.getOptionOrElse(
    option: Option<T>,
    defaultValue: () -> T
): T {
    return if (hasOption(option)) getOption(option) else defaultValue()
}

internal fun optionsContainer(
    map: OptionsWithValue,
): OptionsContainer = OptionsContainerImpl(map)

@JvmInline
internal value class OptionsContainerImpl(
    override val options: OptionsWithValue
) : OptionsContainer {
    override fun hasOption(option: Option<*>): Boolean = options[option] != null

    @Suppress("UNCHECKED_CAST")
    override fun <T> getOption(option: Option<T>): T = options[option] as T
}