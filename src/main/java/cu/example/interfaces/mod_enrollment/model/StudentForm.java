package cu.example.interfaces.mod_enrollment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentForm implements Serializable {


    private static final long serialVersionUID = -4746893050125681578L;

//    private long id;

    private String ci;

    private String name;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private Boolean active;

//    private long tutor;
}
