package roboresume.mahi.roboresume.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RoboResume {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty
    @Size(max=20)
    private String firstname;
    @NotEmpty
    @Size(max=20)
    private String lastname;

    @NotEmpty
    @Email
    private String email;

    @OneToMany(mappedBy = "roboResumeEdu")
    private Set<Education> educations;
    @OneToMany(mappedBy = "roboResumeExp")
    private Set<WorkExperience> experiences;
    @OneToMany(mappedBy = "roboResumeSkill")
    private Set<Skills>skills;

    public RoboResume()
    {
        setEducations(new HashSet<Education>());
        setExperiences(new HashSet<WorkExperience>());
        setSkills(new HashSet<Skills>());
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
        edu.setRoboResumeEdu(this);
        this.educations.add(edu);
    }

    public void addExperience(WorkExperience exp)
    {
        exp.setRoboResumeExp(this);
        this.experiences.add(exp);
    }

    public void addSkills(Skills skil)
    {
        skil.setRoboResumeSkill(this);
        this.skills.add(skil);
    }

}

