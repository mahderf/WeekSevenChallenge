package roboresume.mahi.roboresume.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class PersonRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String role;

    @Column(nullable = false)
    private String roleselect;

    @ManyToMany(mappedBy = "personRoles", fetch = FetchType.LAZY)
    private Collection<Person> people;

  public PersonRole(){

    this.people =new HashSet<Person>();
   }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<Person> getPeople() {
        return people;
    }

    public void setPeople(Collection<Person> people) {
        this.people = people;
    }

    public String getRoleselect() {
        return roleselect;
    }

    public void setRoleselect(String roleselect) {
        this.roleselect = roleselect;
    }

    public void addUser(Person usr)
    {
        people.add(usr);
    }
}
