package cu.example.application.mod_enrollment.service;

import cu.example.application.exception.NotValidIDException;
import cu.example.application.exception.ResourceNotFoundException;
import cu.example.application.mod_enrollment.adapter.TutorAdapter;
import cu.example.application.shared.ValidationUtil;
import cu.example.domain.mod_enrollment.repository.TutorRepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import cu.example.interfaces.mod_enrollment.model.TutorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Setter
@Getter
@AllArgsConstructor
public class TutorService {

    private final TutorAdapter adapter;
    private final ValidationUtil validation;

    @Resource
    private TutorRepository tutorRepository;

    public TutorResponse getTutorById(Long id) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Tutor", "id", id);
        else {
            TutorTable tutor = tutorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tutor", "id", id));
            return adapter.mapTo(tutor);
        }
    }

//    public Page<TutorResponse> getAllByCriteria(Specification<TutorTable> specs, Pageable pageable) {
//        return tutorRepository.findAllByCriteria(specs, pageable).map(adapter::mapTo);
//    }

    public Page<TutorResponse> getAllByCriteria(Specification<TutorTable> specs, Pageable pageable) {
        List<TutorTable> all = tutorRepository.findAll(specs);
        return new PageImpl<>(all, pageable, all.size()).map(adapter::mapTo);
    }

    public Page<TutorResponse> getAll(Pageable pageable) {
        return tutorRepository.findAll(pageable).map(adapter::mapTo);
    }

    public List<TutorTable> getAll(Specification<TutorTable> specs) {
        return tutorRepository.findAll(specs);
    }
}
