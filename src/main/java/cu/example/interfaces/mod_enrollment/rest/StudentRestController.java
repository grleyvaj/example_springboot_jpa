package cu.example.interfaces.mod_enrollment.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sipios.springsearch.anotation.SearchSpec;
import cu.example.application.mod_enrollment.service.StudentService;
import cu.example.application.shared.PersistingTrace;
import cu.example.domain.trace.entity.TraceEvent;
import cu.example.domain.trace.vo.OperationType;
import cu.example.domain.trace.vo.TraceType;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.interfaces.mod_enrollment.assembler.StudentModelAssembler;
import cu.example.interfaces.mod_enrollment.model.StudentForm;
import cu.example.interfaces.mod_enrollment.model.StudentRequest;
import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import cu.example.interfaces.shared.SuccessfulContentHandler;
import cu.example.interfaces.shared.URIConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = URIConstant.ENTITY_API + URIConstant.API_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentRestController {

    private static final String ENTITY_URI = URIConstant.URI_ENROLL;
    private static final String ENTITY_NAME = URIConstant.ENTITY_NAME_ENROLL;

    private final StudentModelAssembler studentAssembler;
    private final SuccessfulContentHandler contentHandler;

    @Resource
    private PagedResourcesAssembler<StudentResponse> pagedResourcesAssembler;

    @Resource
    private PersistingTrace persisting;

    @Resource
    private StudentService studentService;


    //    Este ejemplo contiene acceso a campo profundo porque Student tienen Thesis y pregundo por el id de la tesis
    //    http://127.0.0.1:8083/univ/v1/students/all?search=(name:*lor* AND thesis.id:1)&page=0&size=5

    //    Este ejemplo tambien con acceso profundo obtiene los estudiantes que tienen como tutor al de identificador 1
    //    http://127.0.0.1:8083/univ/v1/students/all?search=tutor.id:1&page=0&size=5

    /**
     * Busqueda avanzada con spring-search
     * Obtner el listado de tutores a partir de filtros de especificaciones y paginación
     *
     * @param specs    Especificación para estudiante
     * @param pageable Paginación, contine el page, size y sort para filtrar resultados
     * @return Obtiene el listado de estudiantes que coninciden con los filtros de búsqueda y paginación
     */

    @GetMapping(path = ENTITY_URI + "/all")
    public ResponseEntity<?> getAllStudents(@SearchSpec Specification<StudentTable> specs, Pageable pageable) {
        Page<StudentResponse> allByCriteria = studentService.getAllByCriteria(specs, pageable);

        PagedModel<EntityModel<StudentResponse>> collModel = pagedResourcesAssembler
                .toModel(allByCriteria, studentAssembler);

        return ResponseEntity.ok(collModel);
    }

    /**
     * Búsqueda de estudiantes con paginación
     *
     * @param pageable Especifica el page, size y sort. El page indica la página que se desea obtener,
     *                 size la cantidad de estudiantes a mostrar por página
     *                 sort para especificar el campo por el que se oredenarán los elementos así como un criterio 'asc' o 'desc'
     * @return Listado de estudiantes según el page, size y sort especificado en el endpoint
     */
    //http://127.0.0.1:8083/univ/v1/students/?page=1&size=20&sort=ci,desc
    //si no especifica page, size y order, por defecto page=0, size=5, ordenara por id ascendentemente
    //http://127.0.0.1:8083/univ/v1/students/
    @GetMapping(path = ENTITY_URI + "/")
    public ResponseEntity<PagedModel<EntityModel<StudentResponse>>> getAllStudentsPageable(@PageableDefault(page = 0, size = 5)
                                                                                           @SortDefault.SortDefaults({
                                                                                                   @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                                                                           }) Pageable pageable) {

        Page<StudentResponse> students = studentService.getAllStudents(pageable);

        PagedModel<EntityModel<StudentResponse>> collModel = pagedResourcesAssembler
                .toModel(students, studentAssembler);

        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_LIST, contentHandler.createSuccessListAlert(ENTITY_NAME), true));

        return ResponseEntity.ok(collModel);
    }

    /**
     * Búsqueda filtrada, se obtienen los estudiantes que coincidan con los filtros establecidos en studentForm
     *
     * @param pageable    Especifica el page, size y sort. El page indica la página que se desea obtener,
     *                    *                 size la cantidad de estudiantes a mostrar por página
     *                    *                 sort para especificar el campo por el que se oredenarán los elementos así como un criterio 'asc' o 'desc'
     * @param studentForm Contiene los campos para filtrar la búsqueda, por ejemplo por name=loria
     * @return Devuelve el listado de estudiantes que se corresponden con los criterios establecidos, ejemplos estudiantos cuyo name=loria
     */
