package roboresume.mahi.roboresume.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import roboresume.mahi.roboresume.models.Person;
import roboresume.mahi.roboresume.models.PersonRole;
import roboresume.mahi.roboresume.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional// either do everything or nothing
@Service//creates a component that works with Repo
public class SSUserDetailsService implements UserDetailsService{

    // taking a repo object from class to class without initializing it
    private PersonRepository personRepository;
    public  SSUserDetailsService(PersonRepository personRepository){
        this.personRepository=personRepository;
    }

    //determine if user has access
    // inside this method we can setup an if condition to check if user is disabled or enabled access
    // if (enable=true)..something like this
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException{
        try {
            Person person = personRepository.findByUsername(username);
            if (person == null) {
                //don't say user not available in real life
                System.out.println("user not found with the provided username " + person.toString());

                return null;
            }

            System.out.println("user from username" + person.toString());
            return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(),
                    getAuthorities(person));
        }
        catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }

    }
    //this is what looks for the type of the roles and grants specific role
    //this is a method that exists in spring to go ahead and check roles assigned to specific user
    private Set<GrantedAuthority> getAuthorities(Person person){
        Set<GrantedAuthority>authorities=new HashSet<GrantedAuthority>();
        for(PersonRole role: person.getPersonRoles())
        {
            GrantedAuthority grantedAuthority=new SimpleGrantedAuthority((role.getRole()));
            authorities.add(grantedAuthority);
        }
        System.out.println("user authorities are "+authorities.toString());
        return authorities;

    }


}
