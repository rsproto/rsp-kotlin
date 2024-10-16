package org.timemates.rrpc.codegen.adapters

import okio.FileSystem
import okio.Path
import org.timemates.rrpc.common.schema.RMResolver

public interface SchemaAdapter {
    public data class Config(
        /**
         * Specifies whether generator should generate client stubs.
         */
        public val clientGeneration: Boolean,

        /**
         * Specifies whether generator should generate server stubs.
         */
        public val serverGeneration: Boolean,

        /**
         * Specifies whether generator should generate metadata information
         * for MS (Metadata Service). Common rRPC Metadata Library is required.
         */
        public val metadata: MetadataSettings,

        /**
         * Options that are specified when generation is called.
         */
        public val options: Map<String, String>,

        /**
         * The output directory among with [FileSystem]. Might be null if
         * schema adapter isn't used for writing to a file system. For such cases,
         * it may print everything to stdout.
         */
        public val output: List<Output>,
    ) {
        public sealed interface Output {
            public data class FS(
                public val fileSystem: FileSystem,
                public val path: Path,
            ) : Output

            // applicable in situations when adapter might emit data somewhere else from a
            // standard situation
            // Handled individually by the Adapter.
            public class Custom(public val name: String) : Output
        }

        public class MetadataSettings(
            public val enabled: Boolean,
            // if a name is not assigned, the random one is used.
            public val name: String? = null,
            /**
             * Specifies whether generated metadata should be assigned to the global context
             * or should be leaved scoped in an object.
             *
             * For `true`, the [name] should be specified.
             */
            public val scoped: Boolean,
        )
    }

    /**
     * This method is used for generating the code usually, but can be used for other
     * purposes, for an example, – logging.
     *
     * @return RMResolver that might be the same as [resolver] or new in cases
     * when you want to modify incoming data to the following adapters.
     */
    public fun process(
        config: Config,
        resolver: RMResolver,
    ): RMResolver
}