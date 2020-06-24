package cu.example.application.mod_enrollment.adapter;

import cu.example.domain.mod_enrollment.entity.Tutor;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import cu.example.interfaces.mod_enrollment.model.TutorRequest;
import cu.example.interfaces.mod_enrollment.model.TutorResponse;
import org.springframework.stereotype.Component;

@Component
public class TutorAdapter {

    public Tutor mapTo(TutorRequest tutorRepresentation) {
        return Tutor.builder()
                .name(tutorRepresentation.getName())
                .last(tutorRepresentation.getLast())
                .doctor(tutorRepresentation.getDoctor())
                .build();
    }

    public TutorResponse mapTo(TutorTable tutorTable){
        return TutorResponse.builder()
                .id(tutorTable.getId())
                .name(tutorTable.getName())
                .last(tutorTable.getLast())
                .doctor(tutorTable.getDoctor())
                .build();
    }
}
