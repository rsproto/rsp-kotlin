package org.timemates.rrpc.generator.kotlin.ext

internal fun String.capitalized(): String {
    return replaceFirstChar { it.uppercaseChar() }
}

internal fun String.decapitalized(): String {
    return replaceFirstChar { it.lowercaseChar() }
}

internal fun String.protoByteStringToByteArray(): ByteArray {
    val result = mutableListOf<Byte>()
    var i = 0

    while (i < length) {
        when {
            startsWith("\\x", i) && i + 3 < length -> {
                val hexValue = substring(i + 2, i + 4)
                val byteValue = hexValue.toInt(16).toByte()
                result.add(byteValue)
                i += 4
            }
            else -> {
                result.add(get(i).code.toByte())
                i++
            }
        }
    }
    return result.toByteArray()
}