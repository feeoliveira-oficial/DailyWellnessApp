package com.example.dailywellnesstracker.Model;

import androidx.room.TypeConverter;
import java.util.Calendar;

public class Converters {

    @TypeConverter
    public static Long fromCalendar(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public static Calendar toCalendar(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar;
    }
}
