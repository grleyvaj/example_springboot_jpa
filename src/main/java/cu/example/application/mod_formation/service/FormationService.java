package cu.example.application.mod_formation.service;

import cu.example.application.exception.NotValidIDException;
import cu.example.application.exception.ResourceNotFoundException;
import cu.example.application.mod_formation.adapter.FormationAdapter;
import cu.example.application.shared.ValidationUtil;
import cu.example.domain.mod_formation.entity.Formation;
import cu.example.domain.mod_formation.repository.FormationRepository;
import cu.example.domain.mod_formation.service.FormationFactory;
import cu.example.domain.mod_formation.vo.FormationStatus;
import cu.example.infrastructure.mod_formation.imports.IFormationRepository;
import cu.example.infrastructure.mod_formation.persistence.mapping.FormationTable;
import cu.example.interfaces.mod_formation.model.FormationRequest;
import cu.example.interfaces.mod_formation.model.FormationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FormationService {

    private final FormationAdapter adapter;
    private final ValidationUtil validation;

    @Resource
    private final FormationFactory factory;

    @Resource
    private FormationRepository repository;

    public List<FormationResponse> getAllFormations() {
        return repository.findAll().stream()
                .map(adapter::mapTo)
                .collect(Collectors.toList());
    }

    public FormationResponse getFormationById(long id) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Formation", "id", id);
        else {
            FormationTable formation = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Formation", "id", id));
            return adapter.mapTo(formation);
        }
    }

    public FormationResponse addFormation(FormationRequest formationRequest) {
        Formation formationToAdd = adapter.mapTo(formationRequest);
        formationToAdd.setStatus(FormationStatus.IN_PROGRESS);
        FormationTable newFormation = repository.save(factory.createFrom(formationToAdd));
        return adapter.mapTo(newFormation);
    }

    public FormationResponse updateFormationByIdAndStatus(long id, String status) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Formation", "id", id);
        else {
            FormationTable formation = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Formation", "ci", id));

            formation.setStatus(FormationStatus.valueOf(status));

            return adapter.mapTo(repository.save(formation));
        }
    }
}
