package cu.example.domain.mod_enrollment.repository;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface TutorRepository {

    public Page<TutorTable> findAll(Pageable pageable);

    public List<TutorTable> findAll(Specification<TutorTable> specs);

    public Page<TutorTable> findAllByCriteria(Specification<TutorTable> studentSpecs, Pageable pageable);

    public Optional<TutorTable> findById(Long id);

    public TutorTable save(TutorTable tutor);

    public void delete(TutorTable tutor);
}
