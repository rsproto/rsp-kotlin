package org.timemates.rrpc.common.schema

import org.timemates.rrpc.common.schema.value.RMPackageName
import org.timemates.rrpc.common.schema.value.RMDeclarationUrl

public fun RMResolver(
    project: List<RMFile>,
): RMResolver = TODO()

public fun RMResolver(
    vararg resolvers: RMResolver,
): RMResolver = TODO()

/**
 * Interface for resolving various components (fields, types, files, services) in the RPC metadata model.
 * This provides a lookup mechanism for retrieving metadata elements such as types, services, fields,
 * extensions, and files based on unique identifiers like type URLs or package names.
 */
public interface RMResolver {

    /**
     * Resolves a field within a type by the given [typeMemberUrl].
     *
     * @param typeMemberUrl The URL that identifies the specific field within the type.
     * @return The corresponding [RMField] if found, or `null` if no matching field is found.
     */
    public fun resolveField(typeMemberUrl: RMTypeMemberUrl): RMField?

    /**
     * Resolves a type by the given [url].
     *
     * @param url The unique identifier for the type, typically used in protobuf definitions.
     * @return The corresponding [RMType] if found, or `null` if no matching type is found.
     */
    public fun resolveType(url: RMDeclarationUrl): RMType?

    /**
     * Resolves an extension by the given [url].
     *
     * @param url The unique identifier for the extension.
     * @return The corresponding [RMExtend] if found, or `null` if no matching extension is found.
     */
    public fun resolveExtend(url: RMDeclarationUrl): RMExtend?

    /**
     * Resolves a file by the given [packageName] and [name].
     *
     * @param packageName The package where the file is defined.
     * @param name The name of the file.
     * @return The corresponding [RMFile] if found, or `null` if no matching file is found.
     */
    public fun resolveFileOf(packageName: RMPackageName, name: String): RMFile?

    /**
     * Resolves the file where a type is present.
     *
     * @param url The reference to the type.
     * @return The corresponding [RMFile] where the type is found, or `null` if no matching file is found.
     */
    public fun resolveFileOf(url: RMDeclarationUrl): RMFile?

    /**
     * Resolves a service by the given [url].
     *
     * @param url The unique identifier for the service, typically used in protobuf definitions.
     * @return The corresponding [RMService] if found, or `null` if no matching service is found.
     */
    public fun resolveService(url: RMDeclarationUrl): RMService?

    /**
     * Resolves all available files in the current [RMResolver].
     *
     * @return A sequence of all [RMFile]s available within this resolver.
     */
    public fun resolveAvailableFiles(): Sequence<RMFile>

    /**
     * Resolves all available services in the current [RMResolver].
     *
     * @return A sequence of all [RMService]s available within this resolver.
     */
    public fun resolveAllServices(): Sequence<RMService>

    /**
     * Resolves all available types in the current [RMResolver].
     *
     * @return A sequence of all [RMType]s available within this resolver.
     */
    public fun resolveAllTypes(): Sequence<RMType>

    /**
     * Resolves nodes with a specific option defined by the given [optionFieldUrl].
     *
     * @param optionFieldUrl The URL of the option field to search for within the nodes.
     * @return A sequence of [RMNode]s that contain the specified option.
     */
    public fun resolveNodesWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMNode>

    /**
     * Resolves services with a specific option defined by the given [optionFieldUrl].
     *
     * @param optionFieldUrl The URL of the option field to search for within the services.
     * @return A sequence of [RMService]s that contain the specified option.
     */
    public fun resolveServicesWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMService>

    /**
     * Resolves types with a specific option defined by the given [optionFieldUrl].
     *
     * @param optionFieldUrl The URL of the option field to search for within the types.
     * @return A sequence of [RMType]s that contain the specified option.
     */
    public fun resolveTypesWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMType>

    /**
     * Resolves RPCs (Remote Procedure Calls) with a specific option defined by the given [optionFieldUrl].
     *
     * @param optionFieldUrl The URL of the option field to search for within the RPCs.
     * @return A sequence of [RMRpc]s that contain the specified option.
     */
    public fun resolveRpcsWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMRpc>

    /**
     * Resolves fields with a specific option defined by the given [optionFieldUrl].
     *
     * @param optionFieldUrl The URL of the option field to search for within the fields.
     * @return A sequence of [RMField]s that contain the specified option.
     */
    public fun resolveFieldsWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMField>

    /**
     * Resolves constants (enum values) with a specific option defined by the given [optionFieldUrl].
     *
     * @param optionFieldUrl The URL of the option field to search for within the constants.
     * @return A sequence of [RMEnumConstant]s that contain the specified option.
     */
    public fun resolveConstantsWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMEnumConstant>

