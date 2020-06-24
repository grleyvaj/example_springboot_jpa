package cu.example.infrastructure.mod_enrollment.imports;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.ThesisTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IThesisRepository extends JpaRepository<ThesisTable, Long> {
}
