package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.Education;

public interface EducationRepository extends CrudRepository<Education, Long> {
    Iterable<Education>findAllById(Long Long);
    Education findAllByInstitute(String partialstring);

}


