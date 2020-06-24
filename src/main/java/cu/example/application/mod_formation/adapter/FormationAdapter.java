package cu.example.application.mod_formation.adapter;

import cu.example.domain.mod_formation.entity.Formation;
import cu.example.domain.mod_formation.vo.FormationStatus;
import cu.example.infrastructure.mod_formation.persistence.mapping.FormationTable;
import cu.example.interfaces.mod_formation.model.FormationRequest;
import cu.example.interfaces.mod_formation.model.FormationResponse;
import org.springframework.stereotype.Component;


@Component
public class FormationAdapter {

    public Formation mapTo(FormationRequest formationRequest) {
        return Formation.builder()
//                .id(formationRequest.getId())
                .description(formationRequest.getDescription())
//                .status(FormationStatus.valueOf(formationRequest.getStatus()))
                .build();
    }

    public FormationResponse mapTo(FormationTable formationTable){
        return FormationResponse.builder()
                .id(formationTable.getId())
                .description(formationTable.getDescription())
                .status(formationTable.getStatus().toString())
                .build();
    }
}
