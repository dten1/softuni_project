package com.aipiwatch.service;

import com.aipiwatch.model.RequestLog;
import com.aipiwatch.storage.RequestLogStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestLogServiceTest {

    @Mock
    private RequestLogStorage storage;

    @InjectMocks
    private RequestLogService service;

    @Test
    void log_shouldSaveLog() {
        RequestLog log = RequestLog.builder()
                .method("GET")
                .endpoint("/test/fast")
                .status(200)
                .durationMs(12)
                .timestamp(LocalDateTime.now())
                .build();

        service.log(log);

        verify(storage).save(log);
    }

    @Test
    void getTotalCount_shouldReturnSize() {
        when(storage.findAll()).thenReturn(List.of(
                RequestLog.builder().method("GET").endpoint("/a").status(200).durationMs(10).timestamp(LocalDateTime.now()).build(),
                RequestLog.builder().method("GET").endpoint("/b").status(200).durationMs(20).timestamp(LocalDateTime.now()).build()
        ));

        long total = service.getTotalCount();

        assertThat(total).isEqualTo(2);
    }

    @Test
    void getSlowRequests_shouldFilterByThreshold() {
        RequestLog fast = RequestLog.builder()
                .method("GET")
                .endpoint("/fast")
                .status(200)
                .durationMs(100)
                .timestamp(LocalDateTime.now())
                .build();
        RequestLog slow = RequestLog.builder()
                .method("GET")
                .endpoint("/slow")
                .status(200)
                .durationMs(900)
                .timestamp(LocalDateTime.now())
                .build();

        when(storage.findAll()).thenReturn(List.of(fast, slow));

        List<RequestLog> result = service.getSlowRequests(500);

        assertThat(result).containsExactly(slow);
    }
}
