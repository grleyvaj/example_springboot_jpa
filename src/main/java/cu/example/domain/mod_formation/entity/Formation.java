package cu.example.domain.mod_formation.entity;

import cu.example.domain.mod_formation.vo.FormationStatus;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Gloria R. Leyva Jerez
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class Formation implements Serializable {

    private static final long serialVersionUID = 4639001840437102606L;

    @Digits(integer = 10, fraction = 0, message = "El id debe ser un número entero con 10 cifras enteras máximas")
    private long id;

    @NotBlank(message = "{notBlank.description}")
    @Size(max = 255, message = "{size.description}")
    private String description;

    private FormationStatus status;


}
