package org.timemates.rrpc.codegen.configuration

import okio.FileSystem
import okio.Path

public data class RMGlobalConfiguration(
    public val sourcePaths: List<Path>,
    public val inputFs: FileSystem,
    public val output: Output,
    public val permitPackageCycles: Boolean,
) {
    public data class Output(
        public val path: Path,
        public val fs: FileSystem,
    )
}