package cu.example.interfaces.mod_enrollment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorResponse implements Serializable {

    private static final long serialVersionUID = 3159482323609971445L;

    private Long id;

    private String name;

    private String last;

    private Boolean doctor;
}
