package cu.example.domain.mod_enrollment.service;

import cu.example.domain.mod_enrollment.entity.SearchCriteria;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
@AllArgsConstructor
public class StudentSpecs implements Specification<StudentTable> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<StudentTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return cb.greaterThanOrEqualTo(
                    root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return cb.lessThanOrEqualTo(
                    root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            System.out.println("tipo:" + root.get(criteria.getKey()).getJavaType());
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return cb.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class){
                return cb.equal(root.get(criteria.getKey()), Boolean.parseBoolean(criteria.getValue().toString()));
            }
            else
                return cb.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }
}
