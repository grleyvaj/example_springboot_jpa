package cu.example.domain.trace.entity;

import cu.example.domain.trace.vo.OperationType;
import cu.example.domain.trace.vo.TraceType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TraceEvent {

    private TraceType traceType;

    private OperationType operationType;

    private String description;

    private Boolean success;
}
