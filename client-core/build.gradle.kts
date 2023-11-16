plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    commonMainImplementation(libs.rsocket.client)
    commonMainImplementation(libs.kotlinx.serialization.proto)
}

mavenPublishing {
    coordinates(
        groupId = "io.timemates.rsproto",
        artifactId = "client-core",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RSProto Client Core")
        description.set("Multiplatform Kotlin core library for RSProto clients.")
    }
}