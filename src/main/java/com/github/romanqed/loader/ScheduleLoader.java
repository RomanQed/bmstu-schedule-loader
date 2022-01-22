package com.github.romanqed.loader;

import biweekly.ICalendar;
import com.github.romanqed.jutils.concurrent.SimpleTaskFabric;
import com.github.romanqed.jutils.concurrent.Task;
import com.github.romanqed.jutils.concurrent.TaskFabric;

import java.util.Map;
import java.util.Objects;

public class ScheduleLoader {
    private static ScheduleLoaderInstance primaryInstance = new ScheduleLoaderInstance();
    private static TaskFabric fabric = new SimpleTaskFabric();

    public static void close() {
        primaryInstance.close();
    }

    public static ScheduleLoaderInstance spawnInstance() {
        return new ScheduleLoaderInstance(null, fabric);
    }

    public static ScheduleLoaderInstance getPrimaryInstance() {
        return primaryInstance;
    }

    public static Task<Map<String, String>> loadSchedules() {
        return primaryInstance.loadSchedules();
    }

    public static Task<ICalendar> findSchedule(String group) {
        return primaryInstance.findSchedule(group);
    }

    public static Task<ICalendar> loadSchedule(String uri) {
        return primaryInstance.loadSchedule(uri);
    }

    public static void setTaskFabric(TaskFabric fabric) {
        ScheduleLoader.fabric = Objects.requireNonNull(fabric);
        primaryInstance = new ScheduleLoaderInstance(null, fabric);
    }
}
