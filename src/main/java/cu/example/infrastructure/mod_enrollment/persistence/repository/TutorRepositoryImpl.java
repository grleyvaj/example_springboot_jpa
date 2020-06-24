package cu.example.infrastructure.mod_enrollment.persistence.repository;

import cu.example.domain.mod_enrollment.repository.TutorRepository;
import cu.example.infrastructure.mod_enrollment.imports.ITutorJPARepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class TutorRepositoryImpl implements TutorRepository {

    private final ITutorJPARepository impl;

    @Override
    public Page<TutorTable> findAll(Pageable pageable) {
        return impl.findAll(pageable);
    }

    @Override
    public List<TutorTable> findAll(Specification<TutorTable> specs){
        return impl.findAll(specs);
    }

    @Override
    public Page<TutorTable> findAllByCriteria(Specification<TutorTable> studentSpecs, Pageable pageable) {
        return findAllByCriteria(studentSpecs, pageable);
    }

    @Override
    public Optional<TutorTable> findById(Long id) {
        return impl.findById(id);
    }

    @Override
    public TutorTable save(TutorTable tutor) {
        return impl.save(tutor);
    }

    @Override
    public void delete(TutorTable tutor) {
        impl.delete(tutor);
    }
}
