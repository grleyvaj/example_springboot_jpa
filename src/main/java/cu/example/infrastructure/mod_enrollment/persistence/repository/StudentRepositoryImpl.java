package cu.example.infrastructure.mod_enrollment.persistence.repository;

import cu.example.domain.mod_enrollment.repository.StudentRepository;
import cu.example.infrastructure.mod_enrollment.imports.IStudentJPARepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private final IStudentJPARepository impl;

    @Override
    public List<StudentTable> findAll() {
        return impl.findAll();
    }

    @Override
    public List<StudentTable> findAll(Specification<StudentTable> specs){
        return impl.findAll(specs);
    }

    @Override
    public Page<StudentTable> findAll(Pageable pageable) {
        return impl.findAll(pageable);
    }

    @Override
    public Page<StudentTable> findAllByCriteria(Specification<StudentTable> studentSpecs, Pageable pageable) {
        return impl.findAll(studentSpecs, pageable);
    }

    //    @Override
//    public List<StudentTable> findAllByCriteria(Specification<StudentTable> studentSpecs) {
//        return impl.findAll(studentSpecs);
//    }

    @Override
    public Page<StudentTable> findAllByTutor(TutorTable tutor, Pageable pageable) {
        return impl.findAllByTutor(tutor, pageable);
    }

    @Override
    public Page<StudentTable> findAllByName(String name, Pageable pageable) {
        return impl.findAllByName(name, pageable);
    }

    @Override
    public Optional<StudentTable> findById(Long id) {
        return impl.findById(id);
    }

    @Override
    public StudentTable save(StudentTable student) {
        return impl.save(student);
    }

    @Override
    public void delete(StudentTable student) {
        impl.delete(student);
    }
}
