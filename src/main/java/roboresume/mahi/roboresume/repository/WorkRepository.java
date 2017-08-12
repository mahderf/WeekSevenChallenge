package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.WorkExperience;

public interface WorkRepository extends CrudRepository<WorkExperience, Long> {

}
