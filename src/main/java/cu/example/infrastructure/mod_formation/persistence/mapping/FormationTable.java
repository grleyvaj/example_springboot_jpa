package cu.example.infrastructure.mod_formation.persistence.mapping;

import cu.example.domain.mod_formation.vo.FormationStatus;
import cu.example.infrastructure.shared.AuditModel;
import lombok.*;

import javax.persistence.*;

/**
 * @author Gloria R. Leyva Jerez
 */
@Entity
@Table(name = "formation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormationTable extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "formation_id_seq")
    private long id;

    private String description;

//    @Enumerated(value = EnumType.ORDINAL)
    private FormationStatus status;

}
