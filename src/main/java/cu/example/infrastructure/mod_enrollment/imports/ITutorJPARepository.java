package cu.example.infrastructure.mod_enrollment.imports;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ITutorJPARepository extends JpaRepository<TutorTable, Long>,
        JpaSpecificationExecutor<TutorTable>,
        PagingAndSortingRepository<TutorTable, Long> {
}
