package cu.example.domain.mod_formation.service;

import cu.example.domain.mod_formation.entity.Formation;
import cu.example.domain.mod_formation.vo.FormationStatus;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.infrastructure.mod_formation.persistence.mapping.FormationTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Setter
@Getter
@AllArgsConstructor
@Validated
public class FormationFactory {

    public FormationTable createFrom(@Valid Formation formation) {

        return FormationTable.builder()
//                .id(student.getId())
                .id(formation.getId())
                .description(formation.getDescription())
                .status(formation.getStatus())
                .build();
    }
}
