package com.github.romanqed;

import biweekly.Biweekly;
import biweekly.ICalendar;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import com.github.romanqed.network.AsyncLoader;
import com.github.romanqed.util.Parse;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class ScheduleLoaderInstance {
    public final static String SCHEDULES_URL = "https://lks.bmstu.ru/schedule/list";
    public final static String BMSTU_URL = "https://lks.bmstu.ru";
    private final UnirestInstance unirestInstance = Unirest.spawnInstance();
    private final TaskFabric fabric = new BaseTaskFabric(Executors.newCachedThreadPool());
    private final AsyncLoader loader;

    public ScheduleLoaderInstance() {
        loader = new AsyncLoader(unirestInstance, fabric);
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
                    new URL(BMSTU_URL + uri),
                    (rawCalendar) -> Biweekly.parse(rawCalendar).first()
            );
        } catch (Exception e) {
            return null;
        }
    }

    public void close() {
        unirestInstance.close();
        fabric.getExecutor().shutdown();
    }
}
