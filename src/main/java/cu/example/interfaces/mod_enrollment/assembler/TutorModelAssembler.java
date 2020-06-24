package cu.example.interfaces.mod_enrollment.assembler;

import cu.example.interfaces.mod_enrollment.model.TutorResponse;
import cu.example.interfaces.mod_enrollment.rest.TutorRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TutorModelAssembler implements RepresentationModelAssembler<TutorResponse, EntityModel<TutorResponse>> {

    @Override
    public EntityModel<TutorResponse> toModel(TutorResponse tutor) {

        return new EntityModel<>(tutor,
                linkTo(methodOn(TutorRestController.class).getTutorById(tutor.getId())).withSelfRel());
    }
}
