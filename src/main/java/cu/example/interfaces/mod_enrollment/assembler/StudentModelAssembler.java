package cu.example.interfaces.mod_enrollment.assembler;

import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import cu.example.interfaces.mod_enrollment.rest.StudentRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentModelAssembler implements RepresentationModelAssembler<StudentResponse, EntityModel<StudentResponse>> {

    @Override
    public EntityModel<StudentResponse> toModel(StudentResponse student) {

        return new EntityModel<>(student,
                linkTo(methodOn(StudentRestController.class).getStudentById(student.getId())).withSelfRel());
    }

//    public List<EntityModel<StudentResponse>> toCollectionModel(List<StudentResponse> students) {
//        return students.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }

    public CollectionModel<EntityModel<StudentResponse>> toCollectionModel(List<StudentResponse> list) {
        List<EntityModel<StudentResponse>> collect = list.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<EntityModel<StudentResponse>>(collect);
    }

}
