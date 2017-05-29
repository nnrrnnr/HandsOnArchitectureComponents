package com.github.watanabear.handsonarchitecturecomponents.converter;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by watanabear on 2017/05/30.
 */

public class DateTypeConverter {

    @TypeConverter
    public static LocalDateTime toDate(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    @TypeConverter
    public static Long toTimestamp(LocalDateTime date) {
        ZonedDateTime zdt = date.atZone(ZoneId.of("Asia/Tokyo"));
        return zdt.toInstant().toEpochMilli();
    }
}
