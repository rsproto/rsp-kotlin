package org.timemates.rrpc.plugin

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

/**
 * Class representing the extension for generating Protobuf code.
 */
@RRpcGradlePluginDsl
public open class RRpcExtension(objects: ObjectFactory) {
    /**
     * If you have custom project structure that does not have commonMain / main sourceSets,
     * you should specify your main source set in the [targetSourceSet].
     */
    public val targetSourceSet: Property<String?> = objects.property<String?>()
        .convention(null)

    /**
     * Settings for specifying the type of generation (Client / Server)
     */
    public val profiles: Profiles = Profiles(objects)

    @RRpcGradlePluginDsl
    public fun profiles(block: Profiles.() -> Unit) {
        profiles.apply(block)
    }

    /**
     * Extra options for generation.
     */
    public val options: Options = Options(objects)

    @RRpcGradlePluginDsl
    public fun options(block: Options.() -> Unit) {
        profiles.global.options.apply(block)
    }

    /**
     * Settings for specifying generation-related paths.
     */
    public val paths: Paths = Paths(objects)

    @RRpcGradlePluginDsl
    public fun paths(block: Paths.() -> Unit) {
        profiles.global.paths.apply(block)
    }

    @RRpcGradlePluginDsl
    public class Profiles(objects: ObjectFactory) {
        /**
         * Represents the flag indicating whether the code generation for the client should be performed.
         */
        public val client: ClientProfile = ClientProfile(objects)

        public fun client(block: ClientProfile.() -> Unit) {
            client.apply(block)
        }

        /**
         * Represents the flag indicating whether the code generation for the server should be performed.
         */
        public val server: ServerProfile = ServerProfile(objects)

        public fun server(block: ServerProfile.() -> Unit) {
            server.apply(block)
        }

        /**
         * Global profile. Other profiles always have everything added here.
         */
        public val global: GlobalProfile = GlobalProfile(objects)

        public fun global(block: GlobalProfile.() -> Unit) {
            global.apply(block)
        }

        @RRpcGradlePluginDsl
        public class GlobalProfile(objects: ObjectFactory) {
            public val options: Options = Options(objects)
            public val paths: Paths = Paths(objects)

            public fun options(block: Options.() -> Unit) {
                options.apply(block)
            }

            public fun paths(block: Paths.() -> Unit) {
                paths.apply(block)
            }
        }

        @RRpcGradlePluginDsl
        public class ClientProfile(objects: ObjectFactory) {
            public val enabled: Property<Boolean> = objects.property<Boolean>().convention(true)
            public val options: Options = Options(objects)
            public val paths: Paths = Paths(objects)

            public fun options(block: Options.() -> Unit) {
                options.apply(block)
            }

            public fun paths(block: Paths.() -> Unit) {
                paths.apply(block)
            }
        }

        @RRpcGradlePluginDsl
        public class ServerProfile(objects: ObjectFactory) {
            public val metadata: Metadata = Metadata(objects)
            public val enabled: Property<Boolean> = objects.property<Boolean>().convention(true)

            public val options: Options = Options(objects)
            public val paths: Paths = Paths(objects)

            public fun metadata(block: Metadata.() -> Unit) {
                metadata.apply(block)
            }

            public fun options(block: Options.() -> Unit) {
                options.apply(block)
            }

            public fun paths(block: Paths.() -> Unit) {
                paths.apply(block)
            }

            @RRpcGradlePluginDsl
            public class Metadata(objects: ObjectFactory) {
                public val enabled: Property<Boolean> = objects.property<Boolean>().convention(false)
                public val scoped: Property<Boolean> = objects.property<Boolean>().convention(false)
                public val name: Property<String> = objects.property<String>().convention(null)
            }
        }
    }

    @RRpcGradlePluginDsl
    public class Options(objects: ObjectFactory) {
        public val permitPackageCycles: Property<Boolean> = objects.property<Boolean>()
            .convention(false)
    }

    @RRpcGradlePluginDsl
    public class Paths(objects: ObjectFactory) {
        /**
         * Contains the path to the folder where the Proto definition files are located.
         */
        public val protoSources: Property<String?> =
            objects.property<String?>().convention(null)
            // objects.property<String>().convention("src/main/proto")

        /**
         * Contains the path to the folder where the generated code will be saved.
         */
//        public val generationOutput: Property<String?> =
//            objects.property<String?>.convention(null)
//            // objects.property<String>().convention("generated/rrpc/src/commonMain")
    }
}