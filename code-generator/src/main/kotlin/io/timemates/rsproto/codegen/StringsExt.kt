package io.timemates.rsproto.codegen

internal fun String.capitalized(): String {
    return replaceFirstChar { it.uppercaseChar() }
}