    /**
     * Filters nodes based on a provided condition.
     *
     * @param condition A lambda that represents the filtering logic for [RMNode]s.
     * @return A new [RMResolver] that only includes the nodes satisfying the provided condition.
     */
    public fun filterNodes(condition: (RMNode) -> Boolean): RMResolver
}

//private class InMemoryRMResolver(
//    private val files: List<RMFile>,
//) : RMResolver {
//    override fun resolveField(typeMemberUrl: RMTypeMemberUrl): RMField? {
//        TODO()
//    }
//
//    override fun resolveType(url: RMDeclarationUrl): RMType? {
//        TODO()
//    }
//
//    override fun resolveFileOf(packageName: RMPackageName, name: String): RMFile? {
//        TODO()
//    }
//
//    override fun resolveService(url: RMDeclarationUrl): RMService? {
//        TODO()
//    }
//
//    override fun resolveAvailableFiles(): Sequence<RMFile> {
//        return files.asSequence()
//    }
//
//    override fun resolveAllServices(): Sequence<RMService> {
//        return resolveAvailableFiles().flatMap { it.services }
//    }
//
//    override fun resolveAllTypes(): Sequence<RMType> {
//        return resolveAvailableFiles().flatMap { it.types }
//    }
//
//    override fun resolveNodesWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMNode> {
//        TODO("Not yet implemented")
//    }
//
//    override fun resolveServicesWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMService> {
//        TODO("Not yet implemented")
//    }
//
//    override fun resolveRpcsWithOption(optionFieldUrl: RMDeclarationUrl): Sequence<RMRpc> {
//        TODO("Not yet implemented")
//    }
//
//    override fun resolveFieldsWithOption(optionFieldUrl: RMDeclarationUrl): Sequence<RMField> {
//        TODO("Not yet implemented")
//    }
//
//    override fun resolveConstantsWithOption(optionFieldUrl: RMDeclarationUrl): Sequence<RMEnumConstant> {
//        TODO("Not yet implemented")
//    }
//}

//private class CompoundRMResolver(
//    private vararg val resolvers: RMResolver,
//) : RMResolver {
//    override fun resolveField(typeMemberUrl: RMTypeMemberUrl): RMField? {
//        return resolvers.firstNotNullOfOrNull { it.resolveField(typeMemberUrl) }
//    }
//
//    override fun resolveType(url: RMDeclarationUrl): RMType? {
//        return resolvers.firstNotNullOfOrNull { it.resolveType(url) }
//    }
//
//    override fun resolveFileOf(packageName: RMPackageName, name: String): RMFile? {
//        return resolvers.firstNotNullOfOrNull { it.resolveFileOf(packageName, name) }
//    }
//
//    override fun resolveService(url: RMDeclarationUrl): RMService? {
//        return resolvers.firstNotNullOfOrNull { it.resolveService(url) }
//    }
//
//    override fun resolveAvailableFiles(): Sequence<RMFile> {
//        return sequence {
//            resolvers.forEach {
//                yieldAll(it.resolveAvailableFiles())
//            }
//        }
//    }
//
//    override fun resolveAllServices(): Sequence<RMService> {
//        return sequence {
//            resolvers.forEach {
//                yieldAll(it.resolveAllServices())
//            }
//        }
//    }
//
//    override fun resolveAllTypes(): Sequence<RMType> {
//        return sequence {
//            resolvers.forEach {
//                yieldAll(it.resolveAllTypes())
//            }
//        }
//    }
//
//    override fun resolveNodesWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMNode> {
//        return sequenceOf(resolveServicesWithOption(optionFieldUrl), resolveFieldsWithOption(optionFieldUrl), resolve
//    }
//
//    override fun resolveServicesWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMService> {
//        return resolvers.asSequence().flatMap { resolver ->
//            resolver.resolveAllServices().filter { optionFieldUrl in it.options }
//        }
//    }
//
//    override fun resolveRpcsWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMRpc> {
//        return resolvers.asSequence().flatMap { resolver ->
//            resolver.resolveAllServices().flatMap { it.rpcs }.filter { rpc ->
//                optionFieldUrl in rpc.options
//            }
//        }
//    }
//
//    override fun resolveFieldsWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMField> {
//        return resolvers.asSequence().flatMap { resolver ->
//            resolver.resolveAllTypes().filterIsInstance<RMType.Message>().flatMap { it.fields }.filter { field ->
//                optionFieldUrl in field.options
//            }
//        }
//    }
//
//    override fun resolveConstantsWithOption(optionFieldUrl: RMTypeMemberUrl): Sequence<RMEnumConstant> {
//        return resolvers.asSequence().flatMap { resolver ->
//            resolver.resolveAllTypes().filterIsInstance<RMType.Enum>().flatMap { it.constants }.filter { constant ->
//                optionFieldUrl in constant.options
//            }
//        }
//    }
//}