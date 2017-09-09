package roboresume.mahi.roboresume.repository;

import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.Person;
import roboresume.mahi.roboresume.models.Skills;

public interface SkillsRepository extends CrudRepository<Skills, Long> {
//    Iterable<Skills>findAllById(Long Long);
//    Iterable<Skills> findAllBySkills(String skillName);
//    Skills findAllByPersonskill(String skill);
//    Iterable<Person>findAllByPersonskill(String personskill);
}
