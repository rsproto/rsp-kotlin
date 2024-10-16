package org.timemates.rrpc.exceptions

import org.timemates.rrpc.metadata.ClientMetadata

public class ProcedureNotFoundException(
    metadata: ClientMetadata
) : Exception("Procedure '${metadata.procedureName}' is not found in service named '${metadata.serviceName}'")