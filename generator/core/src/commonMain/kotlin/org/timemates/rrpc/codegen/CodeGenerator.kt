package org.timemates.rrpc.codegen

import com.squareup.wire.schema.Location
import com.squareup.wire.schema.SchemaLoader
import org.timemates.rrpc.codegen.adapters.SchemaAdapter
import org.timemates.rrpc.codegen.configuration.RMGlobalConfiguration
import org.timemates.rrpc.common.schema.RMResolver
import kotlin.io.path.absolutePathString

public object CodeGenerator {
    public fun generate(
        configuration: RMGlobalConfiguration,
        adapters: Map<SchemaAdapter.Config, SchemaAdapter>,
    ): Unit = with(configuration) {
        output.fs.createDirectories(output.path)

        val schemaLoader = SchemaLoader(configuration.inputFs)
        schemaLoader.permitPackageCycles = configuration.permitPackageCycles

        schemaLoader.initRoots(
            sourcePaths.map { Location.get(it.toNioPath().absolutePathString()) }
        )

        val schema = schemaLoader.loadSchema()

        val resolver = schema.protoFiles
            .filter {
                it.packageName?.startsWith("wire") != true &&
                    it.location.toString() != "google/protobuf/descriptor.proto" &&
                    // wrappers are ignored, because they're generated as usual kotlin types, but nullable
                    it.location.toString() != "google/protobuf/wrappers.proto"
                    // the following types already in the common-core
                    && it.location.toString() != "google/protobuf/timestamp.proto"
                    && it.location.toString() != "google/protobuf/duration.proto"
                    && it.location.toString() != "google/protobuf/any.proto"
                    && it.location.toString() != "google/protobuf/struct.proto"
                    && it.location.toString() != "google/protobuf/empty.proto"
            }
            .map { file ->
                file.asRMFile()
            }.let { files -> RMResolver(files) }

        adapters.forEach { (cfg, adapter) ->
            adapter.process(cfg, resolver)
        }
    }
}