//    http://127.0.0.1:8083/univ/v1/students/search?page=0&size=2
    @GetMapping(path = ENTITY_URI + "/search", consumes = "application/json")
    public ResponseEntity<PagedModel<EntityModel<StudentResponse>>> getAllStudentsByForm(Pageable pageable, @RequestBody StudentForm studentForm) {

        Page<StudentResponse> students = studentService.getAllStudentsBySpecs(studentForm, pageable);

        PagedModel<EntityModel<StudentResponse>> collModel = pagedResourcesAssembler
                .toModel(students, studentAssembler);

        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_LIST, contentHandler.createSuccessListAlert(ENTITY_NAME), true));

        return ResponseEntity.ok(collModel);
    }

    /**
     * Búsqueda de texto completo, se obtiene todos los estudiantes que en alguno de sus campos contengan la cadena especificada por parámetro
     *
     * @param pageable Especifica el page, size y sort. El page indica la página que se desea obtener,
     *                 *                 size la cantidad de estudiantes a mostrar por página
     *                 *                 sort para especificar el campo por el que se oredenarán los elementos así como un criterio 'asc' o 'desc'
     * @param fullText Contiene la cadena de texto que sefiltrá en todos los campos
     * @return Devuelve el listado de estudiantes que se corresponden con los criterios establecidos, ejemplos estudiantos cuyo name=lori
     */
//   http://127.0.0.1:8083/univ/v1/students?search=loria&page=0&size=3&sort=ci,asc
    @GetMapping(path = ENTITY_URI + "/find")
    public ResponseEntity<PagedModel<EntityModel<StudentResponse>>> getAllStudentsByAll(Pageable pageable, @RequestParam(value = "search") String fullText) {

        Page<StudentResponse> students = studentService.getAllStudentsByAll(fullText, pageable);

        PagedModel<EntityModel<StudentResponse>> collModel = pagedResourcesAssembler
                .toModel(students, studentAssembler);

//        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_LIST, contentHandler.createSuccessListAlert(ENTITY_NAME), true));

        return ResponseEntity.ok(collModel);
    }

    @GetMapping(path = ENTITY_URI + "/q")
    public ResponseEntity<PagedModel<EntityModel<StudentResponse>>> getAllStudentsByPathSpecs(Pageable pageable, @RequestParam(value = "search") String searchText) {

        Page<StudentResponse> students = studentService.getAllStudentsByPathSpecs(searchText, pageable);

        PagedModel<EntityModel<StudentResponse>> collModel = pagedResourcesAssembler
                .toModel(students, studentAssembler);

//        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_LIST, contentHandler.createSuccessListAlert(ENTITY_NAME), true));

        return ResponseEntity.ok(collModel);
    }

    /**
     * @param studentId Identificador de estudiante
     * @return Devuelve el estudiante por su identificador
     */
    //    http://127.0.0.1:8083/univ/v1/students/305
    @GetMapping(ENTITY_URI + "/{studentId}")
    public ResponseEntity<EntityModel<StudentResponse>> getStudentById(@PathVariable Long studentId) {
        StudentResponse student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(studentAssembler.toModel(student));
    }


    /**
     * Selección de campos
     *
     * @param fields Campos a seleccionar. ejemplo: fields=name,ci. Aunque estudiante tenga más campos solamente se verán name y ci.
     * @return Devuelve los estudiantes almacenados en BD, pero solamente visualiza los campos especificados por parámetro
     * @throws JsonProcessingException
     */
    @GetMapping(ENTITY_URI + "/by")
