package com.google.protobuf

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

public fun ProtoTimestamp.toJavaInstant(): Instant =
    Instant.ofEpochSecond(seconds, nanos.toLong())

public fun ProtoTimestamp.toJavaLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(toJavaInstant(), ZoneOffset.UTC)

public fun ProtoTimestamp.toJavaLocalDate(): LocalDate =
    LocalDate.ofInstant(toJavaInstant(), ZoneOffset.UTC)