package cu.example.domain.mod_enrollment.service;

import cu.example.domain.mod_enrollment.entity.Tutor;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class TutorFactory {

    public TutorTable createFrom(Tutor tutor, long tutorId) {

        return TutorTable.builder()
//                .id(student.getId())
                .name(tutor.getName())
                .last(tutor.getLast())
                .doctor(tutor.getDoctor())
                .build();
    }

    public Tutor createFrom(TutorTable tutorTable) {

        return Tutor.builder()
                .id(tutorTable.getId())
                .name(tutorTable.getName())
                .last(tutorTable.getLast())
                .doctor(tutorTable.getDoctor())
                .build();
    }

}
