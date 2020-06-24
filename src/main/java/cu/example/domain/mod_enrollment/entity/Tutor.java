package cu.example.domain.mod_enrollment.entity;

import lombok.*;

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
public class Tutor implements Serializable {

    private static final long serialVersionUID = 9153587198765144792L;

    @NotBlank(message = "{notBlank.id}")
    private long id;

    @NotBlank(message = "{notBlank.name}")
    @Size(max = 10, message = "{size.name}")
    private String name;

    @NotBlank(message = "{notBlank.lastName}")
    @Size(max = 10, message = "{size.last}")
    private String last;

    private Boolean doctor;
}
