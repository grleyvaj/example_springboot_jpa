package cu.example.domain.mod_enrollment.service;

import cu.example.application.exception.ResourceNotFoundException;
import cu.example.domain.mod_enrollment.entity.Student;
import cu.example.domain.mod_enrollment.entity.Thesis;
import cu.example.domain.mod_enrollment.repository.ThesisRepository;
import cu.example.domain.mod_enrollment.repository.TutorRepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Setter
@Getter
@AllArgsConstructor
@Validated
public class StudentFactory {

    private final TutorRepository tutorRepository;
    private final ThesisRepository thesisRepository;

    private final TutorFactory tutorFactory;
    private final ThesisFactory thesisFactory;

    public StudentTable createFrom(@Valid Student student, Long tutorId, Thesis thesis) {

        TutorTable tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor", "id", tutorId));

//        ThesisTable thesisTable = thesisRepository.findById(thesis.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Thesis", "id", thesis));
        thesisRepository.save(thesisFactory.createFrom(thesis));

        return StudentTable.builder()
//                .id(student.getId())
                .ci(student.getCi())
                .name(student.getFullName())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .active(student.getActive())
                .tutor(tutor)
                .thesis(thesisFactory.createFrom(thesis))
                .build();
    }
}
