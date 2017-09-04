package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.Education;
import roboresume.mahi.roboresume.models.RoboResume;

public interface EducationRepository extends CrudRepository<Education, Long> {
    Iterable<Education>findAllById(Long Long);

}


