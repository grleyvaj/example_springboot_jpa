package cu.example.domain.trace.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TraceType {

    FUNCIONAL("Trace for Functional Audit"),
    TECNICA("Trace for Technical  Audit");

    String description;
}
