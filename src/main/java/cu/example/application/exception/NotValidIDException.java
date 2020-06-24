package cu.example.application.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Gloria R. Leyva Jerez
 */
@Setter @Getter
@NoArgsConstructor
public class NotValidIDException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public NotValidIDException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("NotValid.%s.%s.%s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
