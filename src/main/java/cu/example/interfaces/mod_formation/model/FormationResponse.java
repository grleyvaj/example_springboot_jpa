package cu.example.interfaces.mod_formation.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class FormationResponse extends RepresentationModel<FormationResponse> implements Serializable {

    private static final long serialVersionUID = -6479135912668457549L;

    private long id;
    private String description;
    private String status;
}
