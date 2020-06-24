package cu.example.infrastructure.mod_trace.imports;

import cu.example.infrastructure.mod_trace.persistence.mapping.Trace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITraceRepository extends MongoRepository<Trace, String> {

    @Override
    Trace save(Trace tracesDoc);

}
