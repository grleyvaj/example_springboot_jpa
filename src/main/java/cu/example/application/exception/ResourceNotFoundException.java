package cu.example.application.exception;

import lombok.*;

/**
 * @author Gloria R. Leyva Jerez
 */
@Setter
@Getter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("NotFound.%s.%s.%s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
