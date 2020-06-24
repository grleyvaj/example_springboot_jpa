package cu.example.interfaces.mod_formation.controller;

import cu.example.application.mod_formation.service.FormationService;
import cu.example.interfaces.mod_formation.assembler.FormationModelAssembler;
import cu.example.interfaces.mod_formation.model.FormationRequest;
import cu.example.interfaces.mod_formation.model.FormationResponse;
import cu.example.interfaces.shared.SuccessfulContentHandler;
import cu.example.interfaces.shared.URIConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = URIConstant.ENTITY_API + URIConstant.API_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public class FormationRestController {

    private static final String ENTITY_URI = URIConstant.URI_FORM;

    private final FormationModelAssembler assembler;
    private final SuccessfulContentHandler contentHandler;

    @Resource
    private FormationService formationService;


    @GetMapping(ENTITY_URI)
    public CollectionModel<EntityModel<FormationResponse>> getAllFormations() {
        List<FormationResponse> formationList = formationService.getAllFormations();

        return new CollectionModel<>(assembler.toCollectionModel(formationList),
                linkTo(methodOn(FormationRestController.class).getAllFormations()).withSelfRel());
    }

    @GetMapping(ENTITY_URI + "/{id}")
    public EntityModel<FormationResponse> getFormationById(@PathVariable long id) {
        return assembler.toModel(formationService.getFormationById(id));
    }

    @PostMapping(path = ENTITY_URI, consumes = "application/json")
    public ResponseEntity<EntityModel<FormationResponse>> newFormation(@RequestBody FormationRequest formationRequest) {

        FormationResponse newFormation = formationService.addFormation(formationRequest);

        return ResponseEntity
                .created(linkTo(methodOn(FormationRestController.class).getFormationById(newFormation.getId())).toUri())
                .body(assembler.toModel(newFormation));
    }

    @DeleteMapping(ENTITY_URI + "/{id}" + "/cancel")
    public ResponseEntity<RepresentationModel<?>> cancel(@PathVariable long id) {

        FormationResponse formation = formationService.getFormationById(id);

        if (formation.getStatus().equals("IN_PROGRESS")) {
            return ResponseEntity.ok(assembler
                    .toModel(formationService.updateFormationByIdAndStatus(id, "CANCELLED")));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError(
                        contentHandler.logrefVndError(),
                        contentHandler.messageVndErrorToCancel() + " " + formation.getStatus()));
    }

    @PutMapping(ENTITY_URI + "/{id}" + "/complete")
    public ResponseEntity<RepresentationModel> complete(@PathVariable long id) {

        FormationResponse formation = formationService.getFormationById(id);

        if (formation.getStatus().equals("IN_PROGRESS")) {
            return ResponseEntity.ok(assembler
                    .toModel(formationService.updateFormationByIdAndStatus(id, "COMPLETED")));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError(contentHandler.logrefVndError(),
                        contentHandler.messageVndErrorToCompleted() + " " + formation.getStatus()));
    }
}
