package cu.example.application.mod_enrollment.adapter;

import cu.example.domain.mod_enrollment.entity.Student;
import cu.example.domain.mod_enrollment.service.ThesisFactory;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.ThesisTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import cu.example.interfaces.mod_enrollment.model.StudentRequest;
import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class StudentAdapter {

    @Resource
    private ThesisFactory thesisFactory;

    public Student mapTo(StudentRequest studentRepresentation) {
        Student build = Student.builder()
                .ci(studentRepresentation.getCi())
                .fullName(studentRepresentation.getName())
                .firstName(studentRepresentation.getFirstName())
                .lastName(studentRepresentation.getLastName())
                .email(studentRepresentation.getEmail().isEmpty() ? null : studentRepresentation.getEmail())
                .active(studentRepresentation.getActive())
                .build();
        return build;
    }

    public StudentResponse mapTo(StudentTable student){

        TutorTable tutor = student.getTutor();
        ThesisTable thesis = student.getThesis();

        return StudentResponse.builder()
                .id(student.getId())
                .ci(student.getCi())
                .name(student.getName())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .active(student.getActive())
                .tutor(tutor==null ? null : tutor.getId())
                .thesis(thesis==null ? null : thesisFactory.createFrom(thesis))
                .build();
    }

}
