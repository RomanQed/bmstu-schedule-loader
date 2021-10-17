package com.github.romanqed.loader;

import biweekly.Biweekly;
import biweekly.ICalendar;
import com.github.romanqed.jutils.concurrent.Task;
import com.github.romanqed.jutils.concurrent.TaskFabric;
import com.github.romanqed.loader.network.AsyncLoader;
import com.github.romanqed.loader.util.Parse;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Map;
import java.util.function.Function;

public class ScheduleLoaderInstance {
    public final static String SCHEDULES_URL = "https://lks.bmstu.ru/schedule/list";
    public final static String BMSTU_URL = "https://lks.bmstu.ru";
    private final AsyncLoader loader;

    public ScheduleLoaderInstance(OkHttpClient client, TaskFabric fabric) {
        loader = new AsyncLoader(client, fabric);
    }

    public ScheduleLoaderInstance() {
        this(null, null);
    }

    public Task<Map<String, String>> loadSchedules() {
        Function<String, Map<String, String>> processor = (rawPage) -> {
            Document page = Jsoup.parse(rawPage);
            return Parse.parseSchedules(page);
        };
        try {
            return loader.asyncLoad(new URL(SCHEDULES_URL), processor);
        } catch (Exception e) {
            return null;
        }
    }

    public Task<ICalendar> loadSchedule(String uri) {
        try {
            return loader.asyncLoad(
                    new URL(BMSTU_URL + uri + ".ics"),
                    (rawCalendar) -> Biweekly.parse(rawCalendar).first()
            );
        } catch (Exception e) {
            return null;
        }
    }

    public AsyncLoader getLoader() {
        return loader;
    }

    public void close() {
        loader.getTaskFabric().getExecutor().shutdown();
    }
}
