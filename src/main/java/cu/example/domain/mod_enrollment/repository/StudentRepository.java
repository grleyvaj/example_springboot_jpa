package cu.example.domain.mod_enrollment.repository;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    public List<StudentTable> findAll();

    public List<StudentTable> findAll(Specification<StudentTable> specs);

    public Page<StudentTable> findAll(Pageable pageable);

    public Page<StudentTable> findAllByCriteria(Specification<StudentTable> studentSpecs, Pageable pageable);

    public Page<StudentTable> findAllByTutor(TutorTable tutor, Pageable pageable);

    public Page<StudentTable> findAllByName(String name, Pageable pageable);

//    public List<StudentTable> findAllByCriteria(Specification<StudentTable> studentSpecs);

    public Optional<StudentTable> findById(Long id);

    public StudentTable save(StudentTable student);

    public void delete(StudentTable student);
}
