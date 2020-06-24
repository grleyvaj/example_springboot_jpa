package cu.example.domain.mod_enrollment.repository;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.ThesisTable;

import java.util.List;
import java.util.Optional;

public interface ThesisRepository {

    public List<ThesisTable> findAll();

    public Optional<ThesisTable> findById(Long id);

    public ThesisTable save(ThesisTable thesis);

    public void delete(ThesisTable thesis);
}
