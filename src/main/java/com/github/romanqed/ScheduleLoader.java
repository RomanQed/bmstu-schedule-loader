package com.github.romanqed;

import biweekly.ICalendar;
import com.github.romanqed.concurrent.Task;

import java.util.Map;

public class ScheduleLoader {
    private static final ScheduleLoaderInstance primaryInstance = new ScheduleLoaderInstance();

    public static void close() {
        primaryInstance.close();
    }

    public static ScheduleLoaderInstance spawnInstance() {
        return new ScheduleLoaderInstance();
    }

    public static Task<Map<String, String>> loadSchedules() {
        return primaryInstance.loadSchedules();
    }

    public static Task<ICalendar> loadSchedule(String uri) {
        return primaryInstance.loadSchedule(uri);
    }
}
