package roboresume.mahi.roboresume.repository;


import org.springframework.data.repository.CrudRepository;
import roboresume.mahi.roboresume.models.PersonRole;


public interface RoleRepository extends CrudRepository<PersonRole,Long> {
Iterable<PersonRole>findAllById(Long Long);
Iterable<PersonRole>findPersonRoleByRole(String String);
PersonRole findByRole(String personRole);

}
