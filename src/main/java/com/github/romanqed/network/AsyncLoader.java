package com.github.romanqed.network;

import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import com.github.romanqed.util.Checks;
import okhttp3.OkHttpClient;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class AsyncLoader extends AbstractLoader {
    protected final TaskFabric fabric;

    public AsyncLoader(OkHttpClient client, TaskFabric fabric) {
        super(client);
        this.fabric = Checks.requireNonNullElse(fabric, new BaseTaskFabric());
    }

    public <T> Task<T> asyncLoad(URL url, Function<String, T> processor) {
        Callable<T> taskBody = () -> processor.apply(load(url));
        return fabric.createTask(taskBody);
    }

    public TaskFabric getTaskFabric() {
        return fabric;
    }
}
