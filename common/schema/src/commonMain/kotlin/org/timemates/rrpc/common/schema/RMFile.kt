package org.timemates.rrpc.common.schema

import kotlinx.serialization.Serializable
import org.timemates.rrpc.common.schema.annotations.NonPlatformSpecificAccess
import org.timemates.rrpc.common.schema.value.RMPackageName
import org.timemates.rrpc.options.FileOption

@Serializable
public class RMFile(
    /**
     * Name of a source file.
     */
    public val name: String,

    /**
     * Package name specified in the proto file. It should not be accessed to generate
     * platform-specific code, but through `platformPackageName(language)`.
     */
    @NonPlatformSpecificAccess
    public val packageName: RMPackageName,

    /**
     * File-level options of the file.
     */
    public val options: RMOptions,

    /**
     * The services that are defined in the `.proto` file.
     */
    public val services: List<RMService>,

    /**
     * Types that are defined in `.proto` file.
     */
    public val types: List<RMType>,

    /**
     * Declared extends in file.
     */
    public val extends: List<RMExtend>,
) : RMNode {
    public companion object {
        public val JAVA_PACKAGE: FileOption<String> = FileOption("java_package", 1)
    }

    /**
     * Gets platform-specific package name based on the [language] that is provided by
     * accessing file-level options.
     */
    @OptIn(NonPlatformSpecificAccess::class)
    public fun platformPackageName(language: Language): RMPackageName {
        return when (language) {
            Language.JAVA, Language.KOTLIN -> TODO()
            Language.PHP -> TODO()
            Language.C_SHARP -> TODO()
            Language.PYTHON -> TODO()
        }.let(::RMPackageName)
    }
}

public fun RMFile.javaPackage(): RMPackageName = TODO()
public fun RMFile.kotlinPackage(): RMPackageName = TODO()
