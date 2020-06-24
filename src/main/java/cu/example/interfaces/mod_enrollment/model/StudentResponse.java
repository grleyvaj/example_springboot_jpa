package cu.example.interfaces.mod_enrollment.model;

import com.fasterxml.jackson.annotation.*;
import cu.example.domain.mod_enrollment.entity.Thesis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id"})
//@JsonFilter("myfilter")
public class StudentResponse implements Serializable {

    private static final long serialVersionUID = 3077294558162556832L;

    private Long id;

    private String ci;

    private String name;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private Boolean active;

    private Long tutor;

    private Thesis thesis;
}
