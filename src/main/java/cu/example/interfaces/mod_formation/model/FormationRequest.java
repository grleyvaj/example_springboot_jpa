package cu.example.interfaces.mod_formation.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormationRequest implements Serializable {

    private static final long serialVersionUID = -4496856197072605423L;

    private long id;

    private String description;

    private String status;

}
