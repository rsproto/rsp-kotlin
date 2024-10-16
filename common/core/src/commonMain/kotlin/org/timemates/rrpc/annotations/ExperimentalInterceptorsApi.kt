package org.timemates.rrpc.annotations

@RequiresOptIn(message = "This API has subject to change.", level = RequiresOptIn.Level.WARNING)
@Target(
    allowedTargets = [
        AnnotationTarget.CLASS,
        AnnotationTarget.CONSTRUCTOR,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.TYPEALIAS,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.PROPERTY
    ],
)
public annotation class ExperimentalInterceptorsApi