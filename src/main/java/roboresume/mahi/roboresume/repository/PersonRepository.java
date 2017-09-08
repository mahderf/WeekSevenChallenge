package roboresume.mahi.roboresume.repository;


import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findByUsername(String username);
    Person findByEmail(String email);
    Long countByEmail(String email);
    Long countByUsername(String username);
    Iterable<Person>findAllByUsername(String username);
    Iterable<Person>findAllByEmail(String email);
    Iterable<Person>findAllById(Long Long);
    Iterable<Person>findPersonById(Long Long);

}
