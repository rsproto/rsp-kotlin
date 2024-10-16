package org.timemates.rrpc.codegen.exception

public class GenerationException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)