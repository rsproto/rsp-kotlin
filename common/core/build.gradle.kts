plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    // -- Serialization --
    commonMainApi(libs.kotlinx.serialization.proto)

    // -- Coroutines --
    commonMainImplementation(libs.kotlinx.coroutines)

    // -- Test --
    jvmTestImplementation(libs.kotlin.test)
    jvmTestImplementation(libs.mockk)
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
        artifactId = "common-core",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Common Core")
        description.set("Multiplatform Kotlin core library for RRpc servers and clients.")
    }
}