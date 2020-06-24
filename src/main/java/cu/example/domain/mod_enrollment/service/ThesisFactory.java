package cu.example.domain.mod_enrollment.service;

import cu.example.domain.mod_enrollment.entity.Thesis;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.ThesisTable;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ThesisFactory {

    public ThesisTable createFrom(Thesis thesis) {

        return ThesisTable.builder()
                .id(thesis.getId())
                .title(thesis.getTitle())
                .theme(thesis.getTheme())
                .description(thesis.getDescription())
                .build();
    }

    public Thesis createFrom(ThesisTable table) {

        return Thesis.builder()
                .id(table.getId())
                .title(table.getTitle())
                .theme(table.getTheme())
                .description(table.getDescription())
                .build();
    }

}
