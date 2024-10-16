plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    // -- Project --
    commonMainImplementation(projects.common.core)

    // -- Serialization --
    commonMainImplementation(libs.kotlinx.serialization.proto)
}


kotlin {
//    js(IR) {
//        browser()
//        nodejs()
//    }
//    iosArm64()
//    iosX64()
//    iosSimulatorArm64()
}

mavenPublishing {
    coordinates(
        groupId = "org.timemates.rrpc",
        artifactId = "common-metadata",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Common Metadata")
        description.set("Multiplatform Kotlin Mutadata library for RRpc servers and clients.")
    }
}