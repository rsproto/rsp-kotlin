plugins {
    id(libs.plugins.conventions.jvm.core.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

group = "org.timemates.rrpc"
version = System.getenv("LIB_VERSION") ?: "SNAPSHOT"

dependencies {
    // -- Project --
    implementation(projects.server.core)
    implementation(projects.server.schema)
    implementation(projects.client.core)
    implementation(projects.client.schema)

    // -- Serialization --
    implementation(libs.kotlinx.serialization.proto)

    // -- RSocket --
    implementation(libs.rsocket.client)
    implementation(libs.rsocket.server)

    // -- Ktor --
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.websockets)

    // -- JUnit --
    implementation(libs.junit.jupiter)

    // -- MockK --
    implementation(libs.mockk)

    // -- Coroutines
    implementation(libs.kotlinx.coroutines.test)
}


