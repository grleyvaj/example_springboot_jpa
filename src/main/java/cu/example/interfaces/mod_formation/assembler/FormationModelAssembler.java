package cu.example.interfaces.mod_formation.assembler;

import cu.example.interfaces.mod_formation.controller.FormationRestController;
import cu.example.interfaces.mod_formation.model.FormationResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;



@Component
public class FormationModelAssembler implements RepresentationModelAssembler<FormationResponse, EntityModel<FormationResponse>> {

    @Override
    public EntityModel<FormationResponse> toModel(FormationResponse formation) {
        // Unconditional links to single-item resource and aggregate root
        EntityModel<FormationResponse> formationModel = new EntityModel<>(formation,
                WebMvcLinkBuilder.linkTo(methodOn(FormationRestController.class).getFormationById(formation.getId())).withSelfRel(),
                linkTo(methodOn(FormationRestController.class).getAllFormations()).withRel("formations"));

        // Conditional links based on state of the formation
        if (formation.getStatus().equals("IN_PROGRESS")) {
            formationModel.add(
                    linkTo(methodOn(FormationRestController.class)
                            .cancel(formation.getId())).withRel("cancel"));
            formationModel.add(
                    linkTo(methodOn(FormationRestController.class)
                            .complete(formation.getId())).withRel("complete"));
        }
        return formationModel;
    }

    public List<EntityModel<FormationResponse>> toCollectionModel(List<FormationResponse> formations) {
        return formations.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
