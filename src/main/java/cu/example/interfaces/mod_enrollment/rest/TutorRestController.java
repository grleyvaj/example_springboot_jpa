package cu.example.interfaces.mod_enrollment.rest;

import com.sipios.springsearch.anotation.SearchSpec;
import cu.example.application.mod_enrollment.service.TutorService;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import cu.example.interfaces.mod_enrollment.assembler.TutorModelAssembler;
import cu.example.interfaces.mod_enrollment.model.TutorResponse;
import cu.example.interfaces.shared.URIConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = URIConstant.ENTITY_API + URIConstant.API_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TutorRestController {

    private static final String ENTITY_URI = URIConstant.URI_TUTOR;

    private final TutorModelAssembler assembler;

    @Resource
    private TutorService tutorService;

    @Resource
    private PagedResourcesAssembler<TutorResponse> pagedResourcesAssembler;


    /**
     * Obtner el listado de tutores que coinciden con las especificacions especificada por el path de la URI
     *
     * @param specs Especificación para tutor
     * @return
     */
    //    Obtiene el listado de tutores sin filtros de especificaciones ni paginación
    //    http://127.0.0.1:8083/univ/v1/tutors

    //    Obtiene el listado de tutores solo con paginación
    //    http://127.0.0.1:8083/univ/v1/tutors?page=0&size=5&sort=id,desc

    //    Obtiene el listado de tutores con paginación y con filtros de especificacion segun el query especificado en el path
    //    Ejemplos:

    //    Listado de tutores cuyo last es EQUAL Gloria e id es EQUAL 2
    //    http://127.0.0.1:8083/univ/v1/tutors?search=(last:'Gloria' AND id:2)&page=0&size=5

    //    Listado de tutores cuyo last es LIKE loria e id es mayor q 0
    //    http://127.0.0.1:8083/univ/v1/tutors?search=(last:*ria AND id>0)&page=0&size=5
    @GetMapping("/tutors")
    public ResponseEntity<?> searchForTutor(@SearchSpec Specification<TutorTable> specs, Pageable pageable) {
        Page<TutorResponse> allByCriteria = tutorService.getAllByCriteria(specs, pageable);

        PagedModel<EntityModel<TutorResponse>> collModel = pagedResourcesAssembler
                .toModel(allByCriteria, assembler);

        return ResponseEntity.ok(collModel);
    }

    //  Operadores para specs usandos Spring-search
    //    La operación igual , usando el operador :
    //    La operación no igual , usando el operador !
    //    Los operadores mayor que y menor que , respectivamente > y <
    //    El operador comienza con / termina con / contiene , utilizando *. Actúa como el bash * expension.
    //    El AND operador
    //    El OR operador
    //    El paréntesis se puede usar para agrupar.
    //    Puede acceder a un campo profundo de un objeto utilizando la notación (.) Por ejemplo options.transmission en nuestro ejemplo.
    //

    /**
     * @param tutorId Identificador de tutor
     * @return Devuelve el tutor por su identificador
     */
    //    http://127.0.0.1:8083/univ/v1/tutors/305
    @GetMapping(ENTITY_URI + "/{id}")
    public ResponseEntity<EntityModel<TutorResponse>> getTutorById(@PathVariable(value = "id") Long tutorId) {
        TutorResponse tutor = tutorService.getTutorById(tutorId);
        return ResponseEntity.ok(assembler.toModel(tutor));
    }

}
