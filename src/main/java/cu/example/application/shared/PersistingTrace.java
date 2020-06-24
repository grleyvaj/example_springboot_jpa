package cu.example.application.shared;

import cu.example.domain.trace.entity.TraceEvent;
import cu.example.domain.trace.repository.TraceRepository;
import cu.example.domain.trace.service.TraceFactory;
import cu.example.infrastructure.mod_trace.persistence.mapping.Trace;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Setter
@AllArgsConstructor
public class PersistingTrace {

    @Resource
    private TraceRepository repository;

    @Resource
    private final TraceFactory factory;

    @Async("taskExecutorTrace")
    public void persist(TraceEvent event) {
        Trace trace = factory.createFrom(event);
        repository.save(trace);
    }
}
