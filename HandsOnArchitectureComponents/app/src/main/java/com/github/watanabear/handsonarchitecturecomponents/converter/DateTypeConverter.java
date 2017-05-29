package com.github.watanabear.handsonarchitecturecomponents.converter;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by watanabear on 2017/05/30.
 * MEMO
 * ---
 * It is worth noting that the @TypeConverters(DateTypeConverter.class)
 * annotation automatically serializes the LocalDateTime object date
 * into a String format of it and deserializes it back into a LocalDateTime object
 * when it reads it out from storage.
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
