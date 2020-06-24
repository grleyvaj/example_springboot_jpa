package cu.example.infrastructure.mod_enrollment.persistence.mapping;

import cu.example.infrastructure.shared.AuditModel;
import lombok.*;

import javax.persistence.*;

/**
 * @author Gloria R. Leyva Jerez
 */
@Entity
@Table(name = "student")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentTable extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "student_id_seq")
    private Long id;

    @Column(name = "num_identity", length = 11, nullable = false, unique = true)
    private String ci;

    @Column(name = "full_name", length = 50, nullable = false)
    private String name;

    @Column(name = "first_name", length = 10, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 10, nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "tutor", referencedColumnName = "id")
    private TutorTable tutor;

    @OneToOne
    @JoinColumn(name = "thesis_id", referencedColumnName = "id", unique = true)
    private ThesisTable thesis;

    public void setEmail(String email) {
        this.email = email.isEmpty() ? null : email;
    }

}
