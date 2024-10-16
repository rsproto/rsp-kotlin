package org.timemates.rrpc.exceptions

public class ServiceNotFoundException(
    serviceName: String,
) : RRpcException("Service '$serviceName' is not found.")