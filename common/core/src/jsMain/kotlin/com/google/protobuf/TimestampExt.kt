package com.google.protobuf

import kotlin.js.Date

public fun ProtoTimestamp.toJsDate(): Date = Date(
    seconds * 1000,
)