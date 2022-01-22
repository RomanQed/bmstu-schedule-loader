package com.github.romanqed.loader;

import biweekly.Biweekly;
import biweekly.ICalendar;
import com.github.romanqed.jutils.concurrent.AbstractTask;
import com.github.romanqed.jutils.concurrent.Task;
import com.github.romanqed.jutils.concurrent.TaskFabric;
import com.github.romanqed.loader.network.AsyncLoader;
import com.github.romanqed.loader.util.ParseUtil;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public class ScheduleLoaderInstance {
    public final static URL SCHEDULES_URL = ParseUtil.parseUrl("https://lks.bmstu.ru/schedule/list");
    public final static URL BMSTU_URL = ParseUtil.parseUrl("https://lks.bmstu.ru");
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
            return ParseUtil.parseSchedules(page);
        };
        return loader.asyncLoad(SCHEDULES_URL, processor);
    }

    public Task<ICalendar> findSchedule(String group) {
        try {
            Map<String, String> found = loadSchedules().async().get();
            return loadSchedule(found.get(group));
        } catch (Exception e) {
            return new AbstractTask<ICalendar>() {
                @Override
                public ExecutorService getExecutor() {
                    return null;
                }

                @Override
                public ICalendar call() {
                    return null;
                }
            };
        }
    }

    public Task<ICalendar> loadSchedule(String uri) {
        return loader.asyncLoad(
                ParseUtil.attachUrl(BMSTU_URL, uri + ".ics"),
                (rawCalendar) -> Biweekly.parse(rawCalendar).first()
        );
    }

    public AsyncLoader getLoader() {
        return loader;
    }

    public void close() {
        loader.getTaskFabric().getExecutor().shutdown();
    }
}
