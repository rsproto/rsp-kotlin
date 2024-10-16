plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

group = "org.timemates.rrpc"
version = System.getenv("LIB_VERSION") ?: "SNAPSHOT"

dependencies {
    // -- Project --
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.schema)
    commonMainImplementation(projects.client.core)

    // -- Test --
    jvmTestImplementation(libs.kotlin.test)
    jvmTestImplementation(libs.mockk)
}


kotlin {
    jvm()
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
        artifactId = "server-metadata-client",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Server Metadata Client")
        description.set("Multiplatform Kotlin Library for working with Server Metadata.")
    }
}
