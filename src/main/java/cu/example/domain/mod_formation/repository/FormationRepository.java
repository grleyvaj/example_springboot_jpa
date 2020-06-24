package cu.example.domain.mod_formation.repository;

import cu.example.infrastructure.mod_formation.persistence.mapping.FormationTable;

import java.util.List;
import java.util.Optional;

public interface FormationRepository {

    public List<FormationTable> findAll();

    public Optional<FormationTable> findById(long id);

    public FormationTable save(FormationTable formationTable);

    public void delete(FormationTable formationTable);
}
