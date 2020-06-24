package cu.example.infrastructure.mod_trace.persistence.mapping;

import cu.example.infrastructure.shared.DateConverter;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collation = "traces")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Trace {

    @Id
    private String documentId;

    @Convert(converter = DateConverter.class)
    private LocalDate localDate;

    private LocalTime hour;

    private String user;

    private Boolean success;

    private String traceType;

    private String operationType;

    private String description;
}
