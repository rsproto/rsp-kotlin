package org.timemates.rrpc.generator.kotlin.ext

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import org.timemates.rrpc.codegen.typemodel.Types
import org.timemates.rrpc.common.schema.Language
import org.timemates.rrpc.common.schema.RMResolver
import org.timemates.rrpc.common.schema.StreamableRMTypeUrl
import org.timemates.rrpc.common.schema.annotations.NonPlatformSpecificAccess
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

@OptIn(NonPlatformSpecificAccess::class)
internal fun RMDeclarationUrl.asClassName(resolver: RMResolver): ClassName {
    return when (this) {
        RMDeclarationUrl.ANY -> ClassName("com.google.protobuf", "ProtoAny")
        RMDeclarationUrl.TIMESTAMP -> ClassName("com.google.protobuf", "ProtoTimestamp")
        RMDeclarationUrl.DURATION -> ClassName("com.google.protobuf", "ProtoDuration")
        RMDeclarationUrl.STRUCT_MAP -> ClassName("com.google.protobuf", "ProtoStruct")
        RMDeclarationUrl.EMPTY -> ClassName("com.google.protobuf", "ProtoEmpty")
        else -> {
            val file = resolver.resolveFileOf(this) ?: return ClassName(enclosingTypeOrPackage ?: "", simpleName)

            val packageName: String = file.platformPackageName(Language.KOTLIN).value
            val enclosingName: String = (enclosingTypeOrPackage?.replace(file.packageName.value, "") ?: "")
                .replace("..", ".")

            ClassName(packageName + enclosingName, simpleName)
        }
    }
}

internal fun StreamableRMTypeUrl.asClassName(resolver: RMResolver): TypeName {
    return when (isStreaming) {
        true -> Types.Flow(type.asClassName(resolver))
        false -> type.asClassName(resolver)
    }
}

@OptIn(NonPlatformSpecificAccess::class)
internal fun RMDeclarationUrl.qualifiedName(resolver: RMResolver): String {
    val packageName = resolver.resolveFileOf(this)?.packageName?.value?.plus(".") ?: ""

    return packageName + simpleName
}