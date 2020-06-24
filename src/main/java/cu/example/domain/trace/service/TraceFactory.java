package cu.example.domain.trace.service;

import cu.example.domain.trace.entity.TraceEvent;
import cu.example.infrastructure.mod_trace.persistence.mapping.Trace;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class TraceFactory {

    public Trace createFrom(TraceEvent event) {

        return Trace.builder()
                .localDate(LocalDate.now())
                .hour(LocalTime.now())
                .user("app-sicom")
                .success(event.getSuccess())
                .traceType(String.valueOf(event.getTraceType().getDescription()))
                .operationType(event.getOperationType().getDescription())
                .description(event.getDescription())
                .build();
    }
}
