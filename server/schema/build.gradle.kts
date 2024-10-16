plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

group = "org.timemates.rrpc"
version = System.getenv("LIB_VERSION") ?: "SNAPSHOT"

dependencies {
    // -- Project --
    commonMainImplementation(projects.server.core)
    commonMainImplementation(projects.common.schema)

    // -- Ktor --
    commonMainImplementation(libs.ktor.server.core)
    commonMainImplementation(libs.ktor.server.websockets)

    // -- Serialization --
    commonMainImplementation(libs.kotlinx.serialization.proto)
}

mavenPublishing {
    coordinates(
        groupId = "org.timemates.rrpc",
        artifactId = "server-metadata",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Server Metadata")
        description.set("Multiplatform Kotlin metadata library for RRpc servers.")
    }
}
