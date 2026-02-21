package com.aipiwatch.controller;

import com.aipiwatch.model.RequestLog;
import com.aipiwatch.service.RequestLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final RequestLogService requestLogService;

    public AnalyticsController(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @GetMapping("/requests")
    public ResponseEntity<List<RequestLog>> getRequests() {
        return ResponseEntity.ok(requestLogService.getAllLogs());
    }
}
