package cu.example.infrastructure.mod_formation.persistence.repository;

import cu.example.domain.mod_formation.repository.FormationRepository;
import cu.example.infrastructure.mod_formation.imports.IFormationRepository;
import cu.example.infrastructure.mod_formation.persistence.mapping.FormationTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class FormationRepositoryImpl implements FormationRepository {

    private final IFormationRepository impl;

    @Override
    public List<FormationTable> findAll() {
       return impl.findAll();
    }

    @Override
    public Optional<FormationTable> findById(long id) {
        return impl.findById(id);
    }

    @Override
    public FormationTable save(FormationTable formationTable) {
        return impl.save(formationTable);
    }

    @Override
    public void delete(FormationTable formationTable) {
        impl.delete(formationTable);
    }
}
