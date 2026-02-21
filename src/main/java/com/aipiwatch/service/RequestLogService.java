package com.aipiwatch.service;

import com.aipiwatch.model.RequestLog;
import com.aipiwatch.storage.RequestLogStorage;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestLogService {
    private final RequestLogStorage storage;

    public RequestLogService(RequestLogStorage storage) {
        this.storage = storage;
    }

    public void log(RequestLog log) {
        storage.save(log);
    }

    public List<RequestLog> getAllLogs() {
        return fetchLogs();
    }

    public long getTotalCount() {
        return fetchLogs().size();
    }

    public List<RequestLog> getSlowRequests(long thresholdMs) {
        return fetchLogs().stream()
                .filter(log -> log.getDurationMs() > thresholdMs)
                .toList();
    }

    public Map<String, Long> getTopEndpoints() {
        return fetchLogs().stream()
                .collect(Collectors.groupingBy(RequestLog::getEndpoint, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(byCountDesc())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (left, right) -> left,
                        LinkedHashMap::new
                ));
    }

    private List<RequestLog> fetchLogs() {
        return storage.findAll();
    }

    private Comparator<Map.Entry<String, Long>> byCountDesc() {
        return Map.Entry.comparingByValue(Comparator.reverseOrder());
    }
}
