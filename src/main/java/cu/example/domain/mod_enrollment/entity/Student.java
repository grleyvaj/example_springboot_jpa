package cu.example.domain.mod_enrollment.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
public class Student implements Serializable {

    private static final long serialVersionUID = -5815233114076929584L;

    private Long id;

    @NotBlank(message = "{notBlank.ci}")
    @Size(min = 11, max = 11, message = "{size.ci}")
    @Pattern(regexp = "^[0-9]*$", message = "{pattern.ci.numeric}")
    private String ci;

    @NotBlank(message = "{notBlank.name}")
    @Size(max = 50, message = "{size.name}")
    private String fullName;

    @NotBlank(message = "{notBlank.firstName}")
    @Size(max = 10, message = "{size.firstName}")
    private String firstName;

    @NotBlank(message = "{notBlank.lastName}")
    @Size(max = 10, message = "{size.lastName}")
    private String lastName;

    @Email(message = "{email.email}")
    private String email;

    private Boolean active;

    @Valid
    private Tutor tutor;

    @Valid
    private Thesis thesis;

    public void setEmail(String email) {
        this.email = email.isEmpty() ? null : email;
    }
}
