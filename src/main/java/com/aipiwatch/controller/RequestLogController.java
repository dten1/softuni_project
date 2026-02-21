package com.aipiwatch.controller;

import com.aipiwatch.model.RequestLog;
import com.aipiwatch.service.RequestLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logs")
public class RequestLogController {
    private final RequestLogService requestLogService;

    public RequestLogController(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @GetMapping
    public ResponseEntity<List<RequestLog>> getAllLogs() {
        return ResponseEntity.ok(requestLogService.getAllLogs());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        return ResponseEntity.ok(requestLogService.getTotalCount());
    }

    @GetMapping("/slow")
    public ResponseEntity<List<RequestLog>> getSlowRequests(
            @RequestParam(name = "threshold", defaultValue = "500") long thresholdMs
    ) {
        return ResponseEntity.ok(requestLogService.getSlowRequests(thresholdMs));
    }

    @GetMapping("/top-endpoints")
    public ResponseEntity<Map<String, Long>> getTopEndpoints() {
        return ResponseEntity.ok(requestLogService.getTopEndpoints());
    }
}
