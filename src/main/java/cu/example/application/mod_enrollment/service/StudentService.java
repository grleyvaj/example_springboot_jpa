package cu.example.application.mod_enrollment.service;

import cu.example.application.exception.NotValidIDException;
import cu.example.application.exception.ResourceNotFoundException;
import cu.example.application.mod_enrollment.adapter.StudentAdapter;
import cu.example.application.shared.ValidationUtil;
import cu.example.domain.mod_enrollment.entity.Student;
import cu.example.domain.mod_enrollment.repository.StudentRepository;
import cu.example.domain.mod_enrollment.repository.ThesisRepository;
import cu.example.domain.mod_enrollment.repository.TutorRepository;
import cu.example.domain.mod_enrollment.service.StudentFactory;
import cu.example.domain.mod_enrollment.service.StudentSpecsBuilder;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import cu.example.interfaces.mod_enrollment.model.StudentForm;
import cu.example.interfaces.mod_enrollment.model.StudentRequest;
import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static cu.example.domain.mod_enrollment.service.StudentSpecsBuilder.fullTextSearch;
import static cu.example.domain.mod_enrollment.service.StudentSpecsBuilder.specsToStudent;

@Service
@Setter
@Getter
@AllArgsConstructor
@Validated
public class StudentService {

    private final StudentAdapter studentAdapter;

    private final ValidationUtil validation;

    @Resource
    private StudentFactory factory;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private TutorRepository tutorRepository;

    @Resource
    private ThesisRepository thesisRepository;

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentAdapter::mapTo)
                .collect(Collectors.toList());
    }

    public Page<StudentResponse> getAllByCriteria(Specification<StudentTable> specs, Pageable pageable) {
        List<StudentTable> all = studentRepository.findAll(specs);
        return new PageImpl<>(all, pageable, all.size()).map(studentAdapter::mapTo);
    }


//    public Page<StudentResponse> getAllStudents(Pageable pageable) {
//        List<StudentResponse> all = repository.findAll(pageable).stream()
//                .map(adapter::mapTo)
//                .collect(Collectors.toList());
//
//        return new PageImpl(all, pageable, all.size());
//    }

    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(studentAdapter::mapTo);
    }


    public Page<StudentResponse> getAllStudentsBySpecs(StudentForm studentForm, Pageable pageable) {
        return studentRepository.findAllByCriteria(specsToStudent(studentForm), pageable)
                .map(studentAdapter::mapTo);
    }

    public Page<StudentResponse> getAllStudentsByAll(String fullText, Pageable pageable) {
        return studentRepository.findAllByCriteria(fullTextSearch(fullText), pageable)
                .map(studentAdapter::mapTo);
    }

    public Page<StudentResponse> getAllStudentsByPathSpecs(String searchText, Pageable pageable) {
        StudentSpecsBuilder builder = new StudentSpecsBuilder();

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(searchText + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<StudentTable> spec = builder.build();

        return studentRepository.findAllByCriteria(spec, pageable)
                .map(studentAdapter::mapTo);
    }

    //    public Page<StudentResponse> getAllStudentsByForm(StudentForm studentForm, Pageable pageable) {
//        List<StudentTable> collect = repository.findAllByCriteria(getStudents(studentForm))
//                .stream()
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(collect, pageable, collect.size()).map(adapter::mapTo);
//    }

    public StudentResponse getStudentById(Long id) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Student", "id", id);
        else {
            StudentTable student = studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
            return studentAdapter.mapTo(student);
        }
    }

    public StudentResponse addStudent(StudentRequest studentRequest) {
        Student studentToAdd = studentAdapter.mapTo(studentRequest);
        StudentTable student = factory.createFrom(studentToAdd, studentRequest.getTutor(), studentRequest.getThesis());
        return studentAdapter.mapTo(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Long studentId, StudentRequest studentRepresentation) {
        if (!validation.isValidId(studentId))
            throw new NotValidIDException("Student", "id", studentId);
        else {
            StudentTable studentTable = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

            Student studentToUpdate = studentAdapter.mapTo(studentRepresentation);
            StudentTable student = factory.createFrom(studentToUpdate, studentRepresentation.getTutor(), studentRepresentation.getThesis());
            student.setId(studentId);
            return studentAdapter.mapTo(studentRepository.save(student));
        }
    }

    public void deleteStudentById(Long id) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Student", "id", id);
        else {
            StudentTable student = studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "ci", id));
            studentRepository.delete(student);
        }
    }

    public Page<StudentResponse> getAllStudentsByTutor(Long tutorId, Pageable pageable) {

        Optional<TutorTable> byId = tutorRepository.findById(tutorId);

        return studentRepository.findAllByTutor(byId.get(), pageable)
                .map(studentAdapter::mapTo);
    }

    public Page<StudentResponse> getAllStudentsByName(String name, Pageable pageable) {
        return studentRepository.findAllByName(name, pageable)
                .map(studentAdapter::mapTo);
    }

    /**
     * Update cause partially by identification and fields
     *
     * @param id     Identification of the student
     * @param fields Fields with the information that you should to update
     */
    public void updatePartial(Long id, Map<String, Object> fields) {

        StudentTable entityToPatch = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(StudentTable.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, entityToPatch, v);
        });

        studentRepository.save(entityToPatch);
    }
}
