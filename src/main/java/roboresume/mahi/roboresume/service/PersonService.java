package roboresume.mahi.roboresume.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roboresume.mahi.roboresume.models.Person;
import roboresume.mahi.roboresume.repository.PersonRepository;
import roboresume.mahi.roboresume.repository.RoleRepository;

import java.util.Arrays;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository=personRepository;
    }
    public Person findByEmail(String email){
        return personRepository.findByEmail(email);
    }
    public Long countByEmail(String email){
        return personRepository.countByEmail(email);
    }
    public Person findByUsername(String username){
        return personRepository.findByUsername(username);
    }
    public void saveJobSeeker(Person person){
        person.setPersonRoles(Arrays.asList(roleRepository.findByRole("JOBSEEKER")));
        person.setEnabled(true);
        personRepository.save(person);
    }
    public void saveAdmin(Person person){
        person.setPersonRoles(Arrays.asList(roleRepository.findByRole("ADMIN")));
        person.setEnabled(true);
        personRepository.save(person);
    }public void saveRecruiter(Person person){
        person.setPersonRoles(Arrays.asList(roleRepository.findByRole("RECRUITER")));
        person.setEnabled(true);
        personRepository.save(person);
    }

}
