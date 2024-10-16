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
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.generator.core)

    // -- SquareUp --
    commonMainImplementation(libs.squareup.kotlinpoet)
    commonMainImplementation(libs.squareup.okio)
}


mavenPublishing {
    coordinates(
        groupId = "org.timemates.rrpc",
        artifactId = "kotlin-generator",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("RRpc Kotlin Code Generator")
        description.set("Code-generation library for RRpc servers and clients.")
    }
}