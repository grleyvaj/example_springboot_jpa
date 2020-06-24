package cu.example.domain.mod_enrollment.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

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
public class Thesis implements Serializable {

    private static final long serialVersionUID = 4690328575363597141L;

    @NotBlank(message = "{notBlank.id}")
    private long id;

    @NotBlank(message = "{notBlank.tittle}")
    @Size(max = 150, message = "{size.tittle}")
    private String title;

    @NotBlank(message = "{notBlank.theme}")
    @Size(max = 150, message = "{size.theme}")
    private String theme;

    @NotBlank(message = "{notBlank.description}")
    @Size(max = 255, message = "{size.description}")
    private String description;
}
