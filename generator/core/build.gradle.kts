plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
}

kotlin {
    explicitApi()
}

group = "org.timemates.rrpc"
version = System.getenv("LIB_VERSION") ?: "SNAPSHOT"

dependencies {
    // -- Project --
    commonMainApi(projects.common.schema)

    // -- SquareUp --
    commonMainImplementation(libs.squareup.wire.schema)
    commonMainImplementation(libs.squareup.kotlinpoet)
    commonMainImplementation(libs.squareup.okio)

    // -- Test --
    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.squareup.okio.fakeFs)
}


mavenPublishing {
    coordinates(
        groupId = "org.timemates.rrpc",
        artifactId = "generator",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Code Generator Core")
        description.set("Code-generation library for RRpc servers and clients.")
    }
}