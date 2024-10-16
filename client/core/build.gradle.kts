plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

group = "org.timemates.rrpc"
version = System.getenv("LIB_VERSION") ?: "SNAPSHOT"

dependencies {
    dependencies {
        // -- Project --
        commonMainImplementation(projects.common.core)

        // -- RSocket --
        commonMainApi(libs.rsocket.client)

        // -- Test --
        jvmTestImplementation(libs.kotlin.test)
        jvmTestImplementation(libs.mockk)
    }

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
        artifactId = "client-core",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Client Core")
        description.set("Multiplatform Kotlin core library for RRpc clients.")
    }
}
