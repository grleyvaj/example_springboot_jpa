package cu.example.domain.mod_enrollment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * key : el nombre del campo - por ejemplo, primerNombre , edad , etc ...
 * operation : la operación - por ejemplo, igualdad, menor que, ... etc.
 * value : el valor del campo, por ejemplo, juan, 25, ... etc.
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
