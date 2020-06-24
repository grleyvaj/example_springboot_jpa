package cu.example.infrastructure.mod_enrollment.persistence.mapping;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gloria R. Leyva Jerez
 */
@Entity
@Table(name = "thesis")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThesisTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tutor_id_seq")
    private Long id;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "theme", length = 150, nullable = false)
    private String theme;

    @Column(name = "description", length = 150, nullable = false)
    private String description;
}
