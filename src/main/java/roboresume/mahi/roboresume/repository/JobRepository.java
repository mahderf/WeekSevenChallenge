package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.Job;

public interface JobRepository extends CrudRepository<Job, Long>{

    Job findByTitle(String title);
    Job findByEmployer(String employer);
    Long countByTitle(String title);
    Long countByEmployer(String employer);
}
