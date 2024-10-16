plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

group = "org.timemates.rrpc"
version = System.getenv("LIB_VERSION") ?: "SNAPSHOT"

dependencies {
    // -- Project --
    commonMainImplementation(projects.common.core)

    // -- Ktor --
    commonMainImplementation(libs.ktor.server.core)
    commonMainImplementation(libs.ktor.server.websockets)

    // -- RSocket --
    commonMainImplementation(libs.rsocket.server)

    // -- Serialization --
    commonMainImplementation(libs.kotlinx.serialization.proto)
}


mavenPublishing {
    coordinates(
        groupId = "org.timemates.rrpc",
        artifactId = "server-core",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Server Core")
        description.set("Multiplatform Kotlin core library for RRpc servers.")
    }
}
