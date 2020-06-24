package cu.example.domain.trace.repository;

import cu.example.infrastructure.mod_trace.persistence.mapping.Trace;

public interface TraceRepository {

   Trace save(Trace trace);
}
