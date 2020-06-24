package cu.example.infrastructure.mod_enrollment.persistence.mapping;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gloria R. Leyva Jerez
 */
@Entity
@Table(name = "tutor")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"studentSet"})
public class TutorTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tutor_id_seq")
    private Long id;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Column(name = "last", length = 10, nullable = false)
    private String last;

    @Column(name = "doctor")
    private Boolean doctor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    private Set<StudentTable> studentSet = new HashSet<StudentTable>(0);
}
