package cu.example.infrastructure.mod_trace.persistence.repository;

import cu.example.domain.trace.repository.TraceRepository;
import cu.example.infrastructure.mod_trace.imports.ITraceRepository;
import cu.example.infrastructure.mod_trace.persistence.mapping.Trace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
@AllArgsConstructor
@Service
public class TraceRepositoryImpl implements TraceRepository {

    private final ITraceRepository impl;

    @Override
    public Trace save(Trace trace) {
        return impl.save(trace);
    }
}
