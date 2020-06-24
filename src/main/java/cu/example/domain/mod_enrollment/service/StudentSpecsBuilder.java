package cu.example.domain.mod_enrollment.service;

import cu.example.domain.mod_enrollment.entity.SearchCriteria;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.interfaces.mod_enrollment.model.StudentForm;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class StudentSpecsBuilder {

    private final List<SearchCriteria> params;
    private String property;

    public StudentSpecsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    /**
     * Búsqueda avanzada
     *
     * @param form Clase que contiene los campos por los cuales se desea filtrar el listado de estudiantes. Ejemplo: name=loria active=true
     * @return Construye una especificación de estudiante con sus predicados para listar los estudiantes que coinciden con los criterios establecidos
     */
    public static Specification<StudentTable> specsToStudent(StudentForm form) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (form.getCi() != null) {
                predicates.add(cb.like(root.get("ci"), "%" + form.getCi() + "%"));
            }

            if (form.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + form.getName() + "%"));
            }

            if (form.getFirstName() != null) {
                predicates.add(cb.like(root.get("firstName"), "%" + form.getFirstName() + "%"));
            }

            if (form.getLastName() != null) {
                predicates.add(cb.like(root.get("lastName"), "%" + form.getLastName() + "%"));
            }

            if (form.getEmail() != null) {
                predicates.add(cb.like(root.get("email"), "%" + form.getEmail() + "%"));
            }

            if (form.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), form.getActive()));
            }

            Optional<Predicate> finalPredicate = predicates.stream().reduce(cb::and);
            return finalPredicate.orElse(null);
        }
                ;
    }

//    public static Specification<StudentTable> fullTextSearch(String searchText) {
//        if (searchText == null) {
//            return null;
//        }
//
//        String fullText = "%" + searchText + "%";
//
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            predicates.add(cb.like(root.get("ci"), fullText));
//            predicates.add(cb.like(root.get("name"), fullText));
//            predicates.add(cb.like(root.get("firstName"), fullText));
//            predicates.add(cb.like(root.get("lastName"), fullText));
//            predicates.add(cb.like(root.get("email"), fullText));
//            predicates.add(cb.equal(root.get("active"), Boolean.parseBoolean(searchText)));
//            //si en l futuro se le adiciona alguna columna a StudentTable debe especificarse para que sea considerada en la búsqueda
//
//            Optional<Predicate> finalPredicate = predicates.stream().reduce(cb::or);
//            return finalPredicate.orElse(null);
//        }
//                ;
//    }


    /**
     * @param searchText Cadena a buscar
     * @return Construye una especificación de estudiante con sus predicados para buscar en todos los campos por la cadena especificada
     */
//    Es igual al anterior pero es más óptimo porque si a StudentTable se le adiciona alguna columna de tipo String, Long o Boolean será considerada automáticamente
    public static Specification<StudentTable> fullTextSearch(String searchText) {
        if (searchText == null) {
            return null;
        }

        String fullText = "%" + searchText + "%";

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Field field : StudentTable.class.getDeclaredFields()) {
                String columnName = field.getName();
//                String type = field.getType().getName();
                Class<?> type = field.getType();

                if (type == Long.class & searchText.matches("\\d*"))
                    predicates.add(cb.equal(root.get(columnName), Long.parseLong(searchText)));
                else if (type == String.class) {
                    //sin importar mayuscula minuscula: ^(?i)(true|false)$
                    if (searchText.matches("true|false"))
                        predicates.add(cb.equal(root.get(columnName), Boolean.parseBoolean(searchText)));
                    else
                        predicates.add(cb.like(root.get(columnName), fullText));
                }
            }

            Optional<Predicate> finalPredicate = predicates.stream().reduce(cb::or);
            return finalPredicate.orElse(null);
        };
    }

    /**
     * Construye un criterio de búsqueda (SearchCriteria) a partir de los parámetros,
     * por ejemplo: para key=name, operation=: y value=loria,
     * construye el criterio: WHERE studenttab0_.name like ?
     * y el criterio constuido se adiciona a la lista de especificaciones por las que se filtrará el listado de estudiantes
     *
     * @param key       Nombre del campo
     * @param operation Operación
     * @param value     Valor del campo,
     * @return Adiciona el criterio según los parámetros especificados
     */
    public StudentSpecsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<StudentTable> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(StudentSpecs::new)
                .collect(Collectors.toList());

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (int i = 0; i < specs.size(); i++) {
                predicates.add(specs.get(i).toPredicate(root, query, cb));
            }

            Optional<Predicate> finalPredicate = predicates.stream().reduce(cb::and);
            return finalPredicate.orElse(null);
        };

    }


    private Object castArguments(Type obj) {
        if (obj.equals(Integer.class)) {
            return Integer.parseInt(obj.toString());
        } else if (obj.equals(Long.class)) {
            return Long.parseLong(obj.toString());
        } else {
            return obj;
        }
    }


}
