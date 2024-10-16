package org.timemates.rrpc.common.schema.annotations

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
)
@RequiresOptIn(
    message = "This declaration requires careful usage regarding different platforms.",
    level = RequiresOptIn.Level.WARNING,
)
public annotation class NonPlatformSpecificAccess