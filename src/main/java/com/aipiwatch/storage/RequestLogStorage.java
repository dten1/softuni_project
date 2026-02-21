package com.aipiwatch.storage;

import com.aipiwatch.model.RequestLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class RequestLogStorage {
    private final CopyOnWriteArrayList<RequestLog> logs = new CopyOnWriteArrayList<>();

    public void save(RequestLog log) {
        logs.add(log);
    }

    public List<RequestLog> findAll() {
        return new ArrayList<>(logs);
    }

    public void clear() {
        logs.clear();
    }
}