//    http://127.0.0.1:8083/univ/v1/students/by?fields=ci,name
//    Para probar este descomentarear el filtro "myfilter" en la clase StudentResponse
    public ResponseEntity<?> getAllStudent(@RequestParam(value = "fields") String fields) throws JsonProcessingException {
        List<StudentResponse> students = studentService.getAllStudents();
        CollectionModel<EntityModel<StudentResponse>> entityModels = studentAssembler.toCollectionModel(students);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("myfilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));

        ObjectMapper mapper = new ObjectMapper().setFilterProvider(filterProvider);

        return new ResponseEntity<>(mapper.readValue(mapper.writeValueAsString(entityModels), Object.class), HttpStatus.OK);
    }

    /**
     * @param studentRequest Información del estudiante que se desea adicionar
     * @return Adiciona un estudiante
     */
    @PostMapping(path = ENTITY_URI, consumes = "application/json")
    public ResponseEntity<EntityModel<StudentResponse>> newStudent(@RequestBody StudentRequest studentRequest) {

        StudentResponse newStudent = studentService.addStudent(studentRequest);
        EntityModel<StudentResponse> student = studentAssembler.toModel(newStudent);

        String msg = contentHandler.createEntityCreationAlertMessage(ENTITY_NAME + " " + student.getContent().getName());
        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_REGISTER, msg, true));

        return ResponseEntity
                .created(student.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .headers(SuccessfulContentHandler.createHeaders(msg))
                .body(student);
    }

    /**
     * @param studentId      Identificador del estudiante que se desea actualizar
     * @param studentRequest Información del estudiante que se desea actualizar
     * @return Actualiza un estudiante
     */
    @PutMapping(path = ENTITY_URI + "/{studentId}", consumes = "application/json")
    public ResponseEntity<EntityModel<StudentResponse>> updateStudent(@PathVariable Long studentId, @RequestBody StudentRequest studentRequest) {

        StudentResponse updateStudent = studentService.updateStudent(studentId, studentRequest);

        EntityModel<StudentResponse> student = studentAssembler.toModel(updateStudent);

        String msg = contentHandler.createEntityUpdateAlertMessage(ENTITY_NAME + " " + updateStudent.getName());
        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_MODIFY, msg, true));

        return ResponseEntity
                .created(student.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .headers(SuccessfulContentHandler.createHeaders(msg))
                .body(student);
    }

    /**
     * @param studentId Identificador del estudiante que se desea eliminar
     * @return Elimina un estudiante
     */
    @DeleteMapping(path = ENTITY_URI + "/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable(value = "id") Long studentId) {
        studentService.deleteStudentById(studentId);

        String msg = contentHandler.createEntityDeleteAlertMessage(ENTITY_NAME + " " + studentId);
        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_DELETE, msg, true));

        return ResponseEntity.noContent()
                .headers(SuccessfulContentHandler.createHeaders(msg)).build();
    }

    //    Listado de estudiante cuyo tutor sea el q tiene id=0. ---> Búsqueda por campo tutor
    //    Example: http://127.0.0.1:8083/univ/v1/students?tutor=0&page=0&size=5&sort=id,asc

    //    Listado de estudiantes q tienen como nombre "loria". ---> Búsqueda por campo name
    //    Example: http://127.0.0.1:8083/univ/v1/students?name=Gloria&page=0&size=5&sort=id,asc

    //   Listado de estudiante q en alguno de sus campos tienen la cadena "loria" ---> Búsqueda de texto completa
    //   http://127.0.0.1:8083/univ/v1/students?q=aria&page=0&size=5&sort=id,asc

    //    Devuelve los estudiantes almacenados en BD, pero solamente visualiza los campos especificados por parámetros
    //    Ejemplo: fields=name,ci. Aunque estudiante tenga más campos solamente se verán name y ci.
    @GetMapping(ENTITY_URI)
    public ResponseEntity<?> getAllStudentsByTutor(Pageable pageable,
                                                   @RequestParam(value = "tutor", required = false) Long tutorId,
                                                   @RequestParam(value = "name", required = false) String studentName,
                                                   @RequestParam(value = "q", required = false) String searchFullText
            /*,  @RequestParam(value = "fields", required = false) String selected) throws JsonProcessingException*/) {

        Page<StudentResponse> students;

        if (tutorId != null) {
            students = studentService.getAllStudentsByTutor(tutorId, pageable);
        } else if (studentName != null) {
            students = studentService.getAllStudentsByName(studentName, pageable);
        } else if (searchFullText != null) {
            students = studentService.getAllStudentsByAll(searchFullText, pageable);
        } else {
            students = studentService.getAllStudents(pageable);
        }

//        if (selected != null) {
//            // Seleccionar campos
//            CollectionModel<EntityModel<StudentResponse>> entityModels = studentAssembler.toCollectionModel(students);
//
//            SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("myfilter",
//                    SimpleBeanPropertyFilter.filterOutAllExcept(selected.split(",")));
//
//            ObjectMapper mapper = new ObjectMapper().setFilterProvider(filterProvider);
//
//            return new ResponseEntity<>(mapper.readValue(mapper.writeValueAsString(entityModels), Object.class), HttpStatus.OK);
//        } else {
        PagedModel<EntityModel<StudentResponse>> collModel = pagedResourcesAssembler
                .toModel(students, studentAssembler);

        return ResponseEntity.ok(collModel);
//        }
    }

//    @GetMapping(ENTITY_URI + "/{studentId}") + "/tutors" + "{tutorId}"})

    @PatchMapping(path = ENTITY_URI + "/{id}", consumes = "application/json")
    public ResponseEntity<?> partialUpdateCauseCreate(
            @PathVariable  Long id,
            @RequestBody(required = true) Map<String, Object> fields) {

        studentService.updatePartial(id, fields);

        return ResponseEntity.noContent().build();
    }
}
