package org.timemates.rrpc.plugin

import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.timemates.rrpc.codegen.CodeGenerator
import org.timemates.rrpc.codegen.configuration.RMGlobalConfiguration
import java.io.File

//public class RSocketProtoGeneratorPlugin : Plugin<Project> {
//    override fun apply(target: Project) {
//        val extension = target.extensions.create<RRpcExtension>("rrpc", target.objects)
//        val generationOutputPath = target.layout.buildDirectory.file(extension.paths.generationOutput)
//
//        val generateProto = target.tasks.create("generateCode") {
//            group = "rrpc"
//
//            inputs.dir(extension.paths.protoSources)
//            outputs.dir(generationOutputPath)
//
//            doLast {
//                val codeGenerator = CodeGenerator.generate(
//                    FileSystem.SYSTEM,
//                    adapters = mapOf()
//                )
//
//                try {
//                    generationOutputPath.get()
//                        .asFile
//                        .listFiles()
//                        ?.forEach(File::deleteRecursively)
//                } catch (e: Exception) {
//                    if(logger.isDebugEnabled)
//                        logger.error(e.stackTraceToString())
//                }
//
//                codeGenerator.generate(
//                    configuration = RMGlobalConfiguration(
//                        inputs = target.file(extension.paths.protoSources.get()).toOkioPath(),
//                        output = target.file(generationOutputPath).toOkioPath(),
//                        clientGeneration = extension.profiles.client.get(),
//                        serverGeneration = extension.profiles.server.get(),
//                        builderTypes = extension.options.builderTypes.get(),
//                        permitPackageCycles = extension.options.permitPackageCycles.get(),
//                    )
//                )
//            }
//        }
//
//        target.afterEvaluate {
//            val allSourceSets = target.extensions.findByType<KotlinMultiplatformExtension>()?.sourceSets
//                ?: target.extensions.findByType<KotlinJvmProjectExtension>()?.sourceSets
//                ?: target.extensions.findByType<KotlinAndroidProjectExtension>()?.sourceSets
//                ?: error("Does Kotlin plugin apply to the buildscript?")
//
//            val commonSourceSet = allSourceSets
//                .findByName(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME)
//            val mainSourceSet = allSourceSets.findByName("main")
//
//            val sourceSet = if (extension.targetSourceSet.getOrNull() != null)
//                allSourceSets.getByName(extension.targetSourceSet.get())
//            else commonSourceSet ?: mainSourceSet
//
//            sourceSet
//                ?.kotlin
//                ?.srcDirs(generateProto.outputs)
//                ?: error(SOURCE_SET_NOT_FOUND)
//        }
//    }
//}
//
//private const val SOURCE_SET_NOT_FOUND =
//    "Unable to obtain source set: you should have commonMain/main or custom one that is set up in the [rrpc.targetSourceSet]"