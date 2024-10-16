package com.google.protobuf

import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970

public fun ProtoTimestamp.toNSDate(): NSDate {
    val totalSeconds = this.seconds + this.nanos / 1_000_000_000.0
    return NSDate.dateWithTimeIntervalSince1970(totalSeconds)
}