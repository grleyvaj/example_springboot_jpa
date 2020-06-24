package cu.example.infrastructure.mod_enrollment.imports;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface IStudentJPARepository extends JpaRepository<StudentTable, Long>,
        PagingAndSortingRepository<StudentTable, Long>,
        JpaSpecificationExecutor<StudentTable> {

    Page<StudentTable> findAllByTutor(TutorTable tutor, Pageable pageable);

    Page<StudentTable> findAllByName(String name, Pageable pageable);
}
