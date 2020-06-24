package cu.example.infrastructure.mod_formation.imports;

import cu.example.infrastructure.mod_formation.persistence.mapping.FormationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormationRepository extends JpaRepository<FormationTable, Long> {
}
