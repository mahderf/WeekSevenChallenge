package roboresume.mahi.roboresume.models;


import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="USER_DATA")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="email",nullable = false,unique = true)
    @Email
    private String email;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="password",nullable = false)
    private String password;
    @Column(name="last_name", nullable = false)
    private  String lastName;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="username",unique = true, nullable = false)

    private String username;

    @Column(nullable = false)
    private String roleselect;

//    @Column(name="company")
//    private boolean company;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns=@JoinColumn(name="role_id"))
    private Collection<PersonRole> personRoles;
    @OneToMany(mappedBy = "personeducation")
    private Set<Education> educations;
    @OneToMany(mappedBy = "personexperience")
    private Set<WorkExperience> experiences;
    @OneToMany(mappedBy = "personskill")
    private Set<Skills>skills;


    public Person(){
        setEducations(new HashSet<Education>());
        setExperiences(new HashSet<WorkExperience>());
        setSkills(new HashSet<Skills>());
        this.personRoles =new HashSet<PersonRole>();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleselect() {
        return roleselect;
    }

    public void setRoleselect(String roleselect) {
        this.roleselect = roleselect;
    }

    public Collection<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(Collection<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    public Set<WorkExperience> getExperiences() {
        return experiences;
    }

    public void setExperiences(Set<WorkExperience> experiences) {
        this.experiences = experiences;
    }

    public Set<Skills> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skills> skills) {
        this.skills = skills;
    }

    public void addEducation(Education edu)
    {
        edu.setPersoneducation(this);
        this.educations.add(edu);
    }

    public void addExperience(WorkExperience exp)
    {
        exp.setPersonexperience(this);
        this.experiences.add(exp);
    }

    public void addSkills(Skills skil)
    {
        skil.setPersonskill(this);
        this.skills.add(skil);
    }
    public void addRole(PersonRole rl)
    {

        personRoles.add(rl);
    }
}
