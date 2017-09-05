package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.Courses;

public interface CourseRepository extends CrudRepository<Courses, Long>{

    Iterable<Courses>findCoursesById(Long Long);
}
