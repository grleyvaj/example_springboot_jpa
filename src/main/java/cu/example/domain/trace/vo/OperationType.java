package cu.example.domain.trace.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {

    GENERIC_LIST("LIST"),
    GENERIC_REGISTER("REGISTER"),
    GENERIC_MODIFY("MODIFY"),
    GENERIC_DELETE("DELETE")
    ;

    String description;
}