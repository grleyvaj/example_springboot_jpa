package cu.example.infrastructure.mod_enrollment.persistence.repository;

import cu.example.domain.mod_enrollment.repository.ThesisRepository;
import cu.example.infrastructure.mod_enrollment.imports.IThesisRepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.ThesisTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class ThesisRepositoryImpl implements ThesisRepository {

    private final IThesisRepository impl;

    @Override
    public List<ThesisTable> findAll() {
        return impl.findAll();
    }

    @Override
    public Optional<ThesisTable> findById(Long id) {
        return impl.findById(id);
    }

    @Override
    public ThesisTable save(ThesisTable thesis) {
        return impl.save(thesis);
    }

    @Override
    public void delete(ThesisTable thesis) {
        impl.delete(thesis);
    }
}
