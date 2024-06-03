package com.n11.case_study.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateConverterUtils {
    public static ZonedDateTime translateDateTime(long timestamp, ZoneId zoneId) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId);
    }
}
