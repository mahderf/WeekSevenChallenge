package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.RoboResume;

public interface ResumeRepository extends CrudRepository <RoboResume,Long>  {

    Iterable<RoboResume>findAllById(Long Long);
    Iterable<RoboResume>findRoboResumeById(Long Long);
}

