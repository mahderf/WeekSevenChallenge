package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.Job;
import roboresume.mahi.roboresume.models.Skills;

import java.util.Collection;
import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long>{

    Job findByTitle(String title);
    Job findByEmployer(String employer);
    Long countByTitle(String title);
    Long countByEmployer(String employer);
    Iterable<Job>findAllByTitle(String title);
    Iterable<Job>findAllByEmployer(String partialstring);
    Iterable<Job>findAllByJobskills(Collection<Skills> skills, Collection<Skills>jobskills);
//    Job findAllByJobskills(String jobskills);
}
